package com.database.index;
/**
 * @author Lopamudra Muduli
 * This class used for pushUp function in Index.java.
 * @return moveUp : Boolean value when split required during over flow of node
 * @ item : Item(dataField and keyField which should be moved to the parent node after the split)
 * @ itemRightChild : right child of the above item
 *  
 */

final class PushHelperClass {
		private final boolean moveUp;
		private final BPlusTreeNode.Item item ;
		private final long itemRightChild;
		
		public PushHelperClass (boolean moveUp,BPlusTreeNode.Item item,long itemRightChild){
			this.moveUp = moveUp;
			this.item = item;
			this.itemRightChild = itemRightChild;
		}
		
		public boolean getMoveUp(){
			return this.moveUp;
		}
		
		public BPlusTreeNode.Item getItem(){
			return this.item;
		}
	    
	    public long getItemRightChild(){
	    		return itemRightChild;
	    }
}
