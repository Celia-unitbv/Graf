import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc
{
	private Point start;
	private Point end;
	private Node startNode;
	private Node endNode;
	public Arc(Point start, Point end, Node startNode, Node endNode)
	{
		this.start = start;
		this.end = end;
		this.startNode = startNode;
		this.endNode = endNode;
	}

	public void update(Point newStart, Point newEnd) {
		// Update the start and end points of the arc based on the new positions
		this.start = newStart;
		this.end = newEnd;
	}
	public void moveArc(int deltaX, int deltaY, Node n) {
		if (n == startNode) {
			start.x += deltaX;
			start.y += deltaY;
		}
		if (n == endNode) {
			end.x += deltaX;
			end.y += deltaY;
		}
	}

	public void drawArc(Graphics g)
	{
		if (start != null)
		{
			g.setColor(Color.RED);
			g.drawLine(start.x, start.y, end.x, end.y);
		}
	}
	public void drawArrow(Graphics g) {
		g.setColor(Color.BLUE);

		g.drawLine(start.x, start.y, end.x, end.y);

		double angle = Math.atan2(end.y - start.y, end.x - start.x);

		int size = 15;

		int[] x = {end.x, (int) (end.x - size * Math.cos(angle - Math.toRadians(30))), (int) (end.x - size * Math.cos(angle + Math.toRadians(30)))};
		int[] y = {end.y, (int) (end.y - size * Math.sin(angle - Math.toRadians(30))), (int) (end.y - size * Math.sin(angle + Math.toRadians(30)))};
		int numPoints = 3;
		g.fillPolygon(x, y, numPoints);
	}
}