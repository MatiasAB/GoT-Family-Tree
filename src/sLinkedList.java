package project3;

import java.util.Collections;
import java.util.List;

import project3.Character;

import java.util.*;

/** sLinkedList class: Each instance of this class is a list that stores instances of a specific variable type (based on
 * how it is instantiated). Each list is made of nodes that contain a piece of data and a reference to the next node
 * in the list.*/

public class sLinkedList<E extends Comparable<E>> {
	// Reference to front of the list
	private sllNode front;
		
		/**sllNode class: Each instance of this class is a node that is essentially one item in of whatever sLinkedList
		 * it happens to be inside. */
		private class sllNode<E extends Comparable<E>> {
			private E data;
			private sllNode next;
		
			//Constructors
			private sllNode(E e, sllNode next){
				this.data = e;
				this.next = next;
			}
		
			private sllNode (E e) {
				this.data = e;
			}
			
			
		}
		
		public sllNode getFront() {
			//Returns a reference to the front of the list.
			return this.front;
		}
		
		/**add() method: this method adds a new node to the sLinkedList based off the input data. */
		public void add (E e) {
			//Reference to the beginning of the list that helps the method traverse the list.
			sllNode<E>current = front;
			//If the list is empty, the method makes the front variable a reference to a node with the input data.
			if (front == null) {
				front = new sllNode<E>(e);
			} else {
				//If the list isn't empty, the method traverses the list until it gets to the end, and adds on a new node
				//which contains the input data.
				while (current.next != null) {
					current = current.next;
				}
				current.next = new sllNode<E>(e);
			}
			
			
		}
		
		
		/** Function: Starting from the beginning of the list, this method checks to see if the list is sorted
		 * by comparing the data in current.data against the data in current.next.data.
		 * If current.data comes after cuurent.next.data, the method determines if the list is not sorted.
		 */
		public boolean isSorted() {
			sllNode<E>current = this.front;
			boolean isit = true;
			while (current != null && current.next != null) {
				if (current.next.data.compareTo(current.data) < 0) {
					isit = false;
				}
				current = current.next;
			}
			return isit;
		}
		
		
		/** sort() method: This method is separate from the MergeSort method because the MergeSort method is
		 * recursive and needs an input to work.
		 */
		public void sort() {
			this.front = this.MergeSort(front);
		}
		
		
	 /** MergeSort() method: The input is one sllNode. After checking if the list has more than one element, the method searches for the middle of the
	  * list and breaks it into two halves on that point, and then proceeds to continually break the list
	  * down until it reaches the base case (current == null || current.next == null). Then, working its 
	  * way back up, the method begins to piece together all the parts of the list while sorting them until
	  * the list is whole and sorted. Then, it returns the sorted list.*/
	   public sllNode MergeSort(sllNode current){ 
	        if (current == null || current.next == null) { 
	            return current; 
	        } 
	  
	       // sends the front node to a different method to determine the middle of the list.
	       sllNode mid = getMiddle(current); 
	       
	       //Separates the two halves of the list by making a variable to save the sllNode right of the middle
	       //(along with the nodes that follow), then setting it to null on the left half of the list so that the
	       //new end point is the end of the left half and not the end of the whole list.
	       sllNode nextofmid = mid.next; 
	       mid.next = null; 
	  
	       sllNode left = MergeSort(current); 
	  
	       sllNode right = MergeSort(nextofmid); 
	       
	       //Sends the two halves of the list to a different method to be sorted and merged together.
	       sllNode sortedlist = sortedMerge(left, right);
	       return sortedlist; 
	    } 
	  
	  
	   /**getMiddle() method: The input for this method is one sllNode. Using the head of the list, 
	    * the method creates two variables that gauge where the middle of the list is by having one sllNode variable 
	    * reach the end of the list while another moves at half the speed. This makes it so when the first variable 
	    * reaches the end, the second variable has found the middle of the list. Then, it returns the variable that 
	    * found the middle.*/
	    public sllNode getMiddle(sllNode current){
	    	//Base case to check if the list is empty. 
	        if (current == null) {
	        	return current; 
	        }
	            
	        //The two variables used to traverse the list. sllNode "two" travels twice as fast as sllNode "one".
	        //Using a loop, this methods lets the sllNodes traverse the list until "two" reaches the end, placing
	        //"one" in the middle of the list.
	       sllNode two = current.next; 
	       sllNode one = current; 
	          
	        while (two != null) { 
	            two = two.next; 
	            if(two!=null) 
	            { 
	                one = one.next; 
	                two=two.next; 
	            } 
	        } 
	        return one; 
	    } 
	    
	
		/**sortedMerge() method: The input for this function is two sllNodes. After making sure that it has two 
		 * nodes to compare, the method checks which node has a lesser value, then changes the node with the greater 
		 * value with the node that follows it, and runs that node through sortedMerge again with the node of lesser value.
		 * This continues until the program gets to a point where there are not two nodes to compare. */
	    public sllNode sortedMerge(sllNode l,sllNode r)  { 
			
	       sllNode result = null; 

	        if (l == null) {
	        	return r; 
	        }
	            
	        if (r == null) {
	        	return l;
	        }
	      

	        if (r.data.compareTo(l.data) > 0) {
	        	result = l;
	        	result.next = sortedMerge(l.next, r);
	        } else {
	        	result = r;
	        	result.next = sortedMerge(l, r.next);
	        }
	        return result; 
	  
	    }
	    
	    
	    /**get() method: This method searches the sLinkedList to see if it has a node that contains data matching the
	     * input data. If it does, it returns that data. */
	    public Object get(Object o) {
	    	sllNode current = this.front;
	    	while (current != null) {
	    		if (current.data.compareTo(o) == 0) {
	    			return current.data;
	    		}
	    		current = current.next;
	    	}
	    	return null;
	    }
	    
	    /**get() method: Similar to the get() method above. Using the input integer, the method traverses the list until
	     * a count variable matches the input integer, and returns the data of whatever node happens to be at that
	     * position. If the the size of the list is smaller than the input integer, the method returns null. */
	    public Object getAt(int i) {
	    	sllNode current = this.front;
	    	int count = 1;
	    	while (current != null) {
	    		if (count == i) {
	    			return current.data;
	    		}
	    		count++;
	    		current = current.next;
	    	}
	    	return null;
	    }
	    
	    
	    
	    public int size() {
	    	sllNode current = this.front;
	    	int count = 0;
	    	while (current != null) {
	    		count++;
	    		current = current.next;
	    	}
	    	return count;
	    }
	 

		/**contains() method: The input for this method is an Object (of any type). The method traverses the list, seeing
		 * if any nodes in the list contain the input data. If any do, the method returns a boolean value of true. If not,
		 * the method returns a boolean value of alse.  
		 */
		public boolean contains(Object o) {
			boolean f = false;
			if (o == null) {
				return false;
			}
			sllNode<E>current = this.front;
			while (current != null) {
				if (current.data.compareTo((E) o) == 0) {
					f = true;
				}
				current = current.next;
			}
			return f;
		}
		
		
		public String toString() {
			//Creates and returns a String representative of the sLinkedList.
			sllNode current = this.front;
			String listStr = "";
			while (current != null) {
				listStr += current.data.toString();				
				current = current.next;
			}
			return listStr;
		}

		public boolean isEmpty() {
			//Checks to see if the method is empty.
			if (this.front == null) {
				return true;
			} else {
				return false;
			}
		}

		/**remove() method: Using the input data, this method traverses the list to see if any nodes contain matching
		 * data. If it does, the node is removed from the list. If not, nothing happens. */
		public void remove(E e) {
			if (this.front != null) {
				sllNode current = this.front;
				while (current != null && current.next != null) {
					if (current.next.data == e && current.next.next !=null) {
						current.next = current.next.next;
					} else if (current.next.data == e) {
						current.next = null;
					}
					current = current.next;
				}
			}
		}
		
		
		public void empty() {
			//Clears the list of all its contents.
			this.front = null;
		}
	
		
}
