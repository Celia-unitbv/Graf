import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.io.BufferedWriter;
import javax.swing.JButton;


public class MyPanel extends JPanel {
	private int nodeNr = 1;
	private int node_diam = 30;
	private Vector<Node> listaNoduri;
	private Vector<Arc> listaArce;
	Point pointStart = null;
	Point pointEnd = null;
	private Node movingNode = null;
	boolean isDragging = false;
	int[][]MatriceAdiacentaOrientat = new int[50][50];
	int[][]MatriceAdiacentaNeorientat = new int[50][50];
	private boolean tipGraf = false;
	private JButton Buton;
	public MyPanel()
	{
		listaNoduri = new Vector<Node>();
		listaArce = new Vector<Arc>();
		//butonul
		Buton = new JButton("Selectare");
		Buton.setBounds(10, 10, 100, 30);
		Buton.addActionListener(e -> {
					tipGraf = !tipGraf;
					if (tipGraf) {
						Buton.setText("Graf Orientat");
						repaint();
					} else {
						Buton.setText("Graf Neorietntat");
						repaint();
					}
		});
		add(Buton);
		// borderul panel-ului

		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(new MouseAdapter() {
			//evenimentul care se produce la apasarea mousse-ului
			public void mousePressed(MouseEvent e) {
				pointStart = e.getPoint();
				if (movingNode == null) {
					for (Node node : listaNoduri) {
						if (node.inNod(e.getPoint()) && node.Mutnod(pointStart)) {
							movingNode = node;
							break;}}}
			}
			
			//evenimentul care se produce la eliberarea mousse-ului
			public void mouseReleased(MouseEvent e) {
				movingNode=null;
				if (!isDragging) {
					node_lipite(e);
				}
				else {
					boolean show1=false,show2=false,show3=false;
					int n=0,m=0;
					for (int i = 0; i < listaNoduri.size(); i++) {
						if (listaNoduri.get(i).inNod(pointStart)) {
							show1 = true;
							n = listaNoduri.get(i).getNumber();
						}
						if (listaNoduri.get(i).inNod(pointEnd)) {
							show2 = true;
							m = listaNoduri.get(i).getNumber();
						}
					}
					if(show1 && show2 && n!=m)
					{
						Arc arc = new Arc(pointStart, pointEnd,listaNoduri.get(n-1),listaNoduri.get(m-1));
						MatriceAdiacentaNeorientat[n-1][m-1]=1;
						MatriceAdiacentaNeorientat[m-1][n-1]=1;
						MatriceAdiacentaOrientat[n-1][m-1]=1;
						listaArce.add(arc);
						listaNoduri.get(n-1).addArd(arc);
						listaNoduri.get(m-1).addArd(arc);
					}
					repaint();
					//mess
					//if (movingNode != null) {
						//int deltaX = e.getX() - pointStart.x;
						//int deltaY = e.getY() - pointStart.y;
						//movingNode.moveBy(deltaX, deltaY);
						//pointStart = e.getPoint();
						//repaint();}
					//mess
				}
				pointStart = null;
				isDragging = false;
				repaint();
			}
			public void node_lipite(MouseEvent e)
			{
				int X_nou=e.getX();
				int Y_nou=e.getY();
				boolean show=true;
				for(int i=0;i<listaNoduri.size() && show;i++)
				{
					if(Math.abs(X_nou-listaNoduri.get(i).getCoordX())<=35 && Math.abs(Y_nou-listaNoduri.get(i).getCoordY())<=35) show=false;
				}
				if(show) addNode(X_nou, Y_nou);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			//evenimentul care se produce la drag&drop pe mousse
			public void mouseDragged(MouseEvent e) {
				if (movingNode != null) {
					int X = e.getX() - pointStart.x;
					int Y = e.getY() - pointStart.y;
					movingNode.move(X, Y);
					for(Arc arc:movingNode.getConnectedArcs()){
						arc.moveArc(X,Y,movingNode);
					}
					pointStart = e.getPoint();
					repaint();
				}
				pointEnd = e.getPoint();
				isDragging = true;
				repaint();
			}
		});
	}


	//metoda care se apeleaza la eliberarea mouse-ului
	private void addNode(int x, int y) {
		Node node = new Node(x, y, nodeNr);
		listaNoduri.add(node);
		nodeNr++;
		repaint();
	}


	
	//se executa atunci cand apelam repaint()
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
		g.drawString("This is my Graph!", 10, 20);
		//deseneaza arcele existente in lista
		/*for(int i=0;i<listaArce.size();i++)
		{
			listaArce.elementAt(i).drawArc(g);
		}*/
		if(tipGraf) writeMatrix(MatriceAdiacentaOrientat);
		else writeMatrix(MatriceAdiacentaNeorientat);
		for (Node nod : listaNoduri)
		{
			nod.drawNode(g, node_diam);
		}
		for (Arc a : listaArce)
		{
			if(!tipGraf) {a.drawArc(g);writeMatrix(MatriceAdiacentaNeorientat);}
			else {a.drawArrow(g);writeMatrix(MatriceAdiacentaOrientat);}
		}
		//deseneaza arcul curent; cel care e in curs de desenare
		if (pointStart != null)
		{
			g.setColor(Color.RED);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
		//deseneaza lista de noduri
		for(int i=0; i<listaNoduri.size(); i++)
		{
			listaNoduri.elementAt(i).drawNode(g, node_diam);
		}
	}

	public void writeMatrix(int[][] matrix) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("matrice.txt"));
			for(int i=1;i<nodeNr;i++)
				writer.write(i+" ");
			writer.write("\n");
			for (int i = 0; i < nodeNr-1; i++) {
				for (int j = 0; j < nodeNr-1; j++) {
					writer.write(matrix[i][j] + " ");
				}
				writer.write("\n");
			}

			writer.close();
		} catch (IOException e) {
			System.err.println("Eroare: " + e.getMessage());
		}
	}
}
