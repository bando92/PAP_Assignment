package pap.ass05.minDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.Random;
import java.util.stream.Collectors;

public class MinDistance {

	static int listDim = 100000;
	static long maxValue = 100;
	static long startTime;
	static long stopTime;
	static long parallelTime;
	static long streamTime;
	public static void main(String[] args) {
		
		//lista di punti casuali da esaminare
		ArrayList<P3d> p3dList = new ArrayList<P3d>();
		for(int i=0;i<listDim;i++)
		{
			p3dList.add(new P3d(
					Long.remainderUnsigned(new Random().nextLong(),maxValue),
					Long.remainderUnsigned(new Random().nextLong(),maxValue),
					Long.remainderUnsigned(new Random().nextLong(),maxValue)
					));
		}
		
		//punto c
		P3d pointC = new P3d(100,100,100);
		
		//----------------parallel
		//cache condivisa
		MinCache cache = new MinCache(Long.MAX_VALUE,pointC);
		System.out.print("\nNumber of cores: " + Runtime.getRuntime().availableProcessors());
		int proc_num = Runtime.getRuntime().availableProcessors();
		int bound = (int) listDim /proc_num;
		ArrayList<FinderThread> thread_list = new ArrayList<FinderThread>();
		startTime = System.nanoTime();
		for (int i = 0; i < proc_num; i++)	{
			if (i != (proc_num-1))
				thread_list.add(i,
						new FinderThread(
								""+(i+1),
								pointC,
								p3dList.subList(i*bound, (i+1)*bound),
								cache)
						);
			else
				thread_list.add(i,
						new FinderThread(
								""+(i+1),
								pointC,
								p3dList.subList(i*bound, listDim),
								cache)
						);
			((Thread) thread_list.toArray()[i]).start();
		}
		
		for(Thread t: thread_list)
		{
			try {
				t.join();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		stopTime = System.nanoTime();
		parallelTime = stopTime - startTime;
		System.out.println("Parallel Result: Point "
							+ cache.getMinPoint()
							+ " distance = "
							+ cache.getMinDistance()
							+ " in "
							+ parallelTime
							+ " ns");
		//----------------parallel

		//----------------stream
		startTime = System.nanoTime();
		OptionalLong optResult = p3dList
				.stream()
				.mapToLong(s -> s.distanceFrom(pointC))
				.min();
		long result = optResult.orElse(0);
		List<P3d> pointList = p3dList
				.stream()
				.filter(s -> s.distanceFrom(pointC) == result)
				.collect(Collectors.toList());
		stopTime = System.nanoTime();
		streamTime = stopTime - startTime;
		System.out.println("Stream Result: Point List = ");
		for(P3d point : pointList)
		{
			System.out.println(point.toString());
		}
		System.out.println(" distance = "
							+ result
							+ " in "
							+ streamTime
							+ " ns");
		//----------------stream
		
		if(parallelTime>streamTime)
			System.out.println("Stream Wins!");
		else
			if(parallelTime<streamTime)
				System.out.println("Parallel Wins!");
			else
				System.out.println("It's a Tie!");
	}

}
