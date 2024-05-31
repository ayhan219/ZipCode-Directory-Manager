import java.io.Serializable;

public class Place implements Serializable {
    // Variables to store information about a place
    String zipcode;
    String town;
    String state;

    // Constructor to initialize Place object with zipcode, town, and state
    public Place(String zipcode, String town, String state) {
        this.zipcode = zipcode;
        this.town = town;
        this.state = state;
    }

    // Copy constructor to create a copy of a Place object
    public Place(Place p) {
        this.zipcode = p.zipcode;
        this.town = p.town;
        this.state = p.state;
    }
   

    // Getter method to retrieve the zipcode of the place
    public String getZipcode() {
        return zipcode;
    }

    // Setter method to set the zipcode of the place
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    // Getter method to retrieve the town name of the place
    public String getTown() {
        return town;
    }

    // Setter method to set the town name of the place
    public void setTown(String town) {
        this.town = town;
    }

    // Getter method to retrieve the state name of the place
    public String getState() {
        return state;
    }

    // Setter method to set the state name of the place
    public void setState(String state) {
        this.state = state;
    }

    // equals method that compare two places based on their zipcodes, towns and
    // states
    public boolean equals(Object obj) {
        if (this != obj && obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Place p = (Place) obj;
            return ((this.zipcode.equals(p.zipcode)) && (this.town.equals(p.town)) && (this.state.equals(p.state)));
        }
    }

    // Method to represent the Place object as a string
    public String toString() {
        // Truncate the zipcode to the first 5 characters
        String newZipCode = "";
        for (int i = 0; i <= 4; i++) {
            newZipCode += zipcode.charAt(i);
        }

        // Return a formatted string representation of the Place object
        return zipcode + ":" + town + "," + state;
    }

}
