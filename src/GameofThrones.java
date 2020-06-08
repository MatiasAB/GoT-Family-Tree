package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import project2.sLinkedList.sllNode;

import java.util.*;

/** GameofThrones class: The main class of the program, which has three methods that handle all the 
 * functions of the program. The main() method is the "driver's seat" of the program: it executes all the primary fucntions
 * of the program, like creating the Character list, the House list, and reading the console in order to respond to
 * the user's commands. The other two methods, scanCharacter() and scanBattle() read in the data files that the program
 * requires.  */

public class GameofThrones {
	
	/** main() method: The method starts by scanning battles.csv and characters_lineage.csv to make a list of all the 
	 * Characters, their battles, and another list of the houses. Once the character list is made, the method calls 
	 * the LinkedList isSorted() method to check if the character list is sorted, and if it isn't, the method calls 
	 * the LinkedList sort() method to sort the list. After that, the method traverses through the house list to build
	 * the family trees for each house. Finally, the method creates a scanner and begins an input loop which allows the 
	 * user to access the functions of the program (printing a character, printing all the characters, printing all 
	 * the houses and their members, or exiting the program).
	 */
	public static void main(String[] args) {
		// Scanning the data files and making the Battle, Character, and House lists.
		String fileName2 = "data/characters_lineage.csv";
		sLinkedList<House> hlist = new sLinkedList<House>();
		sLinkedList<Character>clist = scanCharacter(fileName2, hlist);
		String fileName = "data/battles.csv";
		sLinkedList<Battle> battlelist = scanBattle(fileName, clist);
	
		//Traversing the house list to build all the family trees.
		int size = hlist.size();
		int count = 1;
		while (count <= size) {
			House h = (House) hlist.getAt(count);
			h.buildTree();
			count++;
		}
		
		// Using the sLinkedList methods to check if the Character list is sorted, and then sorting it.
        boolean t = clist.isSorted();
        if (t == false) {
        	clist.sort();
        } 
        
        // Initializing the scanner and beginning the input loop. 
		Scanner scan = new Scanner(System.in);
			int j = 1;
			while (j > 0) {
				System.out.println("Enter a character name (or type \"all\" for all characters,  \"family tree\" for a "
						+ "family tree of all houses, or \"exit\" to exit):");
				String s = scan.nextLine();
				switch(s) {
				
				case "exit":
					System.out.println("Goodbye!");
					j = 0;
					break;
				
				case "all":
					System.out.println(clist.toString());
					break;
					
				case "family tree":
					System.out.println(hlist.toString());
					break;
				
				default:
					//Creates a new Character off of user input, finds the Character in the list, and then prints the 
					//Character and its lineage.
					sLinkedList<Battle> looklist = null;
			 		Character look = new Character (s, "", looklist);
			    	boolean f = clist.contains(look);
			    	if (f == true) {
			    		System.out.println(look.toString());
			    		House house = new House (look.getAllegiance());
				    	if (hlist.contains(house) == true) {
				    		if (house.members.contains(look)) {
				    			if (house.familyTree.contains(look)) {
				    				house.familyTree.printLineage(look);
				    			}
				    		}
				    	}
				    	System.out.println("");
			    	} else if (f == false) {
			    		System.err.println("Character not found!");
			    	}
			    	
			    	break;
				}
			
			
			}
	}


	/** Input: A String for the file name */
	/** scanBattle() method: The method takes in the input of a String for the name of the file it will read and
	 * a sLinkedList of Characters. While reading the Battle document, this method places the Battle name, 
	 * the names of the Characters involved, the outcome, the Battle type, location, and region into Strings 
	 * using the index of ",". At the end, the Strings for the seven elements are used to make a new Battle object, which
	 * is put into the Battle LinkedList. Then, using the names of the attacking and defending kings involved in the Battle,
	 * the method creates two new Character objects and searches for any Characters with the same name as either of the kings.
	 * If there's a match, the Battle is added to the Character's list of battles.
	 */
	public static sLinkedList<Battle> scanBattle(String fileName, sLinkedList<Character> clist){
		// Variables to help distinguish the seven elements of a Battle from each other.
		int one;
		int two;
		int three;
		int four;
		int five;
		int six;
		int seven;
		
		// Reading the file 
		File f = new File(fileName);
		
		if (!f.canRead()) {
			System.exit(1);
			
		 }
		
		 Scanner reader = null;
		 try {
		 reader = new Scanner(f);
		 } catch (FileNotFoundException e) {
			 System.exit(1);
			
		 }
		 
		 // Creating the LinkedList for the Battles, then going through the file and adding Battles to the list.
		 sLinkedList<Battle> battlelist = new sLinkedList<Battle>();
	
		 while (reader.hasNextLine()) {
			 String battle = reader.nextLine();
			 one = battle.indexOf(",");
			 two = battle.indexOf(",", one+1);
			 three = battle.indexOf(",", two+1);
			 four = battle.indexOf(",", three+1);
			 five = battle.indexOf(",", four+1);
			 six = battle.indexOf(",", five+1);
			 seven = battle.length();
			 String bname = battle.substring(0, one);
			 String attackerKing = battle.substring(one +1, two);
			 String defenderKing = battle.substring(two +1, three);
			 String attackerOutcome = battle.substring(three+1, four);
			 String battleType = battle.substring(four+1, five);
			 String location = battle.substring(five+1, six);
			 String region = battle.substring(six+1, seven);
			 
			 Battle obj = new Battle (bname, attackerKing, defenderKing, attackerOutcome, battleType,
					 location, region);
			 battlelist.add(obj);
			 //Creating two Characters objects based off of the attackerKing and defenderKing, then searching the Character
			 //list for any characters by the same name.
			 Character ak = new Character (attackerKing, "", new sLinkedList<Battle>());
			 Character dk = new Character (defenderKing, "", new sLinkedList<Battle>());
			 boolean ak1 = clist.contains(ak);
			 boolean dk1 = clist.contains(dk);
			 //If there is a Character in the Character list involved in the battle, the battle is added to that Character's
			 //battle list.
			 if (ak1 == true) {
				 ak.addBattle(obj);
			 } else if (dk1 == true) {
				 dk.addBattle(obj);
			 }
		 
		 }
		return battlelist;
		
	}
	
	
	/**Input: The LinkedList of all the battles, a string for the file name */
	/**scanCharacter() method: For input, it takes in a string for the name of the file it needs to read, and
	 * a blank sLinkedList of Houses. While reading the character document, this method places the character name and
	 * allegiance into Strings using the index of ",". Then, creating a new House object out of the Character's allegiance,
	 * it first checks that the House doesn't already exist in the House list, then either adds the House to the list and 
	 * adds the Character to that house, or simply just adds the Character to the house depending on the outcome.  
	 */
	public static sLinkedList<Character> scanCharacter (String fileName2, sLinkedList<House> hlist){
		//Four variables to help distinguish the four elements of a Character from each other.
		int one;
		int two;
		int three;
		int four;
		
		//Reading the file
		File f2 = new File(fileName2);
		
		if (!f2.canRead()) {
			System.exit(1);
		 }
		
		 Scanner reader2 = null;
		 try {
		 reader2 = new Scanner(f2);
		 } catch (FileNotFoundException e) {
			 
			 System.exit(1);
			
		 }
		 
		  //Creating the LinkedList for the Characters, then going through the document and adding Characters to the list.
		 sLinkedList<Character> clist = new sLinkedList<Character>();
		 while (reader2.hasNextLine()) {
			 String character = reader2.nextLine();
			 one = character.indexOf(",");
			 three = character.indexOf(",", one+1);
			 four = character.indexOf(",", three+1);
			 two = character.length();
			 String name = character.substring(0, one);
			 String father = character.substring(one+1, three);
			 String mother = character.substring(three+1, four);
			 String allegiances = character.substring((four + 1), two);
			 sLinkedList<Battle> cbattles = new sLinkedList<Battle>();
			 Character per = new Character (name, allegiances, mother, father,  cbattles);
			 if (character.equals("Name,Father,Mother,Allegiance")) {
				 // This makes it so this default entry isn't put into the list of characters
			 } else {
				//Adding the Character to the Character list.
				 clist.add(per); 
				 // Creates a new House object, and checks if it's in the house list.
				 House h = new House(allegiances);
				 boolean hasHouse = hlist.contains(h);
				 if (hasHouse == false) {
					 //Scenario 1: The house is not in the list. The method adds the House to the House list and adds the
					 //Character to that house.
					 hlist.add(h);
					 h.addMember(per);
				 } else {
					 //Scenario 2: The house is in the list. The House object becomes a reference to the House in the list,
					 //and the Character is added to its member list.
					 h = (House) hlist.get(h);
					 h.addMember(per);
				 }
			 }
			 
		 } 
		 
		reader2.close();
		return clist;
		
	}

}
