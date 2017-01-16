/**
*@author Bhakti Kshatriya
* Class used to store the metadata in first 1024 field in the index file
*/

package com.database.index;

import java.io.Serializable;

public class IndexMetadata implements Serializable{

		private static final long serialVersionUID = 1L;
		long mkeyFieldLen;
		long mNItems;
		long mNodes;
		long mRootVal;
		public IndexMetadata(long keyL, long nNodes,long nItems, long rootVal) {
			
		mkeyFieldLen = keyL;
		mNItems = nItems;
		mNodes = nNodes;
		mRootVal = rootVal;
		}
}
