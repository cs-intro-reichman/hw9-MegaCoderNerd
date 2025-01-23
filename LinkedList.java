/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException(
					"index must be between 0 and size");
		}
		if (index == 0) {
			addFirst(block);
			return;
		} 
		Node current = first;
		// as we start at the first node, and we don't want to iterate over a null node
		// which is the end of the list
		int i = 1;
		while(i < index) {
			current = current.next;
			i++;
		}
		if (size == index){
			current.next = new Node(block);
			last = current.next;
		} else{
			Node temp = current.next;
			current.next = new Node(block);
			current.next.next = temp; 
		}
		size++;
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		add(size, block);
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		
		Node current = first;
		first = new Node(block);
		first.next = current;
		if (size == 0) last = first;
		size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index >= size || index < 0) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node curr = first;
		while (index > 0) {
			index--;
			curr = curr.next;
		}
		return curr.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		ListIterator iter = this.iterator();
		int index = 0;
		while (iter.hasNext()) {
			if (iter.next().equals(block)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (this.indexOf(node.block) == -1) {
			throw new IllegalArgumentException(
					"the given node is not in this list");
		}
		Node curr = first;
		Node prNode	= null;

		while (curr != null) {
			if (curr.equals(node)) {
				if (prNode == null) {
					if (size == 1){
						last = null;
					}
					first = curr.next;
				} else {
					
					if (size != 2 && !last.equals(node)){
						
						prNode.next = curr.next;
						prNode = curr;
						
					} else if(size != 2){
						last = prNode;
						prNode.next = curr.next;
						prNode = curr;
					}
					else{
						last = first;
						first.next = null;
						
					}

				}
				size--;
				break;
			}
			prNode = curr;
			curr = curr.next;
		}
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		remove(this.getNode(index));
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		remove(this.getNode(this.indexOf(block)));
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		//// Replace the following statement with your code
		String result = "";
		if (size == 1) {
			return first.block.toString();
		} else if (size == 0) {
			return "";
		}
		result = "" + first.block;
		ListIterator iter = this.iterator();
		iter.next();
		int index = size;
		while (iter.hasNext() && index > 2) {
			result += " " + iter.next();
			index--;
		}
		result += " " + iter.next().toString() + "";
		return result;
	}
}