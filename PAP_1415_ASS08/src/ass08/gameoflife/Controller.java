package ass08.gameoflife;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Controller implements InputListener {

	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Flag stopFlag;
	private Grid grid;
	
	public Controller(GameOfLifeSet set, GameOfLifeView view, Grid grid){
		this.set = set;
		this.view = view;
		this.grid = grid;
	}
	
	public void started(){		
		stopFlag = new Flag();	
		
		ActorSystem system = ActorSystem.create("Play");
		ActorRef a = system.actorOf(Props.create(MasterActor.class, set, view, stopFlag, grid), "oracle");
		system.actorOf(Props.create(Terminator.class, a), "terminator");
	}

	public void stopped() {
		stopFlag.set();
	}
	
	
	
	public static class Terminator extends UntypedActor {
		
		private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
		private final ActorRef ref;
		
		public Terminator(ActorRef ref) {
		  this.ref = ref;
		  getContext().watch(ref);
		}
		
		@Override
		public void onReceive(Object msg) {
		  if (msg instanceof Terminated) {
		    //log.info("-------End of the game. Shutting down the system.----------", ref.path());
		    getContext().system().shutdown();
		  } else {
		    unhandled(msg);
		  }
	   }
  }

}