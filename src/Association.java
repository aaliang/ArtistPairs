import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class Association {

    //the key is a string which is the artist's name, ArtistUser is a data structure which lists the users that have named this artist in their list
    private static HashMap <String, ArtistUser> ArtistMap;

    //these are the artists that have been listed 50 times or more
    private static ArrayList <ArtistUser> MoreThanThreshold;

    //for the sake of this exercise, this is 50, but it is tunable
    private static int THRESHOLD;

    private static void buildListAndMap(String filename)
    {
        //this creates a mapping of artist names to users that have listed the artist
        //additionally it will keep track of artists that have been named 50 (the THRESHOLD) or more times

        ArtistMap = new HashMap <>();
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

                    if(ArtistMap.containsKey(someArtists[i])){
                        //we've already seen this Artist before!

                        ArtistUser anArtist = ArtistMap.get(someArtists[i]);

                        anArtist.users.add(userIndex);

                        //the moment the userCount hits 50 (THRESHOLD), add it to the MoreThanThreshold list
                        if(anArtist.userCount() == THRESHOLD){

                            MoreThanThreshold.add(anArtist);
                        }
                    }

                    else //this is the first time a new Artist is encountered!
                    {
                        ArtistUser newArtist = new ArtistUser(someArtists[i], userIndex);
                        ArtistMap.put(someArtists[i], newArtist);
                    }

                }
                userIndex++;
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
        for (int i = 0; i <  sizeOfList - 1; i++)
        {
            for(int j = i + 1; j < sizeOfList; j++)
            {
                //indices (i, j) is a candidate pair

                lineNumber++;

                int iListSize = MoreThanThreshold.get(i).userCount();
                int jListSize = MoreThanThreshold.get(j).userCount();

                int iIndex = 0;
                int jIndex = 0;
                int pairCount = 0;

                //stepping down the lists, kind of like mergesort i guess
                do
                {
                    int iUserId = MoreThanThreshold.get(i).users.get(iIndex);
                    int jUserId = MoreThanThreshold.get(j).users.get(jIndex);

                    if(iUserId < jUserId)
                        iIndex++;
                    else if(iUserId > jUserId)
                        jIndex++;
                    else //they're equal, advance the indices
                    {
                        pairCount++;
                        iIndex++;
                        jIndex++;
                    }
                } while(    (iIndex < iListSize)    //if any of the below conditions are false, there is no point in proceeding
                        &&  (jIndex < jListSize)
                        &&  (pairCount < THRESHOLD) //
                        &&  (pairCount + iListSize - iIndex >= THRESHOLD)   //below two are slight optimizations, basically there is no sense proceeding if there are not enough things left
                        &&  (pairCount + jListSize - jIndex >= THRESHOLD)
                );


                if(pairCount >= 50)
                {
                    //tcount++;
                    System.out.println(MoreThanThreshold.get(i).artistName + "," + MoreThanThreshold.get(j).artistName);
                    //this just dumps to std out. use I/O redirection to print to a file.
                }
            }
        }

        //System.out.println(tcount); this was just to check how many times I was printing
    }

    public static void main(String[] args) {

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
            System.out.println("    (threshold must be numeric)");
            System.exit(1);
        }

        //reads the input file and prepares the data structures ArtistMap and MoreThanFifty
        buildListAndMap(args[0]);

        //do work
        combineAndOutput();
    }
}


