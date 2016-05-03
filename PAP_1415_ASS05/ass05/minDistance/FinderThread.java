package pap.ass05.minDistance;

import java.util.ArrayList;
import java.util.List;

public class FinderThread extends Thread{
	//private long max;
	private List<P3d> pointArray = new ArrayList<P3d>();
	public static boolean stop_threads = false;
	private long minDistance = Long.MAX_VALUE;
	private P3d pointC;
	private MinCache cache;
	private String name;
	private P3d minPoint;
	private long currentMin;
	
	public FinderThread (String name, P3d pointC, List<P3d> pointArray, MinCache cache ){
		super(name);
		this.name = name;
		this.pointC = pointC;
		this.pointArray = pointArray;
		this.cache = cache;
	}
		
	public void run ()	{
		for(P3d point : pointArray){
			currentMin = Math.min(minDistance, point.distanceFrom(pointC));
			if(minDistance > currentMin)
			{	
				minDistance = currentMin;
				minPoint = point;
			}
			/*System.out.println("Proc "
								+ this.name
								+ " Punto "
								+ point.toString()
								+ " distanza "
								+ point.distanceFrom(pointC));*/
		}
		if(cache.getMinDistance()>minDistance)
		{
			cache.setMinDistance(minDistance);
			cache.setMinPoint(minPoint);
		}
		
		System.out.println("\nRisultato processore "
								+ this.name
								+ " = "
								+ minPoint.toString()
								+ " with distance = "
								+ minDistance);
								
		}
}
