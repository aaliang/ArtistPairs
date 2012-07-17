ArtistPairs

My solution is in the directory ArtistPairs(scales)

Here is a walkthrough, I guess of what's going on. the objective of this exercise is stated below the comments

/* Andy Liang, a.liang1989@gmail.com 
*
* Java JDK 7 is assumed 
*
* To build: javac ArtistPairs.java
*
* To run: java ArtistPairs [input file] [threshold]
* Output is dumped to STDOUT,
* To run and dump output to a file: java ArtistPairs [input file] [threshold] > output
*
* ex. java ArtistPairs Artist_lists_small 50
*
* 
* This is a short description of what is going on from the top level:
* 
* 1) As we read in the input file we count the number of occurrences of each artist throughout the data set.
*   Since we are looking for pairs of artists that occur more than 50 times, we maintain a list of artists that
*   we have seen more than 50 times, and the users that listed them.
* 
* Time and Space complexity are both O(n) for this step, where n is the number of users taken as input
* 
* 2) After all users are processed we take the list of artists that have occurred more than 50 times and map users
*	  to lists of those artists that they have listed (essentially a pruned version of the input file) 
* 
* Time and Space complexity are both O(n)
* 
* 3) We now go through the artist-users list that we generated in step 1 and for each artist, we take the list of users
*   that have listed that artist in their list, use the map that we generated in step 2 to increment a hashmap that counts 
*    each other artist that they have listed in their lists. i.e. the intersection of these sets. If any of these counters
*    exceeds 50, we add the artist to another arraylist that holds these values, and at the end of the looping for each artist
*    we print out the pair.
*    
*   Time complexity: 
* 
* O(m*n*t), where t is the maximum number of artists that a user can list -- which is a constant (50), and m 
* is the number of artists that appear more than the threshold (also 50) times.
* 
*	 this is really just 
*	 O(m*n). however m is bounded by n, so this is really
*
*	 O(n^2)
*	
*  Space complexity:
*  
*  At each iteration in the artist loop:
*  
*  O(n) to build up each hashmap, however this will reset every iteration
*
*	This gives us an overall time complexity of O(n^2) which scales reasonably well since the actual value of m will be no greater than
*	n/t. on extremely large data sets however it might be prudent to just do random sampling instead of iterating over
*  the entire set.
*  
*
*/


===========
The attached utf-8 encoded text file contains the favorite musical artists of 1000 users from LastFM. Each line is a list of up to 50 artists, formatted as follows:

 

Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,Super Furry Animals\n Band of Horses,Iggy Pop,The Velvet Underground,Radiohead,The Decemberists,Morrissey,Television\n

etc.

 

Objective is to produce a list of pairs of artists which appear TOGETHER in at least fifty different lists. For example, in the above sample, Radiohead and Morrissey appear together twice, but every other pair appears only once. Your program should output the pair list to stdout in the same form as the input (eg Artist Name 1, Artist Name 2\n).
