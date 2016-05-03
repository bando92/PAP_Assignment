package pap.ass04.textBall;

import java.util.Random;


public class StarThread extends Thread {
	
	private int color;
	private int x;
	private int y;
	
	public StarThread (String name, int color)	{
		super(name);
		this.color = color%5+1;
	}
	
	public void run ()	{
		TextLib lib = TextLibFactory.getInstance();
		try {
			generateRandomPoint(lib);
			
			int rand_x = 0;
			int rand_y = 0;
			int rand_sleep_time = new Random().nextInt(300) + 10;
			
			do	{
				rand_x = new Random().nextInt(1 - (-1) + 1) - 1;
				rand_y = new Random().nextInt(1 - (-1) + 1) - 1;
			}	while (rand_y==0 && rand_x==0);

			boolean first_star=true;
			while (true)	{
				movePoint(lib,rand_x,rand_y,first_star);
				first_star=false;
				Thread.sleep(rand_sleep_time);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void generateRandomPoint (TextLib lib)	{
		x = new Random().nextInt((BoundUtils.x_max_bound - BoundUtils.x_min_bound) - 1) + BoundUtils.x_min_bound + 1;
		y = new Random().nextInt((BoundUtils.y_max_bound - BoundUtils.y_min_bound) - 1) + BoundUtils.y_min_bound + 1;
		//lib.writeAt(x, y,"*",color);
	}
	
	private void movePoint (TextLib lib, int rand_x, int rand_y, boolean first_point)	{
		if(!first_point)
			lib.writeAt(x, y,"*",0);
		x = x + rand_x;
		y = y + rand_y;
		
		//Controllo se la star esce e da dove
		if (x <= BoundUtils.x_min_bound)
			x = BoundUtils.x_max_bound + rand_x;
		if (y <= BoundUtils.y_min_bound)
			y = BoundUtils.y_max_bound + rand_y;
		if (x >= BoundUtils.x_max_bound)
			x = BoundUtils.x_min_bound + rand_x;
		if (y >= BoundUtils.y_max_bound)
			y = BoundUtils.y_min_bound + rand_y;
		lib.writeAt (x, y,"*", color);
	}
}
