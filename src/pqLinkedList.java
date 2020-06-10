package src;

/** pqLinkedList class: Each instance of this class is a queue that stores instances of a specific variable type (based on
 * how it is instantiated). Each list is made of nodes that contain a piece of data, a reference to the next node
 * in the list, and a number which represents the node's priority.*/

public class pqLinkedList<E extends Comparable<E>> extends sLinkedList<E> {
		//Reference to the front of the list
		private pqNode front;
		
		/**pqNode class: Each instance of this class is a node that is essentially one item in of whatever pqLinkedList
		 * it happens to be inside. */
		private class pqNode<E extends Comparable <E>> {
			//Instance variables for the node's data, a reference to the following node, and the node's priority.
			private E data;
			private pqNode next;
			private float priority;
			
			//Constructors
			private pqNode(E e, pqNode n, float p) {
				this.data = e;
				this.next = n;
				this.priority = p;
			}
			
			private pqNode(E e, float p) {
				this.data = e;
				this.priority = p;
			}
			
			//Compares nodes by priority.
			public int compareTo(pqLinkedList<E>.pqNode<E> arg0) {
				if (this.priority < arg0.priority) {
					return -1;
				} else if (this.priority > arg0.priority) {
					return 1;
				} else {
					return 0;
				}
			}
			
		}
		
		/**insert() method: this method adds a new node to the pqLinkedList based off the input data. */
		public void insert (E e, float p) {
			pqNode<E> current = this.front;
			if (current == null) {
				this.front = new pqNode<E>(e, p);
			} else {
				//If the input's priority happens to be higher than the first node's priority, the input element is placed
				//at the front of the queue. 
				if (p < this.front.priority) {
					pqNode<E> temp = new pqNode<E>(e, p);
					temp.next = current;
					this.front = temp;
				} else {
					while (current.next != null) {
						current = current.next;
					}
					current.next = new pqNode<E>(e, p);
				}
			}
		}
		
		//Takes out the first node in the queue.
		public String remove() {
			String temp = this.front.data.toString();
			this.front = this.front.next;
			return temp;
		}
		
		/** sort() method: This method is separate from the MergeSort method (which sorts the PQ) because the MergeSort 
		 * method is recursive and needs an input to work.
		 */
		public void sort() {
			this.front = this.MergeSort(front);
		}
		
		/** MergeSort() method: The input is one pqNode. After checking if the list has more than one element, the method 
		 * searches for the middle of the list and breaks it into two halves on that point, and then proceeds to continually 
		 * break the list down until it reaches the base case (current == null || current.next == null). Then, working its 
		 * way back up, the method begins to piece together all the parts of the list while sorting them until
		 * the list is whole and sorted. Then, it returns the sorted list.*/
		private pqNode MergeSort(pqNode current) {
			if (current == null || current.next == null) {
				return current;
			}
			
			pqNode<E> mid = getMiddle(current);
			pqNode<E> nextofmid = mid.next;
			mid.next = null;
			
			pqNode<E> left = MergeSort(current);
			pqNode<E> right = MergeSort(nextofmid);
			
			pqNode<E> newfront = sortedMerge(left, right);
			
			return newfront;
		}
		
		/**sortedMerge() method: The input for this function is two pqNodes. After making sure that it has two 
		 * nodes to compare, the method checks which node has a lesser priority, then changes the node with the greater 
		 * priority with the node that follows it, and runs that node through sortedMerge again with the node of lesser 
		 * priority. This continues until the program gets to a point where there are not two nodes to compare. */
		private pqLinkedList<E>.pqNode<E> sortedMerge(pqLinkedList<E>.pqNode<E> left, pqLinkedList<E>.pqNode<E> right) {
			pqNode<E> sm = null;
			
			if (left == null) {
				return right;
			} 
			if (right == null) {
				return left;
			}
			
			if (left.compareTo(right) < 0 || left.compareTo(right) == 0) {
				sm = left;
				sm.next = sortedMerge(left.next, right);
			} else if (left.compareTo(right) > 0) {
				sm = right;
				sm.next = sortedMerge(left, right.next);
			} 
			return sm;
		}
		
		/**getMiddle() method: The input for this method is one pqNode. Using the head of the list, 
		    * the method creates two variables that gauge where the middle of the list is by having one sllNode variable 
		    * reach the end of the list while another moves at half the speed. This makes it so when the first variable 
		    * reaches the end, the second variable has found the middle of the list. Then, it returns the variable that 
		    * found the middle.*/
		private pqLinkedList<E>.pqNode<E> getMiddle(pqNode current) {
			pqNode<E> one = current;
			pqNode<E> two = current;
			
			while(two.next !=null) {
				two = two.next;
				if (two.next != null) {
					two = two.next;
					one = one.next;
				}
			}
			
			return one;
		}

		//Checks to see if the PQ is empty.
		public boolean isEmpty() {
			if (this.front == null) {
				return true;
			} else {
				return false;
			}
			
		}
		
		//Returns a String representation of the first element in the PQ.
		public String peek() {
			return this.front.data.toString();
		}
}
