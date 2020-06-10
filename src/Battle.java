package src;

/** Battle class: Each instance of this class stores a Battle from the data read in the GameofThrones class. */

public class Battle implements Comparable<Battle>{
	//Instance variables for each element of a Battle.
	private String name;
	private String baK;
	private String bdK;
	private String baO;
	private String bbT;
	private String bl;
	private String br;
	
	public Battle (String name, String attackerKing, String defenderKing, String attackerOutcome, 
			String battleType, String location, String region){
		//Constructor for Battle object.
		this.name = name;
		this.baK = attackerKing;
		this.bdK = defenderKing;
		this.baO = attackerOutcome;
		this.bbT = battleType;
		this.bl = location;
		this.br = region;
	}

	@Override
	public int compareTo(Battle o) {
		//Compares the input Battle against the Battle calling this method by name.
		int c = this.name.toLowerCase().compareTo(o.name.toLowerCase());
		return c;
	}
	
	public String toString() {
		//Returns a String that represents the Battle calling this method. 
		String battle = "- " + this.name +", when " + this.baK + " attacked " + this.bdK + ", resulting in a "
				+ this.baO + ", through a " + this.bbT + ", at " + this.bl + ", in the region of " + this.br + "\n";
		return battle;
	}
	
	
	
}
