package pap.ass05.captureTheFlag;

public class Sync {
    
    private int current_turn;
    private int num_thread;
    public boolean winner = false;
    
    public Sync(int num_thread)
    {
        this.num_thread = num_thread;
        current_turn = 1;
    }
 
    public synchronized void waitForTurn(int turn)
    {
    	while(current_turn != turn)
    		try {
				wait();
			} catch (InterruptedException ex){}
    }

    public synchronized void next()
    {
    	if(current_turn == num_thread)
    	{
    		current_turn = 1;
    	}
    	else
    	{
    		current_turn++;
    	}
    	notifyAll();
    }
    
    public synchronized boolean Exist_Winner()
    {
    	return winner;
    }
    
}