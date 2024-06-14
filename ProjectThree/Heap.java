package ProjectThree;

public class Heap {
	private HeapNode[] heap;
	private int size;
	private int maxSize;

	public Heap(int maxSize) {
		this.maxSize = maxSize;
		this.size = 0;
		this.heap = new HeapNode[maxSize + 1];
	}

	private void minHeapify(int pos) {
		int smallest = pos;
		int left = leftChild(pos);
		int right = rightChild(pos);

		if (left <= size && heap[left].getCost() < heap[smallest].getCost()) {
			smallest = left;
		}

		if (right <= size && heap[right].getCost() < heap[smallest].getCost()) {
			smallest = right;
		}

		if (smallest != pos) {
			swap(pos, smallest);
			minHeapify(smallest);
		}
	}

	public void insert(HeapNode element) {
		if (size >= maxSize) {
			System.out.println("Heap is full, cannot insert");
			return;
		}

		heap[++size] = element;
		int current = size;

		while (current > 1 && heap[current].getCost() < heap[parent(current)].getCost()) {
			swap(current, parent(current));
			current = parent(current);
		}
		System.out.println("Inserted: " + element.getVertix().getCountry().getCountryName() + ", Cost: "
				+ element.getCost() + " at position: " + current);
	}

	public HeapNode remove() {
		if (size == 0) {
			System.out.println("Heap is empty, cannot remove");
			return null;
		}
		HeapNode popped = heap[1];
		heap[1] = heap[size--];
		minHeapify(1);
		System.out.println("Removed: " + popped.getCost());
		return popped;
	}

	public int getSize() {
		return size;
	}

	private int parent(int pos) {
		return pos / 2;
	}

	private int leftChild(int pos) {
		return 2 * pos;
	}

	private int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	private void swap(int fpos, int spos) {
		HeapNode tmp = heap[fpos];
		heap[fpos] = heap[spos];
		heap[spos] = tmp;
		System.out.println("Swapped: " + heap[fpos].getVertix().getCountry().getCountryName() + ", Cost: "
				+ heap[fpos].getCost() + " with: " + heap[spos].getVertix().getCountry().getCountryName() + ", Cost: "
				+ heap[spos].getCost());
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void printHeap() {
		for (int i = 1; i <= size; i++) {
			System.out.print(heap[i].getCost() + " ");
		}
		System.out.println();
	}
}
