import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * This class is a possible user interface for the Place Database
 * that uses the console to display the menu of command choices.
 */
public class PDConsoleIO {

  /**
   * A reference to the PlaceDB object to be processed.
   * Globally available to the command-processing methods.
   */
  private PlaceDB theDatabase = null;

  /** Scanner to read from input console. */
  private Scanner scIn = null;

  private boolean isSortedByTownName = false;

  // Constructor
  /** Default constructor. */
  public PDConsoleIO() {
    scIn = new Scanner(System.in);
    isSortedByTownName = false;
  }

  // Methods
  /**
   * Method to display the command choices and process user
   * commands.
   * 
   * @param thePlaceDatabase A reference to the PlaceDB
   *                         to be processed
   */
  public void processCommands(PlaceDB thePlaceDatabase) {
    String[] commands = {
        "Add Place",
        "Look Up by Zipcode",
        "List All Places by Zipcode Prefix",
        "Distance Between Zipcodes",
        "Sort by Town Name",
        "Lookup by Town Name",
        "Exit" };

    theDatabase = thePlaceDatabase;
    int choice;
    do {
      System.out.println();
      for (int i = 0; i < commands.length; i++) {
        System.out.println("Select " + i + ": "
            + commands[i]);
      }

      choice = scIn.nextInt(); // Read the next choice.
      scIn.nextLine(); // Skip trailing newline.
      switch (choice) {
        case 0:
          doAddPlace();
          break;
        case 1:
          doLookupByZipcode();
          break;
        case 2:
          doListAllPlaces();
          break;
        case 3:
          doDistance();
          break;
        case 4:
          System.out.println("Loading data...");
          sortByTownName();
          break;
        case 5:
          lookupBytownName();
          break;
        case 6:
          if (theDatabase instanceof MyPlaceDatabase) {
            writeToBinary(((MyPlaceDatabase) theDatabase).getPlaces());
          }
          System.out.println("Exiting the program! Every single line has been written to the binary file.");
          break;
        default:
          System.out.println("*** Invalid choice "
              + choice
              + " - try again!");
      }

    } while (choice != commands.length - 1);
    System.exit(0);
  }

  // Method to check if a string consists only of numeric characters(private
  // method. (Not needed)
  private boolean isNumeric(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    for (char c : str.toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Method to add a place.
   * pre: The database exists.
   * post: A new place is added.
   */

  // Method to add a new place to the database
  private void doAddPlace() {

    Place p;
    System.out.println("enter zipcode");
    String zipCode = scIn.nextLine();
    while (zipCode.length() != 5 || !isNumeric(zipCode)) {
      System.out.println("Error in zipcode");
      zipCode = scIn.nextLine();
    }
    System.out.println("enter town");
    String town = scIn.nextLine();
    System.out.println("enter state");
    String state = scIn.nextLine();
    System.out.println("Enter latitude and longitude (separated by comma):");
    String latAndlong = scIn.nextLine();
    String[] parts = latAndlong.split(",");

    System.out.println("enter population");
    String pop = scIn.nextLine();

    if (latAndlong.equalsIgnoreCase("none")) {
      p = new Place(zipCode, town, state);
      theDatabase.addPlace(p);
    } else if (pop.equalsIgnoreCase("none")) {
      if (parts.length == 2) {
        double latitude = Double.parseDouble(parts[0].trim());
        double longitude = Double.parseDouble(parts[1].trim());
        p = new LocatedPlace(zipCode, town, state, latitude, longitude);
        theDatabase.addPlace(p);
      }

    } else {
      if (parts.length == 2) {
        double latitude = Double.parseDouble(parts[0].trim());
        double longitude = Double.parseDouble(parts[1].trim());
        System.out.println("latitude:" + latitude);
        System.out.println("longitude:" + longitude);
        p = new PopulatedPlace(zipCode, town, state, latitude, longitude, Integer.parseInt(pop));

        theDatabase.addPlace(p);
      }
    }
    // read all info for a Place object
    // decide if it is a locatedPlace or populatedPlace
    // based on entered data
    // if location is missing it is a Place object
    // if location is there but population is missing it is a locatedPlace
    // if both location and population is available it is a populatedPlace
    // assume there can be no such place with population but without location

    // complete the rest to add the new place to theDatabase

  }

  /**
   * Method to lookup a place by zipcode.
   * pre: The database exists.
   * post: No changes made to the database.
   */

  // Method to lookup a place by zipcode
  private void doLookupByZipcode() {
    // Request the zipcode.
    System.out.println("Enter zipcode");
    String theZip = scIn.nextLine();
    while (theZip.length() < 5 || theZip.length() > 5 || !isNumeric(theZip)) {
      System.out.println("invalid zipcode");
      theZip = scIn.nextLine();
    }
    if (theZip.equals("")) {
      return; // Dialog was cancelled.
    }
    // Look up the zipcode.
    Place p = theDatabase.lookupByZipcode(theZip);
    if (p != null) { // Zipcode was found.
      System.out.println(p.toString());
    } else { // not found.
      // Display the result.
      System.out.println("No such zipcode");

    }
  }

  /**
   * Method to list all places whose zipcodes start with entered prefix.
   * pre: The database exists.
   * post: No changes made to the database.
   */

  // Method to list all places whose zipcodes start with a specified prefix
  private void doListAllPlaces() {
    System.out.println("Enter zipcode prefix");
    String prefix = scIn.nextLine();
    if (prefix.equals("")) {
      return; // Dialog was cancelled.
    }

    theDatabase.listAllPlaces(prefix);

  }

  /**
   * Method to compute the distance between two zipcodes.
   * pre: The database exists.
   * post: No changes made to the database.
   */

  // Method to compute the distance between two zipcodes
  private void doDistance() {
    System.out.println("Enter two zipcodes (separated by comma):");
    String zips = scIn.nextLine();
    String[] zip1Andzip2 = zips.split(",");
    String zip1 = zip1Andzip2[0].trim();
    String zip2 = zip1Andzip2[1].trim();
    double takenDistane = theDatabase.distance(zip1, zip2);
    if (takenDistane != -1) {
      System.out.println("the distance is:" + takenDistane);

    } else {
      System.out.println("Error!");
    }

  }

  // Sorts the list of places by town name in ascending order
  // Prints the sorted list of places with their details
  public void sortByTownName() {
    if (theDatabase == null) {
      System.out.println("Database is empty");
      return;
    }
    ArrayList<Place> places = ((MyPlaceDatabase) theDatabase).getPlaces();

    boolean swapped;
    for (int i = 0; i < places.size() - 1; i++) {
      swapped = false;
      for (int j = 0; j < places.size() - 1 - i; j++) {
        if (places.get(j).getTown().compareTo(places.get(j + 1).getTown()) > 0) {
          Collections.swap(places, j, j + 1);
          swapped = true;
          isSortedByTownName = true;
        }
      }
      if (!swapped)
        break;
    }

    System.out.println("Places sorted by town name:");
    for (Place p : places) {
      System.out.println(p.toString());
    }
  }

  // Prompts the user to enter a town name
  // Retrieves the list of places from the database
  // Iterates through the list to find all places with the entered town name
  // Prints details of all matching places including town name, zipcode,
  // population (if applicable), and rank by population
  // If no places are found with the entered town name, prints a message
  // indicating that
  public void lookupBytownName() {
    System.out.println("Enter town name:");
    String townName = scIn.nextLine();
    ArrayList<Place> places = ((MyPlaceDatabase) theDatabase).getPlaces();
    ArrayList<Place> matchingPlaces = new ArrayList<>();

    // iterate through the list to find all matching town names
    for (Place place : places) {
      if (place.getTown().equalsIgnoreCase(townName)) {
        matchingPlaces.add(place);
      }
    }

    if (!matchingPlaces.isEmpty()) {
      System.out.println("Places with town name \"" + townName + "\":");
      for (Place p : matchingPlaces) {
        System.out.println("Town Name: " + p.getTown());
        System.out.println("Zipcode: " + p.getZipcode());
        System.out.println("Population: "
            + ((p instanceof PopulatedPlace) ? ((PopulatedPlace) p).getPopulation() : "N/A"));

        // determine the rank of the place by population if applicable
        int rank = 1;
        if (p instanceof PopulatedPlace) {
          int population = ((PopulatedPlace) p).getPopulation();
          for (Place place : places) {
            if (place instanceof PopulatedPlace && ((PopulatedPlace) place).getPopulation() > population) {
              rank++;
            }
          }
        } else {
          rank = -1; // if not a populated it is going to show -1
        }
        System.out.println("Rank in population: " + rank);
        System.out.println();
      }
    } else {
      System.out.println("Town name not found.");
    }
  }

  // Performs a binary search on the list of places based on town names
  // Returns the index of the place with the matching town name if found,
  // otherwise returns -1

  private int binarySearch(ArrayList<Place> places, String townName, int left, int right) {
    if (right >= left) {
      int mid = left + (right - left) / 2;
      int comparison = places.get(mid).getTown().compareToIgnoreCase(townName);

      if (comparison == 0) {
        return mid;
      }
      if (comparison > 0) {
        return binarySearch(places, townName, left, mid - 1);
      }
      return binarySearch(places, townName, mid + 1, right);
    }
    return -1;
  }

  // Writes the list of places to a binary file named "database.out"
  // Prints messages indicating the writing process and success status
  // Catches FileNotFoundException and IOException and prints corresponding error
  // messages
  // Closes the ObjectOutputStream in the finally block to ensure resources are
  // properly released

  public static void writeToBinary(ArrayList<Place> places) {
    String binaryFileName = "database.out";
    ObjectOutputStream write = null;
    try {
      write = new ObjectOutputStream(new FileOutputStream(binaryFileName));
      write.writeObject(places);
      System.out.println("Objects are writing to binary file please wait...");
      System.out.println("ArrayList object written to binary file successfully.");
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error writing to binary file: " + e.getMessage());
    } finally {
      if (write != null) {
        try {
          write.close();
        } catch (IOException e) {
          System.out.println("Error closing file: " + e.getMessage());
        }
      }
    }
  }

  // Reads a PlaceDB object from the binary file specified by the given filename
  // Initializes a new MyPlaceDatabase object to store the read data
  // Uses try-with-resources to automatically close the ObjectInputStream after
  // reading
  // Reads the ArrayList of Place objects from the file and adds them to the
  // PlaceDB
  // Prints a success message if the data is loaded successfully
  // Catches FileNotFoundException, IOException, and ClassNotFoundException and
  // prints corresponding error messages
  // Returns the PlaceDB object containing the loaded data
  public static PlaceDB readFromBinary(String filename) {
    PlaceDB pd = new MyPlaceDatabase();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      ArrayList<Place> places = (ArrayList<Place>) ois.readObject();
      for (Place place : places) {
        pd.addPlace(place);
      }
      System.out.println("Data loaded from binary file successfully.");
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error reading from binary file: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.out.println("Class not found: " + e.getMessage());
    }
    return pd;
  }

  // Main method to start the program execution
  // Initializes a PlaceDB and a MyPlaceDatabase object
  // Specifies the binary file name
  // Checks if the binary file exists
  // If the binary file exists, loads data from the binary file using
  // readFromBinary method
  // If the binary file doesn't exist, loads data from CSV files using
  // loadDataFromCSVFiles method
  // Creates a PDConsoleIO object to handle user commands and interactions
  // Processes user commands using the PlaceDatabase object

  public static void main(String[] args) {
    PlaceDB pd = new MyPlaceDatabase();
    MyPlaceDatabase database = new MyPlaceDatabase();
    String binaryFileName = "database.out";

    if (new File(binaryFileName).exists()) {
      System.out.println("Binary file found. Loading data from binary file...");
      pd = readFromBinary(binaryFileName);
      database = (MyPlaceDatabase) pd;
    } else {
      System.out.println(
          "Binary file not found. Loading data from CSV files it will take some time...(when you exit the program(by pressing 6), the binary file will be created)");
      loadDataFromCSVFiles(pd, database);
    }

    PDConsoleIO ui = new PDConsoleIO();
    ui.processCommands(database);
  }

  private static void loadDataFromCSVFiles(PlaceDB pd, MyPlaceDatabase database) {
    String filename = "uszipcodes.csv";
    String filename2 = "ziplocs.csv";

    try (
        Scanner reader = new Scanner(
            new FileInputStream(filename));
        Scanner reader2 = new Scanner(
            new FileInputStream(filename2))) {
      reader.nextLine();
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] data = line.split(",");
        String zipCode = data[0];
        String city = data[1];
        String state = data[2];
        int population = -1;
        if (data.length >= 5 && !data[3].isEmpty()) {
          population = Integer.parseInt(data[3]);
        }

        if (population > 0) {
          pd.addPlace(new PopulatedPlace(zipCode, city, state, 0.0, 0.0, population));
        } else {
          pd.addPlace(new Place(zipCode, city, state));
        }
      }

      reader2.nextLine();
      while (reader2.hasNextLine()) {
        String line2 = reader2.nextLine();
        String[] data2 = line2.split(",");
        String zipCode = data2[0].replaceAll("\"", "");
        double latitude = 0.0;
        double longitude = 0.0;
        if (!data2[5].isEmpty()) {
          latitude = Double.parseDouble(data2[5]);
        }
        if (!data2[6].isEmpty()) {
          longitude = Double.parseDouble(data2[6]);
        }

        Place place = database.lookupByZipcode(zipCode);
        if (place != null) {
          if (place instanceof PopulatedPlace) {
            PopulatedPlace populatedPlace = (PopulatedPlace) place;
            populatedPlace.setLatitude(latitude);
            populatedPlace.setLongitude(longitude);
          } else if (place instanceof LocatedPlace) {
            ((LocatedPlace) place).setLatitude(latitude);
            ((LocatedPlace) place).setLongitude(longitude);
          } else {
            LocatedPlace locatedPlace = new LocatedPlace(zipCode, place.getTown(), place.getState(), latitude,
                longitude);
            pd.addPlace(locatedPlace);
          }
        } else {
          pd.addPlace(new LocatedPlace(zipCode, "", "", latitude, longitude));
        }
      }
    } catch (FileNotFoundException e) {

      System.out.println(e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Error parsing latitude or longitude: " + e.getMessage());
    }
  }

}