package pap.ass05.captureTheFlag;

public class WinFlag {
	
	private boolean somebodyWin;
	
	public WinFlag(){
		this.somebodyWin = false;
	}
	
	public void setMinDistance(long distance){
		synchronized (this) {
			this.somebodyWin = true;
		}
	}
	
	public boolean getMinDistance(){
		synchronized (this) {
			return somebodyWin;
		}
	}
}
