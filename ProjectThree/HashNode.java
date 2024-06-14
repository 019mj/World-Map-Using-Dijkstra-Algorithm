package ProjectThree;

public class HashNode {
	private Vertix data;
	private char flag;
	
	public HashNode(Vertix data, char flag) {
		this.data = data;
		this.flag = flag;
	}

	public Vertix getData() {
		return data;
	}

	public char getFlag() {
		return flag;
	}

	public void setData(Vertix data) {
		this.data = data;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}
	
	public String toString() {
		return this.data +"";
	}
	
}
