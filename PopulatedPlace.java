import java.io.Serializable;

public class PopulatedPlace extends LocatedPlace implements Serializable {
    // Additional variable to store the population of the populated place
    private int population;

    // Constructor to initialize PopulatedPlace object with zipcode, town, state,
    // latitude, longitude, and population
    public PopulatedPlace(String zipcode, String town, String state, double latitude, double longitude,
            int population) {
        super(zipcode, town, state, latitude, longitude);
        this.population = population;
    }

    // Copy constructor to create a copy of a PopulatedPlace object
    public PopulatedPlace(PopulatedPlace p) {
        super(p);
        this.population = p.population;
    }

    // Getter method to retrieve the population of the populated place
    public int getPopulation() {
        return population;
    }

    // Setter method to set the population of the populated place
    public void setPopulation(int population) {
        this.population = population;
    }

    // equals method that compares two populated place object
    public boolean equals(Object obj) {
        if (this != obj && obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            PopulatedPlace p = (PopulatedPlace) obj;
            return super.equals(p) && this.population == p.population;

        }
    }

    // Method to represent the PopulatedPlace object as a string
    public String toString() {
        return super.toString() + "," + population;
    }
}
