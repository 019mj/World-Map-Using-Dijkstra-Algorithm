package ProjectThree;

public class Country {
	
	private String countryName;
	private double longitude;
	private double latitude;
	
	private double x;
	private double y;

	public Country(String countryName, double longitude, double latitude) {
		setCountryName(countryName);
		setLatitude(latitude);
		setLongitude(longitude);
	}
	
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
}
