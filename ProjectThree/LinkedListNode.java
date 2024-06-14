package ProjectThree;

public class LinkedListNode {


	private Edge edge;
	private LinkedListNode next;

	public LinkedListNode(Edge edge) {
		this.edge = edge;
	}
	
	public Edge getEdge() {
		return edge;
	}

	public void setEdge(Edge vertix) {
		this.edge = vertix;
	}

	public LinkedListNode getNext() {
		return next;
	}

	public void setNext(LinkedListNode next) {
		this.next = next;
	}
}
