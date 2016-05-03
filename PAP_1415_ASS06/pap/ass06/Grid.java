package pap.ass06;

import java.util.Random;

public class Grid {

	private boolean[][] grid;
	
	public Grid(int w, int h){
		this.grid = new boolean [w][h];
		initializeGrid(w,h);
	}
	
	public void setGrid(boolean[][] newGrid){
		grid = newGrid;
	}
	
	public boolean[][] getGrid(){
		return grid;
	}
	
	public void setSingleCell(int x, int y, boolean value){
		grid[x][y] = value;
	}
	
	public boolean getSingleCell(int x, int y){
		return grid[x][y];
	}

	private void initializeGrid(int w,int h){
	for(int i = 0; i < w; i++)
	for(int j = 0; j < h; j++)
		grid[i][j] = false;
	}
	
	public void setGridRandom(int w, int h, int nPoint){
		int count = 0;
		int randX;
		int randY;
		
		while (count<nPoint)	{
			randX= Integer.remainderUnsigned(new Random().nextInt(),w);
			randY= Integer.remainderUnsigned(new Random().nextInt(),h);
			if (!grid[randX][randY])	{
				grid[randX][randY] = true;
				count ++;
			}
		}
	}
}
