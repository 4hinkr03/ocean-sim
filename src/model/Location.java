package model;

public class Location {

	private int x, y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void update(Location location) {
		setX(location.getX());
		setY(location.getY());
	}
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public String toString() {
		return "[Location: x=" + getX() + ", y=" + getY() + "]";
	}
}
