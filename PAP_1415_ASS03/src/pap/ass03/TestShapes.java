package pap.ass03;

import java.util.ArrayList;
import java.util.List;

public class TestShapes{

	public static void main(String[] args) {
		
		//boundBox
		P2d bb1 = new P2d(5,50);
		P2d bb2 = new P2d(50,5);
		BBox bbox = new BBox(bb1,bb2);
		System.out.println("Bounding Box: " +bbox.getBottomRight().toString() + "," + bbox.getUpperLeft().toString());
		
		//V2d
		V2d vec = new V2d(10,10);
		
		//containPoint
		P2d containedPoint = new P2d(20,20);
		P2d containedPoint2 = new P2d(100,100);
		//P2d
		P2d a = new P2d(10,10);
		P2d b = new P2d(20,20);
		P2d c = new P2d(30,20);
		P2d d = new P2d(80,12);
		P2d e = new P2d(56,34);
		P2d f = new P2d(79,14);
		P2d g = new P2d(45,36);
		P2d center = new P2d(50,50);
		P2d center2 = new P2d(40,15);
		int radius = 15;
		
		//Line
		Line line = new Line(a,b);
		Line line2 = new Line(d,e);
		
		//Rect
		Rect rect = new Rect(a,c);
		Rect rect2 = new Rect(f,g);
		
		//Circle
		Circle circle = new Circle(center,radius);
		Circle circle2 = new Circle(center2,radius);
		
		//Combo
		List<Shape> list = new ArrayList<Shape>();
		list.add(circle);
		list.add(rect);
		list.add(line);
		
		List<Shape> list2 = new ArrayList<Shape>();
		list2.add(circle2);
		list2.add(rect2);
		list2.add(line2);
		Combo combo2 = new Combo(list2);
		list.add(combo2);
		
		List<Shape> testList = list;
				
		System.out.println("\nUtils.logAll \n");
		System.out.println("List:");
		Utils.logAll(list);
		
		//MoveShapes
		System.out.println("Utils.moveShapes with vec:"+vec.toString()+"\n");
		System.out.println("List Before:");
		Utils.logAll(list);
		Utils.moveShapes(list, vec);
		System.out.println("List After:");
		Utils.logAll(list);
		
		//faccio tornare la lista come era prima
		Utils.moveShapes(list, new V2d(-vec.getX(),-vec.getY()));
		
		//inBBox
		System.out.println("Utils.inBBox \n");
		System.out.println("List Before:");
		Utils.logAll(list);
		testList = Utils.inBBox(list, bbox);
		System.out.println("List After:");
		Utils.logAll(testList);
		
		//maxPerim
		System.out.println("\nUtils.maxPerim \n");
		double result = Utils.maxPerim(list);
		System.out.println("Max Perim: "+result);
		
		//shapeWithMaxPerim
		System.out.println("\nUtils.shapeWithMaxPerim \n");
		Shape shape = Utils.shapeWithMaxPerim(list);
		System.out.println("Shape with Max Perim: "+shape.toString());
		
		//sortShapesByX
		System.out.println("Utils.sortShapesByX \n");
		System.out.println("List Before:");
		Utils.logAll(list);
		testList = Utils.sortShapesByX(list);
		System.out.println("List After:");
		Utils.logAll(testList);
		
		//contains
		System.out.println("\nUtils.contains \n");
		System.out.println("List:");
		Utils.logAll(list);
		System.out.println("Point:" + containedPoint.toString());
		System.out.println("Result: " + Utils.contains(list, containedPoint));
		System.out.println("\nPoint2:" + containedPoint2.toString());
		System.out.println("Result: " + Utils.contains(list, containedPoint2));
		
		//getContaining
		System.out.println("\nUtils.getContaining \n");
		System.out.println("List:");
		Utils.logAll(list);
		System.out.println("Point:" + containedPoint.toString());
		testList = Utils.getContaining(list, containedPoint);
		System.out.println("\nContaining List:");
		Utils.logAll(testList);
		
	}
}
