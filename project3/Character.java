package project3;

/** Character class: Each instance of this class stores a Character from the data read in the GameofThrones class. */

public class Character implements Comparable<Character> {
	//Instance variables that represent the elements of a Character.
	private String name;
	private String calle;
	private sLinkedList<Battle> cbat;
	private String fatherName;
	private String motherName;
	private boolean isPatriarch;
	private boolean isMatriarch;
	
	public Character(String name, String allegiances, String mother, String father, sLinkedList<Battle> battles) {
		// Constructor for Character object.
		this.name = name;
		this.motherName = mother;
		if (this.motherName.equals("MATRIARCH")) {
			this.isMatriarch = true;
		}
		this.fatherName = father;
		if (this.fatherName.equals("PATRIARCH")) {
			this.isPatriarch = true;
		}
		this.calle = allegiances;
		this.cbat = battles;
		
	}

	public Character(String name, String alleg, sLinkedList<Battle> bat) {
		//Constructor for Character object.
		this.name = name;
		this.calle = alleg;
		this.cbat = bat;
	}

	@Override
	public int compareTo(Character arg0) {
		//Compares Characters by name.
		int a = this.name.toLowerCase().compareTo(arg0.name.toLowerCase());
		if (a == 0) {
			//If the two Characters are the same, the method calls the transfer() method in order to make the input
			//Character a reference to the Character call this method.
			this.transfer(arg0);
		}
		return a;
	}
	
	public String toString() {
		//Creates and returns a String representative of the Character.
		String str = this.name + " with allegiance to " + this.calle;
		if (this.cbat !=null) {
			this.cbat.sort();
			str += "\n" + this.cbat.toString();
		}
		return str;
	}
	
	public void transfer(Character c) {
		//Makes the input Character object a reference to the Character object calling this method. 
		c.name = this.name;
		c.calle = this.calle;
		c.cbat = this.cbat;
		
	}
	

	public void addBattle(Battle b) {
		//Adds a Battle to the Character's battle list.
		this.cbat.add(b);
		
	}
	
	//All the methods below return a reference to one of the Character's instance variables.
	
	public boolean getisMatriach() {
		return this.isMatriarch;
	}
	
	public boolean getisPatriach() {
		return this.isPatriarch;
	}
	
	public String getFather() {
		return this.fatherName;
	}
	
	public String getMother() {
		return this.motherName;
	}
	
	public String getName() {
		return this.name;
	}

	public String getAllegiance() {
		return this.calle;
	}
	
}
