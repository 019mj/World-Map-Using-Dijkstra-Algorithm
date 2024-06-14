package ProjectThree;

public class Edge {
	private Vertix source;
	private Vertix destination;

	private double cost;

	public Edge(Vertix source, Vertix destination) {
		this.source = source;
		this.destination = destination;
		this.cost = calculateCost();
	}

	private double calculateCost() {
		double latSource = Math.toRadians(source.getCountry().getLatitude());
		double longSource = Math.toRadians(source.getCountry().getLongitude());
		double latDes = Math.toRadians(destination.getCountry().getLatitude());
		double longDes = Math.toRadians(destination.getCountry().getLongitude());
		
		double diffBetweenLat = latDes - latSource;
		double diffBetweenLong = longDes - longSource;
		

		double res = Math.pow(Math.sin(diffBetweenLat / 2), 2) + Math.pow(Math.sin(diffBetweenLong / 2), 2) * Math.cos(latSource) * Math.cos(latDes);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(res));
		this.cost = rad * c;
		return this.cost;
	}
	
	public Vertix getSource() {
		return source;
	}
	
	public Vertix getDestination() {
		return destination;
	}
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

}
