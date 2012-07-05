import java.util.ArrayList;

public class ArtistUser
	{
		public ArtistUser(String name)
		{
			artistName = name;
			users = new ArrayList <Integer> ();
		}
		
		//this is the constructor that should be used...
		public ArtistUser(String name, int userIndex)
		{
			this(name);
			users.add(userIndex);
		}
		
		
		//just the name of the artist
		public String artistName;
		
		//list of users (indices) that have listed this artist as their favorite.
		public ArrayList <Integer> users;
		
		public int userCount()
		{
			return users.size();
		}
		
	}