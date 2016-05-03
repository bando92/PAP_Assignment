package ass07;

import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class OracleActor extends UntypedActor {
	
	private boolean flagStop;
	private static final long secretNumber = generateRand(NumberBound.bound);
	private ActorRef [] a;
	
	private int nactors;
	private int countTerminated;
	private int countRound;
	
	private long countTurn;
	
	public OracleActor(int nactors) {
		countTurn = 0;
		flagStop = false;
		countTerminated = 0;
		countRound= 0;
		this.nactors = nactors;
		System.out.println("Numero DA INDOVINARE------------"+secretNumber);
				
		a = new ActorRef [nactors];
		for (int i = 0; i < nactors ; i ++)	{
			a [i] = getContext().actorOf(Props.create(PlayerActor.class), "player_"+i);
		}
		a [0].tell(new TurnMsg(a,0,getSelf(),0), getSelf()); 
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		boolean isHigher;
				
		if (msg instanceof PlayerMsg)	{
			
			PlayerMsg playermsg = (PlayerMsg) msg;
			long playerguess = playermsg.getNumber();
			
			if (playerguess == secretNumber)	{
				flagStop = true;
				isHigher = false;

				for (int i = 0; i < nactors ; i ++)	{
					if (a[i] != getSender())
						a[i].tell(new OracleMsg(isHigher, flagStop, playerguess, false), getSelf());
					else
						getSender().tell(new OracleMsg(isHigher, flagStop, playerguess, true), getSelf());
				}
			}
			else
			{					
				isHigher = secretNumber > playerguess ;
				getSender().tell(new OracleMsg(isHigher, flagStop, playerguess), getSelf());
			}
		}
		else	{
			countTerminated ++;
			if (countTerminated >= nactors)	{
				getContext().stop(getSelf());
			}
		}
	}
	
	private static long generateRand (long upper)	{
		return Long.remainderUnsigned(new Random().nextLong(), upper);
	}

}
