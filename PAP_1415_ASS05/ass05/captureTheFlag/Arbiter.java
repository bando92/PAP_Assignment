package pap.ass05.captureTheFlag;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arbiter extends Thread {
    
    private Random generatore;
    private Flag f;
    private Sync s;
    
    public Arbiter(Flag f, Sync s)
    {
        generatore = new Random(System.currentTimeMillis());
        this.f = f;
        this.s = s;
    }
    
    public void run()
    {
        while(!s.winner)
        {
            try 
            {
                sleep(100);
                f.setHigh();
                sleep(generatore.nextInt(10));
                f.setLow();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Arbiter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        System.out.println("bye from Arbiter!");
    }
}
