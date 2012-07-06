ArtistPairs
===========
The attached utf-8 encoded text file contains the favorite musical artists of 1000 users from LastFM. Each line is a list of up to 50 artists, formatted as follows:

 

Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,Super Furry Animals\n Band of Horses,Iggy Pop,The Velvet Underground,Radiohead,The Decemberists,Morrissey,Television\n

etc.

 

Write a program that, using this file as input, produces a list of pairs of artists which appear TOGETHER in at least fifty different lists. For example, in the above sample, Radiohead and Morrissey appear together twice, but every other pair appears only once. Your program should output the pair list to stdout in the same form as the input (eg Artist Name 1, Artist Name 2\n).

 

You MAY return an approximate solution, i.e. lists which appear at least 50 times with high probability, as long as you explain why this tradeoff improves the performance of the algorithm.

 

Please include, either in comments or in a separate file, a brief description of the run-time and space complexity of your algorithm.

 

Your solution should preferably be implemented in Java, Python, Ruby, Ansi C, Ansi C++, Perl. Please include compilation/runtime instructions with your code. Other languages may be considered on a case-by-case basis.