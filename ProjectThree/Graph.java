package ProjectThree;

public class Graph {
	private int numberOfVertices;
	private HashTable hashTable;

	public Graph(int numberOfVertices) {
		this.numberOfVertices = numberOfVertices;
		hashTable = new HashTable(this.numberOfVertices);
	}

	public void addVertix(Vertix vertix) {
		hashTable.put(vertix);
	}

	public Vertix getVertix(String countryName) {
		return hashTable.getVertex(countryName);
	}

	public HashTable getHashTable() {
		return hashTable;
	}

	public HeapNode getResult(int numberOfEdges, String source, String destination) {
		Vertix sourceVertix = this.getVertix(source);

		Heap heap = new Heap(numberOfEdges);

		LinkedList path = new LinkedList(); 
		path.addLast(new Edge(sourceVertix, sourceVertix));
		HeapNode first = new HeapNode(sourceVertix, 0, path);
		heap.insert(first);

		while (!heap.isEmpty()) {
			HeapNode curr = heap.remove();

			if (curr.getVertix().getCountry().getCountryName().equals(destination)) {
				hashTable.setAllVerticesToFalse();
				return curr;
			}

			if (curr.getVertix().isVisited()) {
				continue;
			}

			curr.getVertix().setVisited(true);

			LinkedList vertices = curr.getVertix().getVertices();
			LinkedListNode node = vertices.getFirstNode();
			while (node != null) {
				if (!node.getEdge().getDestination().isVisited()) {
					LinkedList edges = new LinkedList();
					edges.addAll(curr.getPath());
					edges.addLast(node.getEdge());
					HeapNode heapNode = new HeapNode(node.getEdge().getDestination(),
							curr.getCost() + node.getEdge().getCost(),
							edges);
					heap.insert(heapNode);
				}
				node = node.getNext();
			}
		}

		hashTable.setAllVerticesToFalse();
		return null;
	}

}
