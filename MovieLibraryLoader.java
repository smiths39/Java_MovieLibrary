import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A utility class for loading and saving a txt file containing a movie library
 * 
 * @author joel
 * @version Fa2014
 */
public class MovieLibraryLoader
{
	//constants for file format
	private static final String FIELD_DELIM = ";";
	private static final String ACTOR_DELIM = ",";

	/**
	 * Parses a movie from a semicolon-separated entry
	 * @param input The string representing the movie
	 * @return the Movie object, or null if there was an error parsing
	 */
	private static Movie parseMovieEntry(String input)
	{
		try{ //in case we fail to parse anything, such as format didn't work
			String[] fields = input.split(FIELD_DELIM);
			String title = fields[0].trim();
			int year = Integer.parseInt(fields[1].trim());
			String director = fields[2].trim();
			String[] actorArray = fields[3].split(ACTOR_DELIM);
			ArrayList<String> actors = new ArrayList<String>();
			for(int i=0; i<actorArray.length; i++)
				actors.add(actorArray[i].trim()); //add all the actors to the list
			double rating = Double.parseDouble(fields[4]);

			return new Movie(title, year, director, actors, rating);		
		}
		catch(Exception e){
			System.out.println("Failed to parse film: "+input);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Loads a list of movies from a text file (specified by the user)
	 * @param filename The path to the file to load. If null, then the user is prompted for to pick file using a GUI JFileChooser
	 * @return The list of movies loaded from the library
	 */
	public static ArrayList<Movie> loadMoviesFromFile(String filename)
	{
		ArrayList<Movie> movieList = new ArrayList<Movie>();

		File f = null; //the file we will read
		if(filename != null) //if given a filename, load that
			f = new File(filename);
		else //otherwise, pick a file
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt")); //for choosing txt files
			int returnVal = chooser.showOpenDialog(chooser);
			if(returnVal == JFileChooser.APPROVE_OPTION) //if they picked something
				f = chooser.getSelectedFile();
		}

		if(f != null) //make sure we have picked something
		{
			try {
				Scanner sc = new Scanner(f); //parse it with Scanner
				while(sc.hasNextLine())
				{
					Movie movie = parseMovieEntry(sc.nextLine());
					if(movie != null)
						movieList.add(movie);
				}
				sc.close();
			}
			catch(IOException e) {
				System.out.println("Error loading library from file: "+e);
			}
		}

		return movieList;
	}

	/**
	 * Returns a semicolon-separated list of movie details, for use in writing the entry to file
	 * @return A string representation of the movie
	 */
	private static String getEntryString(Movie movie)
	{
		StringBuilder s = new StringBuilder();

		s.append(movie.getTitle());
		s.append(FIELD_DELIM);
		s.append(movie.getYear());
		s.append(FIELD_DELIM);
		s.append(movie.getDirector());
		s.append(FIELD_DELIM);
		String[] actors = movie.getActors();
		for(int i=0; i<actors.length; i++)
		{
			s.append(actors[i]);
			if(i<actors.length-1) //no delimiter after the last actor
				s.append(ACTOR_DELIM); 
		}
		s.append(FIELD_DELIM);
		s.append(movie.getRating());

		return s.toString();
	}
	
	/**
	 * Saves the given list of movies to a file (specified by the user)
	 * @param movies A list of movies to save
	 * @param filename The path to the file to save to. If null, then the user is prompted for to pick file using a GUI JFileChooser
	 */
	public static void saveMoviesToFile(ArrayList<Movie> movies, String filename)
	{
		File f = null; //the file we will read
		if(filename != null) //if given a filename, load that
			f = new File(filename);
		else //otherwise, pick a file
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt")); //for choosing txt files
			int returnVal = chooser.showSaveDialog(chooser);
			if(returnVal == JFileChooser.APPROVE_OPTION) //if they picked something
				f = chooser.getSelectedFile();
		}

		if(f != null) //make sure we have picked something
		{
			try{
				PrintWriter pw = new PrintWriter(f);
				for(Movie movie : movies)
					pw.println(getEntryString(movie));
				pw.close();
			}
			catch(Exception e) {
				System.out.println("Error saving library to file: "+e);
			}
		}
	}

}
