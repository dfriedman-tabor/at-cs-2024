package graphicsEditor;


public class Point {
	public int x, y;
	public Point(int x, int y) {
		this.x = x; this.y = y;
	}

	public String toString() {
		return x + ", " + y;
	}
	
	public boolean equals(Object p) {
		if (p.getClass().equals(Point.class))
			return ((Point)p).x == x && ((Point)p).y == y;
		return false;
	}
}
