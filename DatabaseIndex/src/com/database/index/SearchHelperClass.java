/**
 * @author Lopamudra Muduli
 * This class used for search function to return the boolean value whether the search key is present in the index file and if yes
 * then returns its the location in the node.
 *
 */
package com.database.index;

final class SearchHelperClass {
	    private final boolean found;
	    private final int location;

	    public SearchHelperClass(boolean found, int location) {
	        this.found = found;
	        this.location = location;
	    }
	    public boolean getFound() {
	        return found;
 	    }

	    public int getLocation() {
	        return location;
	    }

}
