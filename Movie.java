import java.util.*;

public class Movie implements MovieInterface {
	private String title, director;
	private int year;
	private double rating;
	private ArrayList<String> actors;
	
	/**
	 * Constructor for Movie class.
	 * 
	 * @param       title 		title of the movie
	 * @param       year 		year the movie was released
	 * @param       director 	director of the movie
	 * @param       actors 		actors in the movie
	 * @param       rating 		rating of the movie
	 */
	public Movie(String title, int year, String director, ArrayList<String> actors, double rating) {
		this.title = title;
		this.year = year;
		this.director = director;
		this.actors = actors;
		this.rating = rating;
	}

	/**
	 * Gets title of the movie
	 * 
	 * @return      title of the movie
	 */
	public String getTitle() { return title; }		

	/**
	 * Gets year the movie was released
	 * 
	 * @return      year movie was released
	 */
	public int getYear() { return year; }

	/**
	 * Gets director of the movie
	 * 
	 * @return      director of the movie
	 */
	public String getDirector() { return director; }	

	/**
	 * Gets actors in the movie
	 * 
	 * @return      actors in the movie
	 */		
	public String [] getActors() { return actors.toArray(new String[0]); }		

	/**
	 * Gets rating of the movie
	 * 
	 * @return    	rating of the movie
	 */
	public double getRating() { return rating; }
}