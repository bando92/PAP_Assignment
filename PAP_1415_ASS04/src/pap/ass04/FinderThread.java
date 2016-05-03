package pap.ass04;

public class FinderThread extends Thread {

	//private long max;
	private Secret secret_num;
	private long from_n;
	private long to_n;
	public static boolean stop_threads = false;
	
	public FinderThread (String name, long from_n, long to_n, Secret secret_num)	{
		super(name);
		this.secret_num = secret_num;
		this.from_n = from_n;
		this.to_n = to_n;
	}
	
	public void run ()	{
		while (!Thread.currentThread().isInterrupted()) {
			long num=from_n;
			while (!secret_num.guess(num) || num>=to_n)	{
				num++;
			}
			long endTime = System.nanoTime();
			System.out.print("\nNumber: "+num+" thread n."+getName()+"\n Time: "+(endTime - TestCruncher.startTime)+"ns.") ;
			System.exit(0);
		}
	}
}
