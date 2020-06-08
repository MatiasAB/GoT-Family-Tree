package project3;

/** FamilyTree class: Each instance of this class stores a FamilyTree consisting of members from a specific instance
 * of the House object.*/

public class FamilyTree {
	//References to the patriarch and matriarch of the House which the FamilyTree is based off of.
	private CharacterNode patriarch;
	private CharacterNode matriarch;
	
	/**CharacterNode class: Each instance of this class is a different item in the family tree that contains a reference
	 * to the character in question, a reference to the nodes containing their parents, and a sLinkedList of the nodes 
	 * containing their children. */
	private class CharacterNode implements Comparable<CharacterNode>{
		private Character character; 
		private CharacterNode father; 
		private CharacterNode mother;
		public sLinkedList<CharacterNode> children;
		
		private CharacterNode(Character c) {
			//CharacterNode constructor
			this.character = c;
		}
		
		@Override
		public int compareTo(CharacterNode o) {
			//Compares CharacterNodes by the names of characters in the nodes.
			return this.character.compareTo(o.character);
		}
		
		public String toString() {
			//Creates and returns a String that represents the CharacterNode.
			return this.character.getName() + " -father: " +this.character.getFather() + " -mother: " +
						this.character.getMother();
		}
		
	}
	
	
	/**buildFamilyTree(House) method: Using the house input, the method builds the family tree. It starts by creating
	 * the matriarch and patriarch CharacterNodes. Then, going through the House's members, it creates a list of the 
	 * House's members who aren't the matriarch or the patriarch. Then, calling the secondary buildFamilyTree method(),
	 * the method constructs the rest of the method.*/
	public void buildFamilyTree(House h) {
		this.matriarch = new CharacterNode(h.getMatriarch());
		this.patriarch = new CharacterNode(h.getPatriarch());
//		int count = 1;
		int size = h.members.size();
		sLinkedList<Character> clist = new sLinkedList<Character>();
		for (int i =1; i <= size; i++) {
			Character c = (Character) h.members.getAt(i);
			if (c != this.matriarch.character && c != this.patriarch.character) {
				clist.add((Character) h.members.getAt(i));
			}
		}
		//Calls the secondary buildFamilyTree method on both the matriarch and patriarch in order to build the rest of
		//the tree.
		this.matriarch = buildFamilyTree(this.matriarch, clist);
		this.patriarch = buildFamilyTree(this.patriarch, clist);

	}
	
	/**buildFamilyTree(CharacterNode, sLinkedList<Character>) method: This method takes in a CharacterNode and a
	 * sLinkedList of Characters as input. If the CharacterNode does not have a children list, the method searches through
	 * the Character list to see if any of the Characters are the children of the Character in the input CharacterNode. If
	 * the Character has no kids in the list, the method returns the node.
	 * If the Character does have kids in the list, the CharacterNode is sent through the method again. Because the 
	 * CharacterNode now has children, it moves to the second part of the method. There, it goes through the list of
	 * the CharacterNode's children, calling itself on each of the CharacterNode's children in order to keep building
	 * the family tree.*/
	public CharacterNode buildFamilyTree(CharacterNode cn, sLinkedList<Character> clist) {
		//Scenario 1: The CharacterNode's list of children is null.
		if (cn.children == null) {
			cn.children = new sLinkedList<CharacterNode>();
			for (int i = 1; i <= clist.size(); i++) {
				boolean dad = cn.character.getName().equals(((Character) clist.getAt(i)).getFather());
				boolean mom = cn.character.getName().equals(((Character) clist.getAt(i)).getMother());
				//If the Character in the input CharacterNode is a parent of the Character at position i in the Character
				//list, a CharacterNode is created for the Character at position i, which is added to the input 
				//CharacterNode's children, while the input CharacterNode is set as the mother or father of the new 
				//CharacterNode.
				if (dad == true || mom == true) {
					CharacterNode cn2 = new CharacterNode((Character) clist.getAt(i));
					CharacterNode inlist1 = findCharacterNode(this.matriarch, cn2);
					CharacterNode inlist2 = findCharacterNode(this.patriarch, cn2);
					if (inlist1 != null) {
						cn2 = inlist1;
					}
					if (inlist2 !=null) {
						cn2 = inlist2;
					}
					cn.children.add(cn2);
					if (dad == true) {
						cn2.father = cn;
					}
					if (mom == true) {
						cn2.mother = cn;
					}
				}
			}
			//If there were no children added to the input CharacterNode's children list, the method returns the
			//CharacterNode. If not, the CharacterNode is set to the result of the method being called on itself again.
			if (cn.children.isEmpty() == true) {
				return cn;
			} else {
				cn = buildFamilyTree(cn, clist);
			}
		} else {
			//Scenario 2: The CharacaterNode has children.
			for (int i = 1; i <= cn.children.size(); i++) {
				//The method is called on each child of the CharacterNode in order to continue down the family tree and
				//see if they have any children themselves.
				CharacterNode cn2 = (CharacterNode) cn.children.getAt(i);
				cn2 = buildFamilyTree((CharacterNode) cn.children.getAt(i), clist);
			}
			//Once the part of the family tree below the CharacterNode is built, the method returns the CharacterNode.
			return cn;
		}
		return cn;
	}
	
	public String toString() {
		//Creates and returns a String that represents the family tree.
		return "Patriarch: " + this.patriarch.character.getName() + "\n" + printTree(this.patriarch, 1)
			   + "\n" + "Matriarch: " + this.matriarch.character.getName() + "\n" + printTree(this.matriarch, 1) + "\n";
	}
	
	/**printTree(CharacterNode, int) method: This is a secondary method called by the toString method. It insures that
	 * each generation of tree is placed into the String that represents the tree, indenting each line a certain amount
	 * depending on which generation a CharacterNode belongs to.  */
	private String printTree(CharacterNode c, int depth) {
		String str = "";
		if (c.children == null) {
			return str;
		} else {
			for (int i = 1; i <= c.children.size(); i++) {
				for (int j = 0; j < depth; j++) {
					str+= "\t";
				}
				str+= c.children.getAt(i).toString() + "\n" + printTree((CharacterNode) c.children.getAt(i), depth+1);
			}
			return str;
		}
	}
	
	
	/**contains() method: The input for this method is a Character. The method traverses the tree, seeing
	 * if any nodes in the list contain the input Character. If any do, the method returns a boolean value of true. If not,
	 * the method returns a boolean value of false.  
	 */
	public boolean contains(Character look) {
		CharacterNode cn = new CharacterNode(look);
		//The method is structured to look through the family tree starting from the patriarch and matriarch separately
		//because there are some Characters who are related to the patriarch but not related to the matriarch, and vice
		//versa.
		CharacterNode ans1 = this.findCharacterNode(this.patriarch, cn);
		if (ans1 == null) {
			CharacterNode ans2 = this.findCharacterNode(this.matriarch, cn);
			if (ans2 == null) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	/**findCharacterNode() method: The input for this method is two CharacterNodes. The first CharacterNode serves as the
	 * starting point for the search, while the second CharacterNode is the one being searched for.
	 * If the two nodes are the same, the method returns the first CharacterNode. If not, the method checks to see if the 
	 * first CharacterNode has children. If not, the method returns a null value. If the CharacterNode does have children, 
	 * the method goes through the children list, comparing each kid (and each kid of theirs) to the second Character by
	 * calling itself, using the CharacterNode at position i as the new starting point.  
	 */
	private CharacterNode findCharacterNode(CharacterNode startingPoint, CharacterNode cn) {
		//Base case: the starting point and the CharacterNode match.
		if (startingPoint.compareTo(cn) == 0) {
			return startingPoint;
		} else {
			//Base case 2: the starting point doesn't match, and doesn't have any children.
			if (startingPoint.children == null) {
				return null;
			} else {
				//Recursive case: the method calls itself using the CharacterNode at position i of the first
				//CharacterNode's children and the CharacterNode it's looking for.
				CharacterNode mark = null;
				for (int i = 1; i <= startingPoint.children.size(); i++) {
					if (findCharacterNode((CharacterNode) startingPoint.children.getAt(i), cn) !=null) {
						mark = findCharacterNode((CharacterNode) startingPoint.children.getAt(i), cn);
					}
				}
				return mark;
			}
		}
	}

	
	/**printLineage(): The method first takes in a Character input. Then, using the input, creates a new
	 * CharacterNode and calls the findCharacterNode() method in order to see if the family tree has a matching
	 * CharacterNode. If it does, it calls the lineageToString method to print the Character's lineage on
	 * their father's side and their mother's side. */
	public void printLineage(Character look) {
		CharacterNode cn = new CharacterNode(look);
		CharacterNode ans1 = this.findCharacterNode(this.patriarch, cn);
		if (ans1 == null) {
			CharacterNode ans2 = this.findCharacterNode(this.matriarch, cn);
			if (ans2 == null) {
				//Do nothing
			} else {
				System.out.println(ans2.toString());
				System.out.println("Father's Side:");
				System.out.println(this.lineageToString(ans2.father));
				System.out.println("\n" + "Mother's Side:");
				System.out.println(this.lineageToString(ans2.mother));
			}
		} else {
			System.out.println(ans1.toString());
			System.out.println("Father's Side:");
			System.out.println(this.lineageToString(ans1.father));
			System.out.println("\n"+"Mother's Side:");
			System.out.println(this.lineageToString(ans1.mother));
		}
	}
	
	
	/**lineageToString(): The method first takes in a CharacterNode input. First, the method checks two base
	 * cases to see if the CharacterNode is null or is the patriarch/matriarch, and returns either a null value or a 
	 * String representative of that CharacterNode depending on the result.
	 * If the CharacterNode does not match any of the base cases, it moves to the recursive case, where the method calls
	 * itself on the father and mother of the CharacterNode (assuming they aren't null), and returns a String that
	 * represents the CharacterNode and the lineage of their parents.*/
	public String lineageToString(CharacterNode cn) {
		if (cn == null) {
			return "";
		}
		String str = " " + cn.toString();
		if (cn.compareTo(this.patriarch) == 0 || cn.compareTo(this.matriarch) == 0) {
			return str;
		} else {
			String mother = "";
			String father = "";
			if (cn.father != null) {
				father += "\n" + lineageToString(cn.father);
			}
			if (cn.mother != null) {
				mother += "\n" + lineageToString(cn.mother);
			}
			return str += father + mother;
		}
	}
	
}
