package model;

public class Location {

	private int x, y;

	public Location() {
		
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
	
	public void updateLocation(int x, int y) {
		if(canMoveTo(x, y)) {
			setX(x);
			setY(y);
		}
	}
	
	private boolean canMoveTo(int x, int y) {
		int diffX = getX() + x;
		int diffY = getY() + y;
		return Math.abs(diffX) <= 1 &&
				Math.abs(diffY) <= 1;
	}
	
}
