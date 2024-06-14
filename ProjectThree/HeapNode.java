package ProjectThree;


public class HeapNode {

	private Vertix vertix;
	private double cost;
	private LinkedList path;

	public HeapNode(Vertix vertix, double cost, LinkedList path) {
		this.vertix = vertix;
		this.cost = cost;
		this.path = path;
	}

	public double getCost() {
		return cost;
	}

	public LinkedList getPath() {
		return path;
	}

	public Vertix getVertix() {
		return vertix;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setPath(LinkedList path) {
		this.path = path;
	}

	public void setVertix(Vertix vertix) {
		this.vertix = vertix;
	}

//	public String getFormattedPath() {
//		StringBuilder pathString = new StringBuilder();
//		for (Country edge : path) {
//			pathString.append("From ").append(edge.getSource().getCountry().getCountryName())
//					.append(" To ").append(edge.getDestination().getCountry().getCountryName())
//					.append(" with Distance ").append(edge.getCost()).append("\n");
//		}
//		return pathString.toString();
//	}
}
