package graphicsEditor;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text extends Shape {

	private String content;
	private Font font;
	
	public Text(int x, int y, Color c, String content, Font font) {
		super(x, y, font.getSize()*content.length(), font.getSize(), c);
		this.content = content;
		this.font = font;
	}

	@Override
	public void draw(Graphics g) {
		g.setFont(font);
		g.setColor(c);
		g.drawString(content, x, y + height);
		
	}

	@Override
	public boolean isOn(int x, int y) {
		if (x <= this.x + width && x >= this.x)
			if (y >= this.y && y <= this.y+height)
				return true;
		return false;
	}
	
	public Shape copy() {
		return new Text(x, y, c, content, font);
	}

	public void resize(int x1, int y1, int x2, int y2) {}
}
