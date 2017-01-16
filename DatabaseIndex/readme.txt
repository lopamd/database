This project creates B+ tree indexing thats simulates the database implementation.
It will read a text file containing data and build the index, treating the first 15
columns as the key. The structure of the index is as follows: First 256 bytes: Name of the text file you
have indexed. This must be blank-filled on the right.
The rest of the file is 1K blocks of index data, according to the way a B+ tree is structured.

This program runs entirely from the command line. Following functionalities are implemented.

1. Create an index.  This takes three parameters: The name of a text file and the name of an
output index that is created, as well as how many byte of the record, starting from the first
position, are considered to be the key.  If the output file exists, overwrite it.  Do not ask. 

For example: java btree -create CS6360Asg5Data.txt CS6360Asg5.indx 15
Creates an index with a key length of 15 bytes.  

2. Find a record by key. This displays the entire record, including the key, and gives its
position, in bytes, within the file.  If the key is not in your index, the program must give
a message to that effect.

For example, for this command line:
 java btree -find MyIndex.indx 11111111111111A

program would return a message that the key was not found.  The command line 
 java btree -find MyIndex.indx  64541668700164B

would return:  At 2127, record:  64541668700164B ANESTH, BIOPSY OF NOSE
  (This presumes that the byte offset of this record from the start of the file is 2127).
3.Insert a new text record.  As with creating a new index, the first 15 bytes must contain
a unique key.  If the key is not in the index, write the record at the end of the data file
(remembering the position, since you will need it), then inserting the key into your index
structure with the pointer to the start of the new record.  Return a message indicating
success and the position of the new record in the text file.  If the key is already in the
index, return a message to that effect and do not insert the record.

For example, the following command line would attempt to insert a record into the
file. This should return error as it is already in the index.
  INDEX -insert MyIndex.indx “64541668700164B Some new Record"

This command line would return success:
    INDEX -insert MyIndex.indx "11111111111111D Some new Record"

4. List sequential records.  The program must show the record containing the
given key and the next n records following it, if there are that many.  If no
record exists with the given key, show records that contain the next-larger key
and give a message that the actual key was not found.  As with the -find
function, show the records in order by key and their position within the text
file.  Obviously this must traverse the tree in key order.  Here is the command
line:
  INDEX -list <index filename> <starting key> <count>

