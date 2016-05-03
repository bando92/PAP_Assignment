package pap.ass03.viewer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import pap.ass03.Circle;
import pap.ass03.Combo;
import pap.ass03.Line;
import pap.ass03.P2d;
import pap.ass03.Rect;
import pap.ass03.Shape;

public class TestShapeViewer {
	
	public TestShapeViewer(){
		// Lista iniziale di Shape
				/*List<Shape> shapeList = Arrays.asList(new Circle(new P2d(160, 160), 150),
						new Line(new P2d(200, 95), new P2d(50, 140)),
						new Rect(new P2d(1, 1), new P2d(60, 80)),
						new Rect(new P2d(10, 10), new P2d(80, 150)));*/
				
				ArrayList<Shape> shapeList2 = new ArrayList<Shape>();
				P2d a = new P2d(10,100);
				P2d b = new P2d(200,20);
				P2d r = new P2d(300,46);
				P2d d = new P2d(216,78);
				P2d t = new P2d(350,78);
				P2d h = new P2d(375,215);
				P2d center = new P2d(200,200);
				int radius = 120;

				Line line1 = new Line(a,b);
				Line line2 = new Line(r,d);
				Line line3 = new Line(b,r);
				Line line4 = new Line(d,a);
				Line line5 = new Line(h,a);
				Rect rect1 = new Rect(t,h);
				System.out.println("UL:"+rect1.getBBox().getUpperLeft().toString()+" BR:"+rect1.getBBox().getBottomRight().toString());
				Circle circle1 = new Circle(center,radius);
				Circle circle2 = new Circle(t,60);
				
				List<Shape> list = new ArrayList<Shape>();
				list.add(circle2);
				list.add(line5);
				Combo combo = new Combo(list);
				
				shapeList2.add(line1);
				shapeList2.add(line2);
				shapeList2.add(line3);
				shapeList2.add(line4);
				shapeList2.add(rect1);
				shapeList2.add(circle1);
				shapeList2.add(combo);
				
				JFrame f = new JFrame("Test");
				Container c = f.getContentPane();
				Viewer v = new Viewer(shapeList2);
				JButton button = new JButton("Update");
				
				f.getContentPane().add(BorderLayout.SOUTH, button);
				
				// Aggiungo il Listener al bottone; una volta premuto richiama la update su una nuova lista
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						List<Shape> l = new ArrayList<Shape>();
						Line lineTest = new Line(b,h);
						Rect rectTest = new Rect(a,d);
						Circle circleTest = new Circle(h,120);
						l.add(lineTest);
						l.add(rectTest);
						l.add(circleTest);
						v.update(l);
					}
				});
				button.setSize(60, 80);
				button.setBounds(500, 400, 580, 460);

				c.add(v);
				f.setSize(600, 500);
				f.setLocation(200, 100);
				f.setResizable(true);
				f.setVisible(true);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new TestShapeViewer();
	}

}
