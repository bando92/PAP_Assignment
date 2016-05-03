package pap.ass03;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Utils {

	public static void moveShapes(List<Shape> shapes,V2d vec ){
		shapes
		.stream()
		.forEach(s-> {s.move(vec);});
	}
	
	public static List<Shape> inBBox(List<Shape> shapes,BBox bbox ){
		List<Shape> list = shapes
		.stream()
		.filter(s -> s.isInside(bbox))
		.collect(Collectors.toList());
		return list;
	}
	
	public static double maxPerim(List<Shape> shapes){
		OptionalDouble optResult = shapes
				.stream()
				.mapToDouble(s -> s.getPerim())
				.max();
		double result = optResult.orElse(0);
		return result;
	}
	
	public static Shape shapeWithMaxPerim(List<Shape> shapes){
		Optional<Shape> maxShape = shapes
				.stream()
				.reduce((s1,s2) -> s1.getPerim() > s2.getPerim() ? s1 : s2);
		return maxShape.orElse(null);
	}
	
	public static List<Shape> sortShapesByX(List<Shape> shapes){
		List<Shape> orderedList = shapes
				.stream()
				.sorted((s1,s2)-> Integer.compare(s1.getBBox().getUpperLeft().getX(), s2.getBBox().getUpperLeft().getX()))
				.collect(Collectors.toList());
		return orderedList;
	}
	
	public static boolean contains(List<Shape> shapes, P2d p0){
		boolean result = shapes
				.stream()
				.anyMatch(s -> s.contains(p0));
		return result;
	}
	
	public static List<Shape> getContaining(List<Shape> shapes, P2d p0){
		List<Shape> resultList = shapes
				.stream()
				.filter(s -> s.contains(p0))
				.collect(Collectors.toList());
		return resultList;
	}
	
	public static void logAll(List<Shape> shapes){
		shapes
		.stream()
		.forEach(s-> {System.out.println(s.toString());});
	}

}
