import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName) {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void menu() {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q")) {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option) {
        if (option.equals("t")) {
            searchTitles();
        } else if (option.equals("c")) {
            searchCast();
        } else if (option.equals("k")) {
            searchKeywords();
        } else if (option.equals("g")) {
            listGenres();
        } else if (option.equals("r")) {
            listHighestRated();
        } else if (option.equals("h")) {
            listHighestRevenue();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1) {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie) {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        /* TASK 4: IMPLEMENT ME! */

        System.out.print("Enter the name of the actor you would like to search: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> finalList = new ArrayList<>();
        ArrayList<Movie> results = new ArrayList<Movie>();


        for (int i = 0; i < movies.size(); i++) {
            String actors = movies.get(i).getCast();
            String[] actorList = actors.split("\\|");

            for (String castMember : actorList) {
                if (!finalList.contains(castMember) && castMember.toLowerCase().contains(searchTerm)) {
                    finalList.add(castMember);
                }
            }
        }

        for (int j = 1; j < finalList.size(); j++) {
            String temp = finalList.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(finalList.get(possibleIndex - 1)) < 0) {
                finalList.set(possibleIndex, finalList.get(possibleIndex - 1));
                possibleIndex--;
            }
            finalList.set(possibleIndex, temp);
        }

        for (int i = 0; i < finalList.size(); i++) {
            String castName = finalList.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + castName);
        }

        System.out.println("Which cast would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCast = finalList.get(choice - 1);

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getCast().contains(selectedCast)) {
                results.add(movies.get(i));
            }
        }

        for (int j = 0; j < results.size(); j++) {
            int minIndex = j;
            for (int k = j + 1; k < results.size(); j++) {
                if (results.get(k).getTitle().compareTo(results.get(minIndex).getTitle()) < 0) {
                    minIndex = k;
                }
            }
            Movie temp = results.get(j);
            results.set(j, results.get(minIndex));
            results.set(minIndex, temp);
        }



        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choices = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choices - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void searchKeywords() {
        /* TASK 3: IMPLEMENT ME! */
        System.out.print("Enter the key word you would like to search: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();


        for (int i = 0; i < movies.size(); i++) {
            String keyword = movies.get(i).getKeywords();
            keyword = keyword.toLowerCase();

            if (keyword.contains(searchTerm)) {
                results.add(movies.get(i));
            }
            sortResults(results);
        }


        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listGenres() {
        /* TASK 5: IMPLEMENT ME! */
        ArrayList<String> genreList = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String[] genre = movies.get(i).getGenres().split("\\|");
            for (int x = 0; x < genre.length; x++) {
                if (genreList.indexOf(genre[x]) == -1) {
                    genreList.add(genre[x]);
                }
            }
        }

        for (int i = 1; i < genreList.size(); i++) {
            String temp = genreList.get(i);
            int possibleIndex = i;
            while (possibleIndex > 0 && temp.compareTo(genreList.get(possibleIndex - 1)) < 0) {
                genreList.set(possibleIndex, genreList.get(possibleIndex - 1));
                possibleIndex--;
            }
            genreList.set(possibleIndex, temp);
        }

        for (int i = 0; i < genreList.size(); i++) {
            String genre = genreList.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = genreList.get(choice - 1);
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getGenres().contains(selectedGenre)) {
                results.add(movies.get(i));
            }
        }
        sortResults(results);

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choices = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choices - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated() {
        /* TASK 6: IMPLEMENT ME! */
        ArrayList<Movie> sortedMoviebyRating = new ArrayList<>();


        for (int j = 1; j < movies.size(); j++) {
            Movie temp = movies.get(j);
            double tempRating = temp.getUserRating();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempRating > movies.get(possibleIndex - 1).getUserRating()) {
                movies.set(possibleIndex, movies.get(possibleIndex - 1));
                possibleIndex--;
            }
            movies.set(possibleIndex, temp);
        }

        for (int i = 0; i < 50; i++) {
            sortedMoviebyRating.add(movies.get(i));
        }

        for (int i = 0; i < sortedMoviebyRating.size(); i++) {
            String title = sortedMoviebyRating.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + " " + sortedMoviebyRating.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = sortedMoviebyRating.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void listHighestRevenue() {
        /* TASK 6: IMPLEMENT ME! */
        ArrayList<Movie> sortedMoviebyRev = new ArrayList<>();


        for (int j = 1; j < movies.size(); j++) {
            Movie temp = movies.get(j);
            int tempRating = temp.getRevenue();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempRating > movies.get(possibleIndex - 1).getRevenue()) {
                movies.set(possibleIndex, movies.get(possibleIndex - 1));
                possibleIndex--;
            }
            movies.set(possibleIndex, temp);
        }

        for (int i = 0; i < 50; i++) {
            sortedMoviebyRev.add(movies.get(i));
        }

        for (int i = 0; i < sortedMoviebyRev.size(); i++) {
            String title = sortedMoviebyRev.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + " " + sortedMoviebyRev.get(i).getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = sortedMoviebyRev.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void importMovieList(String fileName) {
        /* TASK 1: IMPLEMENT ME! */
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null) {
                // import all cells for a single row as an array of Strings,
                // then convert to ints as needed
                String[] movieFromCSV = line.split(",");

                // pull out the data for this cereal

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);


                // create Cereal object to store values
                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

                // adding Cereal object to the arraylist
                movies.add(nextMovie);
            }
            bufferedReader.close();
        } catch (IOException exception) {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

}

// ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary
