package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
		sLinkedList<Character>clist = help.scanCharacter(fileName2, hlist);

		String fn2 = "data/predictions.csv";
		pqLinkedList<Prediction> predictionsLL = help.scanPrediction(fn2, clist);
		predictionsLL.sort();

		String fileName = "data/battles.csv";
		sLinkedList<Battle> battlelist = help.scanBattle(fileName, clist);
	
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
			help.cmd();
			while (j > 0) {
				System.out.print("Enter command (type \"help\" if you want the list of commands):");
				String s = scan.nextLine();
				switch(s) {
				
				case "exit":
					System.out.println("Goodbye!");
					j = 0;
					break;

				case "help":
					help.cmd();
					break;
				
				case "all":
					System.out.println(clist.toString());
					break;
					
				case "all Houses":
					System.out.println(hlist.toString());
					break;

				case "LLTD":
					if (predictionsLL.isEmpty() == false) {
						System.out.println("LLTD: " + predictionsLL.peek());
					} else {
						System.err.println("No more characters!");
					}
					break;

				case "remove next":
					if (predictionsLL.isEmpty() == true) {
						System.err.println("No more characters!");
					} else {
						System.out.println("Removed: " + predictionsLL.remove().toString());
					}
					break;

				case "remove all":
					if (predictionsLL.isEmpty() == true) {
						System.err.println("No more characters!");
					} else {
						while (predictionsLL.isEmpty() == false) {
							System.out.println("Removed: " + predictionsLL.remove());
						}
					}
					break;
				
				default:
					//Creates a new Character off of user input, finds the Character in the list, and then prints the 
					//Character and its lineage.
					if (s.contains("House ")) {
						House look = new House (s);

						if (hlist.contains(look)) {
							System.out.println(look.toString());
						}
					} else {
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
				    		System.err.println("Character not found or incorrect input!");
				    	}
					}
					
			    	
			    	break;
				}
			
			
			}
	}

}
