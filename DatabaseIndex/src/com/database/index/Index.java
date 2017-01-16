/**
* This source code implements B+ Tree indexing from an key/value input file. 
 * It supports insert, find, search and find functions.
 * 
 * @author Lopamudra Muduli <lopamudra.muduli@utdallas.edu>
 * 		   Bhakti Khatri <brk160030@utdallas.edu>
 * @versopm 1.0
 */

package com.database.index;

import com.database.index.BPlusTreeNode;
import com.database.index.BPlusTreeNode.Item;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Index {
    
    static int mkeyFieldLength; /* Key Field Length Size */
    static String mInputFName;  /* Input Data File Name */
    static String mIndexFName;  /* Index File Name */
    ArrayList<BPlusTreeNode.Item> leafNode = new ArrayList<BPlusTreeNode.Item>(); /* Leaf Node */
    long mRoot = Constants.nullpointer;/* Root pointer location */
    long mNumNodes = 0;   		/* Total Number of nodes in the B+ tree */
    int mNumItems = 0;			/* Total Number of Items read from the input file */
    int mNodeSize = 1024;    	/* Block size */
	long offset=0;
    static BPlusTreeNode mCurrentNode = new BPlusTreeNode();
    FileInputStream indexFile;
    FileChannel mDataFile;
 
    
    /**
     * addItem() - Add a new item to the BplusTree Node.
     * @param newItem - New item to be added.
     * @param NewRight - The right element splited from the Node.
     * @param node - Node in which the item will be added.
     * @param location - Index in the Node where item will be added.
     * @author Lopamudra Muduli
     */
    public void addItem(BPlusTreeNode.Item newItem, long NewRight,
    						  BPlusTreeNode node, int location)
	{
	    int j;

	    for (j = node.getCount(); j > location; j--)
	    {
	    	node.setRecord(j,node.getRecord(j-1));
	    	node.getChild()[j+1] = node.getChild()[j];
	    }
	    node.setRecord(location, newItem);
	    node.getChild()[location + 1] = NewRight;
	    node.Count++;
	}
    /**
    * searchNode() - Search for a specific key in the b+ tree.
    * @param target - The value to look for in the CurrentNode field
    * @return - return true if found, false otherwise
    * Location - index of where Target was found. If not found, index
    * and index + 1 are the indices between which Target would fit.
    * @author Lopamudra Muduli
    */
    public static SearchHelperClass searchNode(String target)
    {
        boolean found;
        found = false;
        int location = -1;
       
        if (target.compareTo(mCurrentNode.getRecord(0).getKeyField()) < 0)
            location = -1;
        else
        { // do a sequential search, right to left:
            location = mCurrentNode.Count - 1;
            while ((target.compareTo(mCurrentNode.getRecord(location).getKeyField()) < 0)
             && (location > 0))
                location--;

            if ((target.compareTo(mCurrentNode.getRecord(location).getKeyField()) == 0))
                found = true;
        }
        return new SearchHelperClass(found,location);
    }
    
    /**
    * split() - Split the node that CurrentRoot points to into 2 nodes
    * @param CurrentItem - Item to be placed in node
    * @param CurrentRight - Pseudopointer to the child to the right of CurrentItem
    * @param CurrentRoot - Pseudopointer to the node to be split
    * @param Location - Index of where CurrentItem should go in this node
    * @return - NewItem - The item to be moved up into the parent node.
    *          NewRight - a pointer to the new RightNode
    * @author Lopamudra Muduli
    */
    public PushHelperClass split(BPlusTreeNode.Item recordToInset, long insertRecRchildValue,long currentNodeIndx, int location)
    {
        int j, Median;
        BPlusTreeNode rightNode = new BPlusTreeNode();
        BPlusTreeNode.Item newRootRecord;
        long newRootRChildptr;

        if (location < Constants.minKeys)
            Median = Constants.minKeys;
        else
            Median = Constants.minKeys + 1;
        
        int offset = (int)(currentNodeIndx * mNodeSize +1024);//0*1024
        readFromIndexFile(mDataFile,offset);
        
        for (j = Median; j < Constants.maxNumKeys; j++)
        {  // move half of the items to the RightNode
            rightNode.record[j - Median] = mCurrentNode.record[j];
            rightNode.setChild(j - Median + 1,mCurrentNode.child[j + 1]);
        }
        
        rightNode.Count = Constants.maxNumKeys - Median;
        mCurrentNode.Count = Median;   // is then incremented by AddItem
        
        //put CurrentItem in place
        if (location < Constants.minKeys)
            addItem(recordToInset, insertRecRchildValue, mCurrentNode, location + 1);
        else
            addItem(recordToInset, insertRecRchildValue, rightNode, location - Median + 1);
        
        newRootRecord = mCurrentNode.getRecord(mCurrentNode.Count - 1);
        rightNode.child[0] = mCurrentNode.child[mCurrentNode.Count];
        mCurrentNode.Count--;
        
        int offsetw = (int)(currentNodeIndx * mNodeSize +1024);//0
        writeInIndexFile(mDataFile, mCurrentNode, offsetw);
        
        newRootRChildptr = mNumNodes;//1
        
        int offsetwD = (int)(newRootRChildptr * mNodeSize +1024);//1
        writeInIndexFile(mDataFile, rightNode, offsetwD);
        mNumNodes++;//1++
        
        return new PushHelperClass(true, newRootRecord, newRootRChildptr);

    }

    public void printNext(RandomAccessFile raReadInputF,BPlusTreeNode currNode,
					      BPlusTreeNode prevNode, int location, int remainingCount) {
		long currentRoot;
		
    	if(remainingCount == 0 || location == Constants.maxNumKeys)
			return;
		try {
			if(currNode.child[location+1] != -1){
				currentRoot = currNode.child[location+1];
				int offset = (int) (currentRoot * mNodeSize + 1024);
				readFromIndexFile(mDataFile, offset);
				long offsetInput = mCurrentNode.getRecord(0).getDataField();
				raReadInputF.seek(offsetInput);
				String result = raReadInputF.readLine();
				System.out.println("At "+offset + ", record: "+ result);
				remainingCount--;
				location = 0;
				printNext(raReadInputF, mCurrentNode, prevNode, location, remainingCount);
			} 
				
			if(currNode.getRecord(location+1) != null){
				prevNode = currNode;
				long offsetInInputF = currNode.getRecord(location+1).getDataField();
				raReadInputF.seek(offsetInInputF);
				String result = raReadInputF.readLine();
				System.out.println("At "+offsetInInputF + ", record: "+ result);
				remainingCount--;
				location++;	 
				printNext(raReadInputF, currNode, prevNode, location, remainingCount);
			}else {
					printNext(raReadInputF, prevNode, null, 0,remainingCount);
			}
		
		}catch (IOException e) {
			e.printStackTrace();
		}

    }

    /**
     * pushDown() - Handles the B+ Tree insertion case where new items need to be pushed down.
     * @param record - New item need to be added.
     * @param root - Current root location.
     * @return - PushHelperClass that will contain the new right child, item.
     * @author Lopamudra Muduli
     */
    public PushHelperClass pushDown(BPlusTreeNode.Item record,long root){
    	long currentRoot = root;
    	PushHelperClass returnPush = null;
    	
    	if (currentRoot == Constants.nullpointer)   // stopping case
        {   // cannot insert into empty tree
    		returnPush = new PushHelperClass(true,record,Constants.nullpointer);
    		return returnPush;
        }
        else   // recursive case
        {	
        	int offset1 = (int)(currentRoot * mNodeSize +1024);
            readFromIndexFile(mDataFile,offset1);
                        
            SearchHelperClass searchResult = searchNode(record.getKeyField());
            if (searchResult.getFound()){
            	System.out.println("Error: attempt to put a duplicate into B-tree");
            }
                
            PushHelperClass phelper = pushDown(record, mCurrentNode.child[searchResult.getLocation() + 1]);

            if ((phelper!= null) && phelper.getMoveUp())
            {	
            	int offset2 = (int)(currentRoot * mNodeSize +1024);
                readFromIndexFile(mDataFile,offset2);
            	
                if (mCurrentNode.getCount() < Constants.maxNumKeys)
                    {
                		returnPush = new PushHelperClass(false, null, Constants.nullpointer);
                		// moveUp = false;
                        addItem(phelper.getItem(), phelper.getItemRightChild(), mCurrentNode, searchResult.getLocation()+1);
                        
                        int offset = (int)(currentRoot * mNodeSize +1024);
                        writeInIndexFile(mDataFile,mCurrentNode,offset);
                      
                    }
                else
                	returnPush = split(phelper.getItem(), phelper.getItemRightChild(),currentRoot,searchResult.getLocation());
                	
            }
        	return returnPush;

        }

    }
    /**
     * Insert() - Insert a given item to the B+ Tree.
     * @param insertItem - The item to be inserted.
     * @return - True if insert is successful.
     * @author Lopamudra Muduli
     */
    boolean Insert(BPlusTreeNode.Item insertItem){
    	//boolean MoveUp = false;
        long NewRight = Constants.nullpointer;
        int offset;
        PushHelperClass pushHelp;
        pushHelp = pushDown(insertItem,mRoot);
        if ((pushHelp!=null) && pushHelp.getMoveUp())   // create a new root node
        {
            mCurrentNode.Count = 1;
            mCurrentNode.setRecord(0, pushHelp.getItem());
            mCurrentNode.child[0] = mRoot;
            mCurrentNode.child[1] = pushHelp.getItemRightChild();
            offset = (int)(mNumNodes * mNodeSize +1024);
            mRoot = mNumNodes;            
            mNumNodes++;
            writeInIndexFile(mDataFile,mCurrentNode,offset);
        }

        mNumItems++;        
    	return true;
    }
    public ArrayList<BPlusTreeNode.Item> orderItem(ArrayList<BPlusTreeNode.Item> items){
        
        for (int i = 0; i < items.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < items.size(); j++)
                if (items.get(j).getKeyField().compareTo(items.get(index).getKeyField()) < 0)
                    index = j;
      
            Item sItem = items.get(index); 
            items.set(index,items.get(i));
            items.set(i, sItem);
        }
        return items;
    }
    /**
     * writeIndex() - Read the key value pair from input file and update the Index file.
     * @param inputfile - File object to the key data file.
     * @param datafile  - FileChannel object to the index file.
     * @return - None
     * @author Lopamudra Muduli
     */
    public void writeIndex(RandomAccessFile inputfile,FileChannel datafile){
    	long lineOffset = 0;
    	long dataFieldOffset = 0;
		String line;
		int count = 0;
		
		try {
			while((line = inputfile.readLine()) != null){
				
				dataFieldOffset = lineOffset;	
				//System.out.println("Datafield offset: for line:"+ line + " : "+dataFieldOffset + "["+ Integer.toHexString((int)dataFieldOffset) +"]");
				//System.out.println("---------------------------------------");
				// Calculate the new offset
				lineOffset += line.length() + 2; // Additional byte for new line character [0d0a as per the input file]
				//4. Create an Item object and return it.
				BPlusTreeNode.Item item = new BPlusTreeNode().new Item(line.substring(0, mkeyFieldLength), dataFieldOffset);
				Insert(item);
				leafNode.add(item);
				count++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * writeInIndexFile() - Write an Object to Index file given an offset.
     * @param datafile - Index file channel object.
     * @param obj	- Java object to be written.
     * @param offset - Index file offset at which obj has to be written.
     * @return - Number of bytes written.
     * @author Lopamudra Muduli
     */
    public static int writeInIndexFile(FileChannel datafile,Object obj,long offset){
		int bytesWritten = 0;
    	try {
				byte[] recordByteArray = serialize(obj);
				//System.out.println("Writing data at offset"+offset);
				datafile.position(offset);
				bytesWritten = datafile.write(ByteBuffer.wrap(recordByteArray));
				//System.out.println("bytesWritten : "+bytesWritten);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return bytesWritten;
    }
    public void printNext(RandomAccessFile raReadInputF, String in, int rCount) {
    	int i=0 ;
    	String sResult;
    	long offset;
    	BPlusTreeNode.Item item;
    	try {
	    	for (i = 0; i < leafNode.size(); i++) {
				if (leafNode.get(i).getKeyField().compareTo(in) >= 0){
					break;
				}
			}
			while(rCount >0 && i < leafNode.size()){
				i++;
				item = leafNode.get(i);
				offset = item.getDataField();
	     		raReadInputF.seek(offset);
				sResult = raReadInputF.readLine();
				System.out.println("At "+offset + ", record: "+sResult);
				rCount--;
			}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
    /**
     * readFromIndexFile() - Read from the Index file given an offset.
     * @param datafile - Index File channel object.
     * @param offset - Index file offset at which data needs to be read.
     * @return None
     * @author Lopamudra Muduli
     */
    public void readFromIndexFile(FileChannel datafile,long offset){
    	
		int bytesRead;
    	
		try{
			byte[] readChannel = new byte[mNodeSize];
			//System.out.println("Reading data at offset"+offset);
			datafile.position(offset);
			bytesRead = datafile.read(ByteBuffer.wrap(readChannel));
			if (bytesRead != -1) {
				//System.out.println("BytesRead : "+bytesRead);
				mCurrentNode = (BPlusTreeNode)deserialize(readChannel);
			} 
		}catch(IOException e){
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * updateMetaData() - Update all the metadata for the index File. First 1K block is reserved for the meta data.
     * |-----------------------------|---------------|-----------|-------------|----------------|-----|
     * |Input File name <256>	     | Key Length<8> |numNodes<8>|numRecords<8>|root location<8>|	  |													     |	
     * |-----------------------------|---------------|-----------|-------------|----------------|-----|
     * This function is called at the end of creation Index file after parsing the input file completely.
     * @param indexF - Index file for writing the meta data.
     * 
     * @return None.
     * @author Bhakti Khatri
     */
    public void updateMetaData(FileChannel indexF){
    	int offset = 0;
    	try {
    		indexF.position(offset);
    		//1. write the Input file name
			indexF.write(ByteBuffer.wrap(mInputFName.getBytes()));
			//2. Write the key length after 256.
			//System.out.println("mkeyFieldLength : "+mkeyFieldLength +"mNumNodes: "+mNumNodes+" mNumItems: "+mNumItems+" mRoot : "+mRoot);
			offset = offset + Constants.dataFMaxLen;
			IndexMetadata mdata = new IndexMetadata(mkeyFieldLength,mNumNodes,mNumItems,mRoot);
			writeInIndexFile(indexF,mdata,offset);
			long leafNodeLoc = mNumNodes * mNodeSize + 1024;
			//Collections.sort(leafNode,new CustomComparator());
			orderItem(leafNode);
			indexF.position(leafNodeLoc);
			ByteBuffer bsize = ByteBuffer.allocate(4).putInt(serialize(leafNode).length);
			bsize.flip();
			leafNodeLoc = leafNodeLoc + indexF.write(bsize);
			writeInIndexFile(indexF, leafNode, leafNodeLoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    /**
     * readMetadata() - Reads the Metadata file and populate all the metadata.
     * @param indexF - Index file for reading the meta data.
     * @return None.
     * @author Bhakti Khatri
     */
    public void readMetadata(FileChannel indexF){
    	int bytesRead;
    	IndexMetadata mdata;
    	BPlusTreeNode node;
    	String inputFile;

    	
    	byte[] readMdata = new byte[256];
    	byte[] readNode = new byte[1024];
    	//TODO: Read the input file name first
    	try {		
			indexF.position(0);
			bytesRead = indexF.read(ByteBuffer.wrap(readMdata));
			inputFile = new String(trim(readMdata));
			mInputFName = inputFile;
			offset = offset + Constants.dataFMaxLen;
			indexF.position(offset);
			bytesRead = indexF.read(ByteBuffer.wrap(readMdata));
			if (bytesRead != -1) {
				mdata = (IndexMetadata)deserialize(readMdata);
				mkeyFieldLength = (int) mdata.mkeyFieldLen;
				mNumItems = (int) mdata.mNItems;
				mNumNodes = mdata.mNodes;
				mRoot = mdata.mRootVal;
			}
			long leafNodeLoc = mNumNodes * mNodeSize + 1024;
			indexF.position(leafNodeLoc);
			byte[] lsize = new byte[4];
			ByteBuffer dest = ByteBuffer.wrap(lsize);
			leafNodeLoc = leafNodeLoc + indexF.read(dest);
			dest.flip();
			indexF.position(leafNodeLoc);
			byte[] lnode = new byte[dest.getInt()];
			bytesRead = indexF.read(ByteBuffer.wrap(lnode));
			if (bytesRead != -1) {
				leafNode = (ArrayList<BPlusTreeNode.Item>) deserialize(lnode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    	
	/**
	 * retrieve() - Function used to check SearchKey in the table
	 * @param searchKey - Given key to be searched.
	 * @return - PushHelperClass object that has following variables.
	 * 			 true if SearchKey was found
	 *           Item - The item where SearchKey was found
	 * @author Bhakti Khatri
	 */
    public PushHelperClass retrieve(String searchKey){
	    long currentRoot;
	    int offset;
	    PushHelperClass phelper = null;
   	    currentRoot = mRoot;
	    while ((currentRoot != Constants.nullpointer))
   	    {
	        offset = (int)(currentRoot * mNodeSize +1024);//0*1024
	        readFromIndexFile(mDataFile,offset);
	        
	        SearchHelperClass searchHelpReturn = searchNode(searchKey);
	        if (searchHelpReturn.getFound())
	        {	
	        	phelper = new PushHelperClass(true, mCurrentNode.getRecord(searchHelpReturn.getLocation()), Constants.nullpointer);
	        	break;
	        }
	        else
	            currentRoot = mCurrentNode.child[searchHelpReturn.getLocation() + 1];
	    }
	    return phelper;
	}
    
    /**
    * retrieveList - Get and print the list of records followed by first match
    * @param searchKey - Given Key to be searched.
    * @param listCount - Total number of next keys to be printed.
    * @return - None
    * @author Bhakti Khatri
    */
    public void retrieveList(String searchKey,int listCount) {   	
    	//System.out.println("search key : "+ searchKey);
    	BPlusTreeNode.Item item = null;
    	BPlusTreeNode foundKeyNode;
        long currentRoot = mRoot;
        int currCount = 0;
        String sResult;
        long offset;
        boolean bKeyFound = false;
   
        try{
        	RandomAccessFile raReadInputF = new RandomAccessFile(mInputFName, "r");
		
	        while (currentRoot != Constants.nullpointer)
	        {   
	        	offset = (int)(currentRoot * mNodeSize +1024);//0*1024
		        readFromIndexFile(mDataFile,offset);
		        
		        SearchHelperClass searchHelpReturn = searchNode(searchKey);
		        if (searchHelpReturn.getFound())
    	        {	
		        	bKeyFound = true;
		        	int location = searchHelpReturn.getLocation();
		        	foundKeyNode = mCurrentNode;
		        	int j = location;
		        	//for (int j=location;j<Constants.maxNumKeys && currCount < listCount; j++) {
		        		item = foundKeyNode.getRecord(location);
		        		if (item != null) {
		        			offset = item.getDataField();
			            	raReadInputF.seek(offset);
							sResult = raReadInputF.readLine();
							System.out.println("At "+offset + ", record: "+sResult);
							currCount = currCount + 1;
						}
					//}
    	        	break;
    	        }
    	        else
    	            currentRoot = mCurrentNode.child[searchHelpReturn.getLocation() + 1];
	        }
	        if (!bKeyFound)
	        	System.out.println("Given Key not found.\nNext Keys with larger key value than the Given Key");
	        printNext(raReadInputF, searchKey, listCount-currCount);
	        raReadInputF.close();
	     	
	        }catch(IOException e) {
				e.printStackTrace();
	        }
    }
    
	 /*
	  * Utility function starts
	  */
	 public static byte[] serialize(Object obj)
	         throws IOException {
	
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(baos);
	     oos.writeObject(obj);
	     oos.flush();
	
	     return baos.toByteArray();
	 }
	 
	 public static Object deserialize(byte[] byteArray)
	         throws IOException, ClassNotFoundException {
	
	     ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArray));
	     return ois.readObject();
	 }
	 
	 public static byte[] trim(byte[] bytes)
	 {
	     int i = bytes.length - 1;
	     while (i >= 0 && bytes[i] == 0)
	     {
	         --i;
	     }
	
	     return Arrays.copyOf(bytes, i + 1);
	 }
	 /*
	  * Utility function ends
	  */
		 
    
    public static void main(String[] args) throws FileNotFoundException {
 		String input = new Scanner(System.in).nextLine();
		String[] split_dash=input.split("-");
		//System.out.println("Split dash"+Arrays.toString(split_dash));
		String[] split_space = split_dash[1].split(" ");
		//System.out.println("split_space"+Arrays.toString(split_space));
		Index idx = new Index();
		/**
		 * @author: Lopamudra Muduli
		 */
		if(split_space[0].equalsIgnoreCase("create"))
		{
			mkeyFieldLength = Integer.parseInt(split_space[3]);
			mInputFName = split_space[1];
			mIndexFName = split_space[2];
			RandomAccessFile raRead = new RandomAccessFile(mInputFName, "r");
			try {
				FileChannel raWrite = new RandomAccessFile(mIndexFName, "rw").getChannel();
				
				if(mInputFName.isEmpty()){
					System.out.println("Input file is empty");
				}
				else{
					idx.mDataFile = raWrite;
					idx.writeIndex(raRead,raWrite);
					idx.updateMetaData(raWrite);
					System.out.println("Index File created Suceessfully");
				}
				raWrite.close();
				raRead.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * @author: Bhakti Khatri
		 */
		else if(split_space[0].equalsIgnoreCase("find"))
		{
			BPlusTreeNode.Item item;
	        String searchKey;
	        mIndexFName = split_space[1];
	        searchKey = split_space[2];
			//System.out.println("Trying to search key: "+searchKey);
	        try {
	        	
				FileChannel indexF = new RandomAccessFile(mIndexFName, "r").getChannel();
				idx.mDataFile = indexF;
	        	idx.readMetadata(indexF);
	        	PushHelperClass pRetrieveReturn = idx.retrieve(searchKey);
	 	        
	 	        if ((pRetrieveReturn!= null) && pRetrieveReturn.getMoveUp())
	 	        {	
	 				RandomAccessFile raRead = new RandomAccessFile(idx.mInputFName, "r");
	 				long offset = pRetrieveReturn.getItem().getDataField();
					raRead.seek(offset);
					String sResult = raRead.readLine();
					System.out.println("At "+offset + ", record: "+sResult);
					raRead.close();

				} else {
					System.out.println("The given key ["+ searchKey+"] not found in the index");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/**
		 * @author: Lopamudra Muduli
		 */
		else if (split_space[0].equalsIgnoreCase("insert")){
	    	BPlusTreeNode item;
	    	mIndexFName = split_space[1];
	    	//mInputFName = "";
			String[] split_quotation = split_dash[1].split("\"");
			String searchKey_value = split_quotation[1].substring(0, 15);
	        try {
	        	FileChannel raWrite = new RandomAccessFile(mIndexFName, "rw").getChannel();
        		idx.mDataFile = raWrite;
	        	idx.readMetadata(raWrite);
	        	PushHelperClass pRetrieveReturn = idx.retrieve(searchKey_value);
        		if((pRetrieveReturn != null) && pRetrieveReturn.getMoveUp()){
        			System.out.println("Record already present in the input file, the request record can not be inserted.");
        		}else{
        			//System.out.println("Record ready to be inserted");
        			RandomAccessFile raReadWrite = new RandomAccessFile(idx.mInputFName, "rw");
        			raReadWrite.seek(raReadWrite.length());
        			raReadWrite.writeBytes(split_quotation[1]);
        			raReadWrite.writeBytes("\r\n");
        			raReadWrite.seek(0);
        			idx.mNumItems = 0;
        			idx.mNumNodes = 0;
        			idx.leafNode.clear();
        			idx.mRoot = Constants.nullpointer;
        			idx.writeIndex(raReadWrite,raWrite);
					idx.updateMetaData(raWrite);
					raReadWrite.close();
					raReadWrite.close();
					System.out.println("Inserted new key successfully");
        		}
        		}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
	    } 
		/**
		 * @author: Bhakti Khatri
		 */
		else if (split_space[0].equalsIgnoreCase("list")){
        	
	    	 // Retrieves list of records in that particular branch
	        mIndexFName = split_space[1];
	        String searchKey = split_space[2];
	        int listCount = Integer.parseInt(split_space[3]);	 
        	FileChannel readIndexF = new RandomAccessFile(mIndexFName, "r").getChannel();
        	idx.mDataFile = readIndexF;
        	idx.readMetadata(readIndexF);
	        idx.retrieveList(searchKey,listCount);
	     	try {
				readIndexF.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
        }
		
    }
	
}

   
