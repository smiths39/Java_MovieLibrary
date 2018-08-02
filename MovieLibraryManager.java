import java.util.*;
import java.io.*;

public class MovieLibraryManager {

	// Global variables
	private static final String FILENAME = "movies.txt";
	private static ArrayList<String> menuOptions;
	private static ArrayList<Movie> movieList = new ArrayList<>(); 
	private static Scanner scanner = new Scanner(System.in);
	private static MovieLibrary movieLibrary = new MovieLibrary();
	private static MovieLibraryLoader movieLibraryLoader = new MovieLibraryLoader();

	/**
	 * Displays main menu to the console
	 * Performs operation based on user input
	 */
	private static void mainMenu() {
		boolean exitMenuRequest = false;
		Object userSelection;

		addMenuOptions();
		clearConsole();

		while (!exitMenuRequest) {
			printMenu();

			userSelection = userMenuSelection();

			if (isNumeric(userSelection.toString())) {
				int menuOption = Integer.parseInt(userSelection.toString());
			
				if (menuOption != -1) {
					if (menuOption == 9) {
						// Exit system
						exitMenuRequest = true;
					} else {
						clearConsole();
						performMenuOptions(menuOption);
					}
				}
			} else if (userSelection instanceof String) {
				// Convert object to string type
				String menuOption = (String) userSelection;
				
				if (menuOption.equals("EXIT")) {
					exitMenuRequest = true;
				}
			}
		}
	}

	/**
	 * Add menu options to ArrayList
	 */
	private static void addMenuOptions() {
		// Initialise ArrayList
		menuOptions = new ArrayList<>();

		menuOptions.add("List movies in library");
		menuOptions.add("Look up a movie by title");
		menuOptions.add("Look up movies by actor");
		menuOptions.add("Look up highest rated film");
		menuOptions.add("Add a movie to the library");
		menuOptions.add("Remove a movie from the library");
		menuOptions.add("Load a library from a file");
		menuOptions.add("Save movie library to a file");
		menuOptions.add("Exit system");
	}

	/**
	 * Displays menu options to the console.
	 */
	private static void printMenu() {
		// Print menu header
		System.out.println("Welcome to the movie library.");
		System.out.println("Select an option: ");

		// Print menu options within ArrayList
		for (int i = 0; i < menuOptions.size(); i++) {
			System.out.println("\t" + (i + 1) + ". " + menuOptions.get(i));
		}
	}

	/**
	 * User selection is read in via standard input by the user. 
	 * If input is outside the range of menu options provided or invalid keyword, -1 is returned.
	 * 
	 * @return      (in)valid option chosen by the user
	 */
	private static Object userMenuSelection() {
		// Read in user selection
		String userInput = scanner.nextLine();

		if (isNumeric(userInput)) {
			// Convert object to integer type
			int menuOption = Integer.parseInt(userInput);

			// Check input is within valid option boundaries 
			if (menuOption > 0 && menuOption <= menuOptions.size()) {
				return menuOption;
			}	
		} else {
			// Convert object to string type
			String menuOption = (String) userInput;
			menuOption = menuOption.toUpperCase().trim();

			// Check if user entered exit keyword
			if (menuOption.equals("EXIT")) {
				return menuOption;
			}
		}
		
		return -1;
	}

	/**
	 * Determines if string contains only numeric values
	 * 
	 * @return      true or false depending if string contains only numeric values
	 */
	private static boolean isNumeric(String val) {
		return val.chars().allMatch(Character::isDigit);
	}

	/**
	 * Clears the console screen
	 */
	private static void clearConsole() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Call method corresponding to menu option selected by user
	 *
	 * @param 	menuOption 	menu option selected by user
	 */
	private static void performMenuOptions(int menuOption) {
		printOptionTitle(menuOption);

		switch (menuOption) {
			case 1:
				// List movies in library
				movieLibrary.getListOfTitles(movieList);
				break;

			case 2:
				// Look up a movie by title
				System.out.print("\nMovie Title: ");
				String movieTitle = scanner.nextLine();

				clearConsole();
				printOptionTitle(menuOption);
				
				movieLibrary.getMovieDetails(movieList, movieTitle);
				break;

			case 3:
				// Look up movies by actor
				System.out.print("\nActor: ");
				String actorName = scanner.nextLine();

				clearConsole();
				printOptionTitle(menuOption);

				movieLibrary.getMovieWithActor(movieList, actorName);
				break;

			case 4:
				// Look up highest rated film
				movieLibrary.getMovieWithHighestRating(movieList);
				break;

			case 5:
				// Add a movie to the library
				ArrayList<String> allActors = new ArrayList<>();

				System.out.print("Movie Title: ");
				String title = scanner.nextLine();

				System.out.print("Year: ");
				int year = scanner.nextInt();

				// Consume last new line not handled by nextInt()
				scanner.nextLine(); 

				System.out.print("Director: ");
				String director = scanner.nextLine();

				System.out.print("Actors: ");
				String actors = scanner.nextLine();
				allActors.addAll(Arrays.asList(actors.split(",")));

				System.out.print("Rating: ");
				double rating = scanner.nextDouble();

				// Consume last new line not handled by nextInt()
				scanner.nextLine(); 

				movieLibrary.addMovie(movieList, title, year, director, allActors, rating);
				break;

			case 6:
				// Remove a movie from the library
				System.out.print("Movie Title: ");
				String removeTitle = scanner.nextLine();

				System.out.print("Year: ");
				int removeYear = scanner.nextInt();

				// Consume last new line not handled by nextInt()
				scanner.nextLine(); 

				movieLibrary.removeMovie(movieList, removeTitle, removeYear);
				break;

			case 7:
				// Load a library from a file
				movieList = movieLibraryLoader.loadMoviesFromFile(FILENAME);
				System.out.println("\nINFO: Movie library loaded in.\n");
				break;

			case 8:
				// Save movie library to a file
				movieLibraryLoader.saveMoviesToFile(movieList, FILENAME);
				System.out.println("\nINFO: Movie library saved to " + FILENAME + "\n");
				break;

			default:
				break;
		}
	}

	/**
	 * Prints graphical title of selected option before results are displayed to screen.
	 *
	 * @param 	menuOption 	menu option selected by user
	 */
	private static void printOptionTitle(int menuOption) {
		String optionTitle = null;

		switch (menuOption) {
			case 1:
				// List movies in library
				optionTitle = "LIST OF MOVIES IN LIBRARY";
				break;

			case 2:
				// Look up a movie by title
				optionTitle = "MOVIE DETAILS";
				break;

			case 3:
				// Look up movies by actor
				optionTitle = "MOVIES BY ACTOR";
				break;

			case 4:
				// Look up highest rated film
				optionTitle = "HIGHEST RATED MOVIE";
				break;

			case 5:
				// Add a movie to the library
				optionTitle = "ADD NEW MOVIE";
				break;


			case 6:
				// Remove a movie from the library
				optionTitle = "REMOVE MOVIE";
				break;

			default:
				break;
		}

		if (optionTitle != null) {
			int i;

			for (i = 0; i < optionTitle.length()+15; i++) {
				System.out.print("=");
			}

			System.out.println("\n\t" + optionTitle);

			for (i = 0; i < optionTitle.length()+15; i++) {
				System.out.print("=");
			}

			System.out.println();
		}
	}

	public static void main(String [] args) {
		mainMenu();
	}
}