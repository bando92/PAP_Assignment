package pap.ass05.captureTheFlag;

public class Player extends Thread{
    
    private Flag f;
    private Sync s;
    private int turn;
    private boolean nobodywin = true;
    
    public Player(Flag f, Sync s, int turn)
    {
        this.f = f;
        this.s = s;
        this.turn = turn;
    }
    
    public void run()
    {
        while(nobodywin)
        {
            s.waitForTurn(turn);
			if(s.Exist_Winner())
			{
			    System.out.println("Thread n. " + turn + ": SOB");
			    nobodywin = false;
			}
			else
			{
				if(f.capture())
				{
					s.winner = true;
					nobodywin = false;
					System.out.println("Thread n. " + turn + ": WON!");
				}
			}
			s.next();
        }  
        //System.out.println("bye from thread" + turn);
    }    
    
}
