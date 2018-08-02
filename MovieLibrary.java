import java.util.*;
import java.io.*;

public class MovieLibrary {
	
	/**
	 * Lists the titles of movies in library
	 *
	 * @param 	movieList		list containing existing movies in library
	 */
	public static void getListOfTitles(ArrayList<Movie> movieList) {
		for (Movie movieTitle : movieList) {
			System.out.println(movieTitle.getTitle());
		}

		System.out.println();
	}

	/**
	 * Displays details of a movie with a given title
	 * 
	 * @param 	movieList		list containing existing movies in library
	 * @param 	movieTitle		title of movie to retrieve details of 	
	 */
	public static void getMovieDetails(ArrayList<Movie> movieList, String movieTitle) {
		for (Movie movie : movieList) {
			if (movie.getTitle().toUpperCase().trim().equals(movieTitle.toUpperCase().trim())) {
				System.out.printf("%-15s %1s %n", "Title:", movie.getTitle());
				System.out.printf("%-15s %1s %n", "Year:", movie.getYear());
				System.out.printf("%-15s %1s %n", "Director:", movie.getDirector());
				System.out.printf("%-15s %1s %n", "Actors:", String.join(",", movie.getActors()));
				System.out.printf("%-15s %1s %n%n", "Rating:", movie.getRating());
				break;
			}
		}
	}

	/**
	 * Lists all movies starring a given actor
	 * 
	 * @param 	movieList		list containing existing movies in library
	 * @param 	actor 			name of actor starring in movies 	
	 */
	public static void getMovieWithActor(ArrayList<Movie> movieList, String actorName) {
		List<String> movieActors;
		List<String> actorMovies = new ArrayList<>();

		for (Movie movie : movieList) {
			movieActors = Arrays.asList(movie.getActors());  
			
			if (movieActors.contains(actorName)) {
				actorMovies.add(movie.getTitle());
			} 
		}

		for (String movieTitle : actorMovies) {
			System.out.println(movieTitle);
		}

		System.out.println();
	}

	/**
	 * Reports the movie with highest rating
	 * 
	 * @param 	movieList		list containing existing movies in library
	*/
	public static void getMovieWithHighestRating(ArrayList<Movie> movieList) {
		double highestRating = 0.0;
		String highestRatedMovie = "";
		double movieRating;

		for (Movie movie : movieList) {
			movieRating = movie.getRating();

			if (movieRating > highestRating) {
				highestRating = movieRating;
				highestRatedMovie = movie.getTitle();
			}
		}

		System.out.println(highestRatedMovie + "\n");
	}

	/**
	 * Add a new movie to the library
	 * Duplicates are disallowed
	 * 
	 * @param 	movieList		list containing existing movies in library
	 */	
	public static void addMovie(ArrayList<Movie> movieList, String title, int year, String director, ArrayList<String> allActors, double rating) {
		boolean duplicateFound = false;
		Movie newMovie = new Movie(title, year, director, allActors, rating);

		for (Movie movie : movieList) {
			if (movie.getTitle().equals(title) && movie.getYear() == year) {
				duplicateFound = true;
				break;
			}
		} 

		if (duplicateFound) {
			System.out.println("\nINFO: Movie already exists in library.\n");
		} else {
			movieList.add(newMovie);
			System.out.println("\nINFO: Movie added to library.\n");
		}
	}

	/**
	 * Remove a movie from the library
	 * 
	 * @param 	movieTitle	title of movie to remove from library
	 * @param 	year		year movie was released 	
	 */	
	public static void removeMovie(ArrayList<Movie> movieList, String title, int year) {
		boolean movieFound = false;

		for (Movie movie : movieList) {
			if (movie.getTitle().equals(title) && movie.getYear() == year) {
				movieList.remove(movie);
				movieFound = true;
				break;
			}
		} 

		if (!movieFound) {
			System.out.println("\nINFO: Movie does not exist in library.\n");
		} else {
			System.out.println("\nINFO: Movie removed from library.\n");
		}
	}
}