import java.util.ArrayList;

public class MyPlaceDatabase implements PlaceDB {

    private static ArrayList<Place> places; // ArrayList to store places

    // Constructor to initialize the database
    public MyPlaceDatabase() {
        places = new ArrayList<>();
    }

    // Getter method to retrieve the ArrayList of places
    public ArrayList<Place> getPlaces() {
        return places;
    }

    // Method to display all places in the database
    public void show() {
        for (Place p : places) {
            System.out.println(p);
        }
    }

    // Method to add a new place to the database
    public void addPlace(Place newPlace) {
        boolean placeExists = false;
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            if (place.getZipcode().equals(newPlace.getZipcode())) {
                places.set(i, newPlace); // Update the existing place
                placeExists = true;
                break;
            }
        }
        if (!placeExists) {
            places.add(newPlace);
        }
    }

    // Method to lookup a place by its zipcode
    public Place lookupByZipcode(String zipcode) {
        for (Place place : places) {
            if (place.getZipcode().equals(zipcode)) {
                return place;
            }
        }
        return null; // Return null if place not found
    }

    // Method to list all places with zipcodes starting with a given prefix
    public void listAllPlaces(String prefix) {
        for (Place place : places) {
            if (place.getZipcode().startsWith(prefix)) {
                System.out.println(place.toString());
            }
        }
    }

    // Method to calculate the distance between two places given their zipcodes
    public double distance(String zip1, String zip2) {
        LocatedPlace place1 = (LocatedPlace) lookupByZipcode(zip1);
        LocatedPlace place2 = (LocatedPlace) lookupByZipcode(zip2);

        if (place1 != null && place2 != null) {
            double lat1 = place1.getLatitude();
            double lon1 = place1.getLongitude();
            double lat2 = place2.getLatitude();
            double lon2 = place2.getLongitude();

            return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
        }
        return -1; // Return -1 if one or both places are not found
    }

    public void getNumberOfPlaces() {
        System.out.println(places.size());
    }

}
