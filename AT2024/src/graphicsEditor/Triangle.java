package graphicsEditor;


import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends Shape {
	int x3, y3;
	
	public Triangle(int x, int y, int w, int h, int x3, int y3, Color c) {
		super(x, y, w, h, c);
		this.x3 = x3; this.y3 = y3;
	}

	@Override
	public Shape copy() {
		return new Triangle(x,y,width,height,x3,y3,c);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);

		//draw triangle

		int[] xpoints = {x, width, x3};

		int[] ypoints = {y, height, y3};

		g.fillPolygon(xpoints, ypoints, 3);
		
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		width = x2; height = y2;

		//find third point** new way

//		double theta = Math.atan( (double)(y2 - y1) / (x2-x1) );
//
//		double ourAngle = theta - Math.PI/3;
//
//			
//
//		double sideLength = Math.sqrt( Math.pow((x2 - x1),2) + Math.pow(y2 - y1,2) );
//
//		double a = sideLength * Math.sin(ourAngle);
//
//		double b = sideLength * Math.cos(ourAngle);
//
//
//
//		x3 = (int) (x1 + a);
//
//		y3 = (int) (y1 + b);
		
		
		double slope = (double) (y1 - y2) / (x2 - x1);
		double sideLength = Math.sqrt(Math.pow((x2 - x1),2) + Math.pow(y2 - y1,2));
		double projectionLength = sideLength * Math.sqrt(3) / 2;
		double xm = (double) x1 + x2 / 2;
		double ym = (double) y1 + y2 / 2;
		
		x3 = (int) (xm + projectionLength / (Math.sqrt(1 + Math.pow(slope, 2))));
		y3 = (int) (ym + projectionLength * slope / (Math.sqrt(1 + Math.pow(slope, 2))));
		
		
	}
	
	

}
