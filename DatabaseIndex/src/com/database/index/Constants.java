/**
 * @author Bhakti Khatri
 * This class used for Constants value fields used in the Index.java 
 *
 */
package com.database.index;

public class Constants {
	final static int maxNumKeys = 4;   // max number of keys in a node
	final static int maxKeyFieldLength = 15;
	final static int order = maxNumKeys + 1;  // Order of the B Tree
	final static int minKeys = 2;    // min number of keys in a node
	final static long nullpointer = -1L;   // the L indicates a long int
	final static int dataFMaxLen = 256; // Max allowed length of Data in a line
	final static int STRING_MAX_SIZE = 256;
}
