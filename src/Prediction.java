package project4;

/** Prediction class: Each instance of this class stores a Character and a float representing the Character's probability
 * of dying based on the data read in the GameofThrones class. */

public class Prediction implements Comparable<Prediction> {
	//The instance variables which hold a reference to a Character and its PLOD. 
	private float plod;
	private Character character;
	
	
	public Prediction (Character c, float plod) {
		//Constructor
		this.plod = plod;
		this.character = c;
	}
	
	public String toString() {
		//String representation of the Prediction.
		return this.character.getName() + " with PLOD of " + this.plod;
	}
	
	@Override
	public int compareTo(Prediction arg0) {
		//Compares two Predictions by their PLODs.
		if (this.plod < arg0.plod) {
			return -1;
		} else if (this.plod > arg0.plod) {
			return 1;
		} else {
			return 0;
		}
	}

}
