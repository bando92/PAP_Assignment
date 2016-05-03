package pap.ass03.viewer;
import java.awt.Canvas;
import java.awt.Color;

import static java.util.stream.Collectors.toList;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import pap.ass03.Circle;
import pap.ass03.Combo;
import pap.ass03.Line;
import pap.ass03.P2d;
import pap.ass03.Rect;
import pap.ass03.Shape;

public class Viewer extends Canvas implements ShapeViewer {
	private List<Shape> shapes;
	private boolean picked; // Serve ad indicare se il metodo paint viene invocato per la prima volta o se l'ha invocato il Listener
	private Graphics g;
	private List<Boolean> bool_list;
	
	public Viewer(List<Shape> shapes){
		this.shapes = shapes;
		this.picked = false;
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
			// Ogni volta che viene premuto il bottone viene invocato questo metodo
			public void mouseClicked(MouseEvent e) {
				System.out.println("X: "+ e.getX() + " Y: "+e.getY());
				picked = true;
				bool_list = getShapes().stream()
					.map(s -> s.contains(new P2d(e.getX(), e.getY())))
					.collect(toList()); // Ritorna una lista di booleani che indicano se il punto selezionato è contenuto nella rispettiva figura indicata dall'indice dell'elemento
				update(getShapes());
			}
			
		});
	}
	
	public void paint(Graphics g) {
		this.g = g;
		if(!picked)
			for(Shape s : shapes)
				draw_Shape(s, false);
		else{
			picked = false;
			for(int i = 0; i < shapes.size(); i++)
				draw_Shape(shapes.get(i), bool_list.get(i));
		}
	}

	public void update(List<Shape> shapes) {
		this.shapes = shapes;
		this.repaint();
	}
	
	private void draw_Shape(Shape s, boolean picked){
		
		if(s instanceof Line)
		{
			if(picked)
				g.setColor(Color.RED);
			else
				g.setColor(Color.BLUE);
			g.drawLine(((Line)s).getFirstPoint().getX(), ((Line)s).getFirstPoint().getY(), ((Line)s).getSecondPoint().getX(), ((Line)s).getSecondPoint().getY());
		}
		if(s instanceof Rect)
		{
			if(picked)
				g.setColor(Color.RED);
			else
				g.setColor(Color.GREEN);
			g.fillRect(s.getBBox().getUpperLeft().getX(), s.getBBox().getUpperLeft().getY(), ((Rect)s).getWidth(),((Rect)s).getHeight());
			g.setColor(Color.BLACK);
			g.drawRect(s.getBBox().getUpperLeft().getX(), s.getBBox().getUpperLeft().getY(), ((Rect)s).getWidth(), ((Rect)s).getHeight());
		}
		if(s instanceof Circle){
			if(picked)
				g.setColor(Color.RED);
			else
				g.setColor(Color.YELLOW);
			int radius = (int) (s.getPerim() / (2*Math.PI));
			P2d center = new P2d(s.getBBox().getUpperLeft().getX()+radius, s.getBBox().getUpperLeft().getY()+radius);
			g.fillOval(center.getX()-radius, center.getY()-radius, radius*2, radius*2);
			g.setColor(Color.BLACK);
			g.drawOval(center.getX()-radius, center.getY()-radius, radius*2, radius*2);
		}
		if(s instanceof Combo){
			for(Shape sh : ((Combo) s).getList())
				draw_Shape(sh, picked);
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	
	private List<Shape> getShapes() {
		return shapes;
	}
	
	

}