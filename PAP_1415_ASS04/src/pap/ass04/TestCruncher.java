package pap.ass04;

import java.util.ArrayList;

public class TestCruncher {
	
	public static long startTime;
	
	public static void main(String[] args) {
		System.out.print("\nNumber of cores: " + Runtime.getRuntime().availableProcessors());
		startTime = System.nanoTime();
		long max = 999999999;
		Secret secret_num = new Secret(max);
		int proc_num = Runtime.getRuntime().availableProcessors();
		int bound = (int) max/proc_num;
		ArrayList<FinderThread> thread_list = new ArrayList<FinderThread>();
		//creo un thread per ogni processore
		for (int i = 0; i < proc_num; i++)	{
			if (i != (proc_num-1))
				thread_list.add(i, new FinderThread(""+(i+1), i*bound, (i+1)*bound, secret_num));
			//qualora fossi nell'ultimo range di numeri arrivo fino a max
			else
				thread_list.add(i, new FinderThread(""+(i+1), i*bound, max, secret_num));
			((Thread) thread_list.toArray()[i]).start();
		}

	}
}
