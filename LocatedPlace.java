import java.io.Serializable;

public class LocatedPlace extends Place implements Serializable {

    // Instance variables to store latitude and longitude coordinates
    private double latitude;
    private double longitude;

    // Constructor to initialize LocatedPlace object with provided values
    public LocatedPlace(String zipcode, String town, String state, double latitude, double longitude) {
        // Call the constructor of the superclass Place with provided zipcode, town, and
        // state
        super(zipcode, town, state);
        // Initialize latitude and longitude coordinates
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Copy constructor to create a copy of an existing LocatedPlace object
    public LocatedPlace(LocatedPlace l) {
        // Call the copy constructor of the superclass Place with the provided
        // LocatedPlace object l
        super(l);
        // Copy latitude and longitude coordinates from the provided LocatedPlace object
        // l
        this.latitude = l.latitude;
        this.longitude = l.longitude;
    }

    // Getter method to retrieve the latitude coordinate
    public double getLatitude() {
        return latitude;
    }

    // Setter method to set the latitude coordinate
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Getter method to retrieve the longitude coordinate
    public double getLongitude() {
        return longitude;
    }

    // Setter method to set the longitude coordinate
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // equals method that compares two LocatedPlace object
    public boolean equals(Object obj) {
        if (this != obj && obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            LocatedPlace p = (LocatedPlace) obj;
            return super.equals(p) && this.latitude == p.latitude && this.longitude == p.longitude;
        }
    }

    // Overridden toString method to provide a string representation of the
    // LocatedPlace object
    @Override
    public String toString() {
        // Call the toString method of the superclass Place and concatenate latitude and
        // longitude coordinates
        return super.toString() + "," + latitude + "," + longitude;
    }
}
