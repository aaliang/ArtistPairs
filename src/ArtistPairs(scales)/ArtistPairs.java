import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
public class ArtistPairs {

	//these are the artists that have been listed 50 times or more
	private static ArrayList <ArtistUser> MoreThanThreshold;
    
	private static HashMap <Integer, ArrayList<Integer>> SparseUserMap;
	
	//for the sake of this exercise, this is 50, but it is tunable
	private static int THRESHOLD;
	
	private static void buildListAndMap(String filename)
	{
		//the key is a string which is the artist's name, ArtistUser is a data structure which lists the users that have named this artist in their list
		HashMap <String, ArtistUser> ArtistMap = new HashMap <>();

		//this creates a mapping of artist names to users that have listed the artist
		//additionally it will keep track of artists that have been named 50 (the THRESHOLD) or more times
		
		MoreThanThreshold = new ArrayList <>();
		
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename.trim()));
			
            int userIndex = 0;
  
            while(in.ready()){
            	//read each line (user) and detokenize the string
            	
                String aLine = in.readLine();
                aLine = new String(aLine.getBytes(), "UTF-8");
                
                String someArtists[] = aLine.split(",");
                
                for(int i = 0; i < someArtists.length; i++){
                	
                	String artist = someArtists[i].trim();                	    	
                	
                    if(ArtistMap.containsKey(artist)){
                    	//we've already seen this Artist before!                  	
                    	
                    	ArtistUser anArtist = ArtistMap.get(artist);
                    	
                    	anArtist.users.add(userIndex);
                    	
                    	//the moment the userCount hits 50 (THRESHOLD), add it to the MoreThanThreshold list
                        if(anArtist.userCount() == THRESHOLD){

                        	MoreThanThreshold.add(anArtist);
                        }                  
                    }

                    else //this is the first time a new Artist is encountered!
                    {                   	
                    	ArtistUser newArtist = new ArtistUser(artist, userIndex);
                    	ArtistMap.put(artist, newArtist);
                    }
                   
                }                
                userIndex++;
            }
            
            
            //sparsify, basically what we are doing here is taking that user-artist list that we got as input, except keeping only the artists with 50+ values
            //and putting it into a hashmap
            
            SparseUserMap = new HashMap <> ();
            //keyed by the user index
            
            for(int i = 0; i < MoreThanThreshold.size(); i++)
            {
            	ArtistUser anArtist = MoreThanThreshold.get(i);
            	
            	for(int j = 0; j < anArtist.users.size(); j++)
            	{
            		int userJIndex = anArtist.users.get(j);
            		
            		if(!SparseUserMap.containsKey(userJIndex))
            		{
            			ArrayList <Integer> initialArtistList = new ArrayList<>();
            			SparseUserMap.put(userJIndex, initialArtistList);
            		}
            		SparseUserMap.get(userJIndex).add(i);
            	}
            }
            
            
		}
		catch(IOException iox)
        {
            System.out.println("cannot open file" + filename);
            System.exit(1);
        }
        
	}
	
	private static void combineAndOutput(){
		
		//this will give all possible combinations of artists that appear in more than 50 user lists, using the MoreThanThreshold list
		//this is a whole lot better than going through every single artist that appears
		
		
		int lineNumber = 1;
		
		//int tcount = 0;
		
		int sizeOfList = MoreThanThreshold.size();
		
		//we need this so we don't do repeats
		HashSet <Integer> processed = new HashSet <>();
		
		
		for(int i=0; i < sizeOfList; i++)
		{
			processed.add(i);
			
			int iListSize = MoreThanThreshold.get(i).userCount();
			
			LinkedHashMap <Integer, Integer> lhmap = new LinkedHashMap<>();
			//keyed by an integer that represents the index of the artist in MoreThanThreshold
			
			for(int j = 0; j < iListSize; j++)
			{
				//get a user listed under an artist in the common artists list (morethanthreshold)
				Integer aUserId = MoreThanThreshold.get(i).users.get(j);
				
				//now we use this id to find the artist nodes that are connected to the user via SparseUserMap (this is guaranteed to be bound by the threshold value
				ArrayList <Integer> artistsListedByUser = SparseUserMap.get(aUserId);
				
				int aluSize = artistsListedByUser.size();
				for(int k = 0; k < aluSize; k++)
				{
					int artistId = artistsListedByUser.get(k);
					if(processed.contains(artistId))
						continue;
					int initialCount = 0;
					
					if(lhmap.containsKey(artistId))
					{
						initialCount = lhmap.get(artistId);
					}
					lhmap.put(artistId, initialCount + 1);		
				}
			}
			
			
			//at the end of this loop, we should know all the other artists that are compatible
			
			//iterate over stuff, do lookups
			
			Iterator iter = lhmap.entrySet().iterator();
			while(iter.hasNext())
			{
				Entry thisEntry = (Entry) iter.next();
				
				Integer artistId = (Integer) thisEntry.getKey();
				Integer artistCount = (Integer) thisEntry.getValue();
				
				if(artistCount >= 50)
				{
					String otherName = MoreThanThreshold.get(artistId).artistName;
					
					String name = MoreThanThreshold.get(i).artistName;
					
					System.out.println(name + "," + otherName);
				}
			}

		}
		
	}
    
    public static void main(String[] argc) {
    	
    	String[] args = {"Artist_lists_small.txt","50"};
    	
    	if(args.length != 2)
    	{
    		System.out.println("usage: java ArtistPairs [input file] [threshold]");
    		System.exit(1);
    	}
    	try
    	{
    		THRESHOLD = Integer.parseInt(args[1]);
    	}
    	catch(Exception e)
    	{
    		System.out.println("usage: java ArtistPairs [input file] [threshold]");
    		System.out.println("	(threshold must be numeric)");
    		System.exit(1);
    	}
    	
    	//reads the input file and prepares the data structures ArtistMap and MoreThanFifty
    	buildListAndMap(args[0]);
    	
    	//do work
        combineAndOutput();
    }
}


