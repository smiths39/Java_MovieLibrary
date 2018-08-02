import java.util.*;

public interface MovieInterface {
 	public String getTitle();			//gets the title of the film
	public int getYear();				//gets the year the film was released
	public String getDirector();		//gets the director of the film
	public String [] getActors();		//gets a list of actors in the film
	public double getRating();			//gets a rating (out of 10) for the film
}