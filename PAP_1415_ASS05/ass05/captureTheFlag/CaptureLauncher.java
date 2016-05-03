package pap.ass05.captureTheFlag;

public class CaptureLauncher {

	    public static void main(String[] args) {
	        
	        int num_threads = 10;
	        Player[] p = new Player[num_threads];
	        Sync s = new Sync(num_threads);
	        Flag f = new Flag();
	        Arbiter a = new Arbiter(f,s);
	        
	        for(int i = 0; i < num_threads; i++)
	        {
	            p[i] = new Player(f, s, i + 1);
	            p[i].start();
	        }
	        a.start(); 
	    }
}
