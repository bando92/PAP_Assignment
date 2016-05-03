package pap.ass06;

public class Test {
	
	public static void main (String args[])	{
		
		int w = 700;
		int h = 700;
		int nPoint = 30000;
		Grid grid = new Grid(w,h);
		grid.setGridRandom(w, h, nPoint);
		
		GameOfLifeSet set = new GameOfLifeSet(w, h);
		GameOfLifeView view = new GameOfLifeView(w, h);
		Controller controller = new Controller(set, view, grid);
		view.addListener(controller);
		view.setVisible(true);
		
	}

}
