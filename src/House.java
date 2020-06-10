package src;

/** House class: Each instance of this class stores a House based off the allegiances of the Characters in the 
 * Character list.*/

public class House implements Comparable<House> {
	private String name; 
	protected sLinkedList<Character> members; 
	private Character patriarch; 
	private Character matriarch; 
	public FamilyTree familyTree;
	
	public House(String n) {
		//Constructor for the House object.
		this.name = n;
		this.members = new sLinkedList<Character>();
		this.familyTree = new FamilyTree();
	}
	
	
	public void addMember(Character c) {
		//Adds the input Character to the House's list of characters, and marks it as the matriarch or patriarch of the House
		//if appropriate.
		this.members.add(c);
		if (c.getisMatriach() == true) {
			this.matriarch = c;
		} else if (c.getisPatriach() == true) {
			this.patriarch = c;
		}
	}
	
	@Override
	public int compareTo(House arg0) {
		//Creates and returns a String representative of the Character.
		if (this.name.compareTo(arg0.name) == 0){
			this.transfer(arg0);
		}
		return this.name.compareTo(arg0.name);
	}
	
	private void transfer(House arg0) {
		//Makes the input House object a reference to the House object calling this method.
		arg0.name = this.name;
		arg0.members = this.members;
		arg0.familyTree = this.familyTree;
		arg0.patriarch = this.patriarch;
		arg0.matriarch = this.matriarch;
	}

	public String toString() {
		return this.name + "\n" + this.familyTree.toString() + "\n";
	}
	
	public void buildTree() {
		//This methods builds the House's family tree using the buildFamilyTree() method in the FamilyTree class.
		this.familyTree.buildFamilyTree(this);
	}
	
	//These methods return references to the Matriarch and Patriarch of the House, respectively. 
	public Character getMatriarch() {
		return this.matriarch;
	}
	
	public Character getPatriarch() {
		return this.patriarch;
	}
}
