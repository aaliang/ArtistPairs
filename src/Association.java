import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class Association {

    //remember to add a unicode detector later...
    //c1 public static HashMap <String, Integer> artistMap = new HashMap <> ();
	public static HashMap <String, ArtistUser> artistMap = new HashMap <> ();
    //c1 public static ArrayList <String> moreThanFifty = new ArrayList <> ();
	public static ArrayList <ArtistUser> moreThanFifty = new ArrayList <> ();
    
	public static int threshold = 50;
    
    public static void main(String[] args) {
    
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("Artist_lists_small.txt"));
			
            int userIndex = 0;
            
            while(in.ready()){
            	//read each line (user) and detokenize the string
            	
                String aLine = in.readLine();
                aLine = new String(aLine.getBytes(), "UTF-8");
                
                String someArtists[] = aLine.split(",");
                
                
                for(int i = 0; i < someArtists.length; i++){
                	
                    if(artistMap.containsKey(someArtists[i])){
                    	//we've already seen this Artist before!
                    	
                        //c1 count = artistMap.get(someArtists[i]);
                    	
                    	ArtistUser anArtist = artistMap.get(someArtists[i]);
                    	
                    	anArtist.users.add(userIndex);
                    	
                    	//the moment the userCount hits 50, add it to the moreThanFifty list
                        if(anArtist.userCount() == threshold){
                            //moreThanFifty.add(someArtists[i]);
                        	moreThanFifty.add(anArtist);
                        }                  
                    }
                    //c1
                    //count++;
                    //artistMap.put(someArtists[i], count);
                    //
                    else //this is the first time a new Artist is encountered!
                    {                   	
                    	ArtistUser newArtist = new ArtistUser(someArtists[i], userIndex);
                    	artistMap.put(someArtists[i], newArtist);
                    }
                   
                }
                
                userIndex++;
            }
            in.close();
            
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("50.txt"), "UTF8");
            for(int i = 0; i < moreThanFifty.size(); i ++){
            	//out.write(moreThanFifty.get(i).artistName + ":");
            	//System.out.print(moreThanFifty.get(i).artistName + ":");
            
            	
            	int a = moreThanFifty.get(i).users.get(0).intValue();
            	//out.write(a);
            	
            	
            	//System.out.print(a);
            	for(int j = 1; j < moreThanFifty.get(i).users.size(); j++)
            	{
            		//out.write(" " + moreThanFifty.get(i).users.get(j));
            		//System.out.print(" " + moreThanFifty.get(i).users.get(j));
            
            		
            	}
            	//System.out.print("\n");
            	//out.write("\n");
            }
            out.close();
            
        }
        catch(IOException iox)
        {
            System.out.println("cannot open file");
        }
        
        //System.out.println("there are " + artistMap.size() + " artists");
        //System.out.println("there are " + moreThanFifty.size() + " artists on more than 50 lists");
        
        int sizeOfList = moreThanFifty.size();
        
        try
        {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("pairs.txt"), "UTF8");
            int lineNumber = 1;
            
            //this will give all possible combinations of artists that appear in more than 50 user lists,
            //this is a whole lot better than going through every single artist that appears
            for (int i = 0; i <  sizeOfList - 1; i++)
            {
                for(int j = i + 1; j < sizeOfList; j++)
                {
                    //indices (i, j) is a pair
                    //out.write(lineNumber + ": " + moreThanFifty.get(i).artistName + "," + moreThanFifty.get(j).artistName);
                    //out.write("\n");
                    lineNumber++;
                    
                    int iListSize = moreThanFifty.get(i).userCount();
                    int jListSize = moreThanFifty.get(j).userCount();
                    
                    int iIndex = 0;
                    int jIndex = 0;
                    int pairCount = 0;
                    
                    //stepping down the lists, kind of like mergesort i guess
                    do
                    {
                    	int iUserId = moreThanFifty.get(i).users.get(iIndex);
                    	int jUserId = moreThanFifty.get(j).users.get(jIndex);
                    	
                    	if(iUserId < jUserId)
                    		iIndex++;
                    	else if(iUserId > jUserId)
                    		jIndex++;
                    	else //they're equal
                    	{
                    		pairCount++;
                    		iIndex++;
                    		jIndex++;
                    	}
                    } while(iIndex < iListSize && jIndex < jListSize && pairCount < threshold);
                    
                    if(pairCount >= 50)
                    {
                    	System.out.println(moreThanFifty.get(i).artistName + "," + moreThanFifty.get(j).artistName);
                    }
                }
            }
            out.close();
            
        }
        catch(IOException iox)
        {
            System.out.println("cannot write to file");
        }
        
    }
}


