package ass07;

import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Main {
		
	public static void main (String args[])	{
			
		boolean flag = false;
		Scanner scanIn = new Scanner(System.in);
		String s;
		int nactors = 0;
		
		while (flag == false) {
			System.out.println("Inserire N: ");
			s = scanIn.nextLine();
			try {
				nactors = Integer.parseInt(s);
				flag = true;
			} catch (NumberFormatException e) {
				System.out.println("Devi inserire un valore intero");
			}
			
		}
		scanIn.close();
		
		ActorSystem system = ActorSystem.create("Play");
		ActorRef a = system.actorOf(Props.create(OracleActor.class, nactors), "oracle");
		system.actorOf(Props.create(Terminator.class, a), "terminator");
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
			    log.info("-------End of the game. Shutting down the system.----------", ref.path());
			    getContext().system().shutdown();
			  } else {
			    unhandled(msg);
			  }
		   }
	  }
}
