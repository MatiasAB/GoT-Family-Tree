package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.*;

public class help {

	public static void cmd() {
		System.out.println("Standard commands: Enter a character name to see a character's information, \"all\" for all characters, \"House [house name]\" for a house's information, \"all Houses\" for a "
						+ "all houses, or \"exit\" to exit.\n\n" + "Prediction list commands: Enter \"remove all\" to remove all characters, \"remove next\" to remove the next character,\r\n" + 
						"or \"LLTD\" to see which character is the least likely to die.\r\n\n" +"Note: commands are case-sensitive.\n");
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


	/**The method's input is a String, a Character LL, and a Predictions pqMyArrayList (a heap). Using the String, the method
	 * finds the file to be read. While reading the file, the method creates both a Character and their Prediction.
	 * After checking to see if the Character is present in the Character LL, the Prediction (which contains a reference
	 * to the Character) is inserted into the priority queue which the method returns and the heap. */
	public static pqLinkedList<Prediction> scanPrediction(String fn2, sLinkedList<Character> clist) {
		File f = new File(fn2);
		//Reading the fie
		if (!f.canRead()) {
			System.exit(1);
			
		 }
		
		 Scanner reader = null;
		 try {
		 reader = new Scanner(f);
		 } catch (FileNotFoundException e) {
			 System.exit(1);
			
		 }
		 pqLinkedList<Prediction> predictionsLL = new pqLinkedList<Prediction>();
		 int comma;
		 while (reader.hasNextLine()) {
			 String pred = reader.nextLine();
			 comma = pred.indexOf(",");
			 boolean plod = pred.substring(0, comma).equals("plod");
			 if (plod == false) {
				 String name = pred.substring(comma+1, pred.length());
				 float p = Float.parseFloat(pred.substring(0, comma));
				 Character c = new Character(name);
				 //Check to see if the Character LL has the Character in question already, which will either make the 
				 //input Character a reference to the one in the list, or do nothing at all. 
				 clist.contains(c);
				 if (clist.contains(c) == false) {
					 clist.add(c);
				 }
				 Prediction pd = new Prediction (c, p);
				 predictionsLL.insert(pd, p);
			 }
		 }
		return predictionsLL;
	}
}