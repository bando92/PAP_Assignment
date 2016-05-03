package ass08.gameoflife;

public class GameOfLifeSet {

	private int w,h;
	private int image[]; 
	
	public GameOfLifeSet(int w, int h){
		this.w = w;
		this.h = h;
        image = new int[w*h];
	}
	
	public int getSizeX(){
		return w;
	}
	
	public int getSizeY(){
		return h;
	}
	
	public int[] getImage(){
		return image;
	}



	public void computePoint(int x, int y, boolean alive) {
		if (alive)	{
			image[y*w+x]  = 0;
		}
		else
		{
			image[y*w+x]  = 16777215;
		}
	}
}
