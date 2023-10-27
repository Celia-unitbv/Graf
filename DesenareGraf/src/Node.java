import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
public class Node
{
	private int coordX;
	private int coordY;
	private int number;
	private List<Arc> connectedArcs;
	public Node(int coordX, int coordY, int number)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
		connectedArcs = new ArrayList<>();
	}
	
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public void drawNode(Graphics g, int node_diam)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g.fillOval(coordX, coordY, node_diam, node_diam);
		g.setColor(Color.WHITE);
		g.drawOval(coordX, coordY, node_diam, node_diam);
		if(number < 10)
			g.drawString(((Integer)number).toString(), coordX+13, coordY+20);
		else
			g.drawString(((Integer)number).toString(), coordX+8, coordY+20);
	}
	public boolean inNod(Point point) {
		int raza = 15;
		int nodeCenterX = coordX + raza;
		int nodeCenterY = coordY + raza;
		double distance = Math.sqrt(Math.pow(point.getX() - nodeCenterX, 2) + Math.pow(point.getY() - nodeCenterY, 2));
		if(distance <= raza) return true;
		else return false;
	}
	public boolean Mutnod(Point point) {
		int raza = 15;
		int nodeCenterX = coordX + raza;
		int nodeCenterY = coordY + raza;
		double distance = Math.sqrt(Math.pow(point.getX() - nodeCenterX, 2) + Math.pow(point.getY() - nodeCenterY, 2));
        return distance <= raza - 10;
	}
	public void move(int X, int Y) {
		coordX += X;
		coordY += Y;
	}
	public void addArd(Arc arc){
		connectedArcs.add(arc);
	}

	public List<Arc> getConnectedArcs() {
		return connectedArcs;
	}
}
