package ProjectThree;

public class Vertix {
	private Country country;
	private LinkedList vertices = new LinkedList();
	private boolean visited;

	public Vertix(Country country) {
		this.country = country;
		this.visited = false;
	}
	
	public Country getCountry() {
		return country;
	}

	public LinkedList getVertices() {
		return vertices;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public int hashCode() {
		return country.getCountryName().hashCode();
	}
}
