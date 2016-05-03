package ass08.gameoflife;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class MasterActor extends UntypedActor {

	private int w;
	private int h;
	private Flag stopFlag;
	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Grid oldGrid;
	private Grid newGrid;
	private Counter counter;
	private ActorRef [] a;
	private int x;
	private int y;
	private Grid isalive;
	private int countReceived;
	private int numberOfWorkers;
	private int pieceWidth;
	
	public MasterActor (GameOfLifeSet set, GameOfLifeView view, Flag stopFlag, Grid grid)	{
		this.w = set.getSizeX();
		this.h = set.getSizeY();
		this.stopFlag = stopFlag;
		this.set = set;
		this.view = view;
		this.oldGrid = grid;
		this.counter = new Counter(0);
	}
	
	@Override
	public void preStart() throws Exception {
		
		countReceived = 0;
		numberOfWorkers = 10;
		pieceWidth = (int)(w/numberOfWorkers);
		a = new ActorRef [numberOfWorkers];

		for (int x = 0; x < w; x++){
			for (int y=0; y < h; y++)	{
				set.computePoint(x, y, oldGrid.getSingleCell(x, y));
				if(oldGrid.getSingleCell(x, y))
					counter.inc();
			}
    	}
		
		newGrid = new Grid(w,h);
		view.setUpdated(set);
		view.changeState("Start with " + counter.getValue() + " dots");
		
		
		for(int x = 0; x < numberOfWorkers; x++){
			a[x] =  getContext().actorOf(Props.create(WorkerActor.class), "player_"+x);
			if(x == 0)
			{
				//log("" + 0 + pieceWidth);
				a[x].tell(new MasterMsg(0, pieceWidth, oldGrid, set), getSelf()); 
			}
			else if(x != (numberOfWorkers-1))
			{
				//log("" + x*numberOfWorkers + ((x+1)*pieceWidth));
				a[x].tell(new MasterMsg(x*numberOfWorkers, ((x+1)*pieceWidth), oldGrid, set), getSelf()); 
			}
			else
			{
				//log("" + x*numberOfWorkers + w);
				a[x].tell(new MasterMsg(x*numberOfWorkers, w, oldGrid, set), getSelf()); 
			}
		}
		
		
		/*
		for(int k = 0; k < numberOfWorkers; k++){
			a[k] =  getContext().actorOf(Props.create(WorkerActor.class), "player_"+k);
		}
		
		for(int j = 1; j <= numberOfWorkers; j++){
			
			if(j != numberOfWorkers)
			{	
				//log(""+ (j-1)*pieceWidth + " " + j*pieceWidth);
				a[j-1].tell(new MasterMsg((j-1)*pieceWidth, (j*pieceWidth), oldGrid, set), getSelf()); 
			}
			else
			{
				//log(""+ (j-1)*pieceWidth + " " + (w-1));
				a[j-1].tell(new MasterMsg((j-1)*pieceWidth, w, oldGrid, set), getSelf()); 
			}
		}
		*/	
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		
		if (msg instanceof WorkerMsg)
		{
			WorkerMsg workermsg = (WorkerMsg) msg;
			x = workermsg .getX();
			y = workermsg .getY(); 
			isalive = workermsg.getAliveGrid();
			countReceived++;
			
			for(int i = x; i < y; i++)
			{
				for(int j = 0; j < h; j++){
					newGrid.setSingleCell(i, j, isalive.getSingleCell(i, j));
					if(isalive.getSingleCell(i, j) == true && oldGrid.getSingleCell(i, j) == false) {
						counter.inc();
					}
					else {
						if(isalive.getSingleCell(i, j) == false && oldGrid.getSingleCell(i, j) == true)
							counter.dec();
					}
				}
			}	
					
			if(countReceived >= numberOfWorkers)
			{		
				countReceived = 0;
				view.setUpdated(set);
				    	
				if (!stopFlag.isSet())
				{
					view.changeState("Number of alive dots: " + counter.getValue());
				} 
				else 
				{
					view.changeState("interrupted");
					getContext().stop(getSelf());
					return;	
				}
				    	
				oldGrid.setGrid(newGrid.getGrid());
				newGrid = new Grid(w,h);    	
				  
				for(int x = 0; x < numberOfWorkers; x++){
		    		if(x == 0)
		    			a[x].tell(new MasterMsg(0, pieceWidth, oldGrid, set), getSelf()); 
		    		else if(x != (numberOfWorkers-1))
						a[x].tell(new MasterMsg(x*numberOfWorkers, ((x+1)*pieceWidth), oldGrid, set), getSelf()); 
					else
						a[x].tell(new MasterMsg(x*numberOfWorkers, w, oldGrid, set), getSelf()); 
				}
				/*
				for(int k = 1; k <= numberOfWorkers; k++)
				{		
					if(k != numberOfWorkers)
					{
						a[k-1].tell(new MasterMsg((k-1)*pieceWidth, (k*pieceWidth), oldGrid, set), getSelf());  
					}
					else
					{
						a[k-1].tell(new MasterMsg((k-1)*pieceWidth, w, oldGrid, set), getSelf()); 
					}
				}
				*/		 						
			}	
		}
	}
	
	private void log(String msg){
		synchronized(System.out){
			System.out.println(msg);
		}
	}

}
