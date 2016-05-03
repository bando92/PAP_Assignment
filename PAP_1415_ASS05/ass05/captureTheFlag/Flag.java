package pap.ass05.captureTheFlag;

public class Flag {
    
    private boolean is_high;
    
    public Flag()
    {
        is_high = false;
    }
    
    public synchronized void setHigh()
    {
        is_high = true;
    }

    public synchronized void setLow()
    {
        is_high = false;
    }
  
    public synchronized boolean capture()
    {
        return is_high;
    }
    
}
