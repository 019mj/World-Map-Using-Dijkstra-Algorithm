package ProjectThree;

public class LinkedList {

    private LinkedListNode first;
    private int size; // size attribute to keep track of the number of elements

    public LinkedList() {
        first = null;
        size = 0; // Initialize size to 0
    }

    public LinkedListNode getFirstNode() {
        return first;
    }

    public LinkedListNode setFirstNode(LinkedListNode n) {
        return first = n;
    }

    public void addFirst(Edge edge) { // O(1)
        LinkedListNode temp = new LinkedListNode(edge);

        // empty list
        if (first == null)
            first = temp;
        else { // adding as first
            temp.setNext(first);
            first = temp;
        }

        size++; // Increment size
    }

    public void addLast(Edge vertix) { // O(n)
        LinkedListNode temp = new LinkedListNode(vertix);

        // empty list
        if (first == null)
            first = temp;
        else {
            LinkedListNode curr = first;
            // looping until last element
            while (curr.getNext() != null)
                curr = curr.getNext();

            curr.setNext(temp); // adding to last
        }

        size++; // Increment size
    }

    public boolean deleteFirst() { // O(1)
        if (first == null) // empty list
            return false;

        else {
            LinkedListNode temp = first;
            first = first.getNext();
            temp.setNext(null);
        }
        size--; // Decrement size
        return true;
    }

    public boolean deleteLast() { // O(n)
        if (first == null) // empty list
            return false;

        else {
            LinkedListNode current = first;
            // stops at one before last node
            while (current.getNext().getNext() != null)
                current = current.getNext();

            current.setNext(null);
        }
        size--; // Decrement size
        return true;
    }

    public Edge get(String name) { // O(n)
        LinkedListNode curr = getNode(name);

        if (curr == null)
            return null; // not found

        else
            return curr.getEdge(); // found
    }

    public LinkedListNode getNode(String name) {
        if (first != null) { // checking for empty list
            LinkedListNode curr = first;

            while (curr != null) {
                if (curr.getEdge().getDestination().getCountry().getCountryName().equals(name))
                    return curr; // found

                curr = curr.getNext();
            }
        }
        return null; // not found
    }

    public Edge getFirst() { // O(1)
        if (first == null)
            return null;
        return first.getEdge();
    }

    public Edge getLast() { // O(n)
        if (first == null)
            return null;

        LinkedListNode curr = first;
        // looping until last element
        while (curr.getNext() != null)
            curr = curr.getNext();

        return curr.getEdge();
    }

    public void printList() { // O(n)
        LinkedListNode current = first;
        if (first == null)
            return;
        while (current != null) {
            System.out.println(current.toString());
            current = current.getNext();
        }
    }

    public boolean isEmpty() {
        return first == null;
    }
    
    public int getSize() {
        return size; // Return the current size
    }

    public void addAll(LinkedList list) {
        if (list == null || list.isEmpty()) {
            return; // If the provided list is null or empty, do nothing
        }

        // Get the first node of the provided list
        LinkedListNode current = list.getFirstNode();

        while (current != null) {
            // Add each element of the provided list to the end of the current list
            this.addLast(current.getEdge());
            current = current.getNext();
        }
    }
    
    public LinkedListNode getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        LinkedListNode current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

}
