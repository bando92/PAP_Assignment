package ass07;

import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class PlayerActor extends UntypedActor {

	private long upper_range;
	private long lower_range;
	private long guess;
	private int playerId;
	private int turn;
	
	int nextindex;
	ActorRef [] players;
	ActorRef oracleRef;
	
	@Override
	public void preStart() {
		upper_range = NumberBound.bound;
		lower_range = 0;
		turn = 0;
	}
	
	@Override
	public void onReceive(Object msg) {

		if (msg instanceof TurnMsg)
		{
			TurnMsg turnmsg = (TurnMsg) msg;
			players = turnmsg.getPlayers();
			nextindex = (turnmsg.getIndex() + 1) % players.length;
			oracleRef = turnmsg.getOracle();
			playerId = turnmsg.getIndex();
			
			if (playerId == 0)	{
				turn++;
				System.out.println("Turn n."+turn);
			}
			guess = generateGuess(lower_range, upper_range);
			oracleRef.tell(new PlayerMsg(guess), getSelf());
		}
		else if (msg instanceof OracleMsg)
		{
			OracleMsg oraclemsg = (OracleMsg) msg;
			
				if (!oraclemsg.isTerminated())	{
					if (oraclemsg.isHigher())
						lower_range = oraclemsg.getPreviousRandomNumber(); 
					else
						upper_range = oraclemsg.getPreviousRandomNumber();	
					
					players[nextindex].tell(new TurnMsg(players, nextindex, oracleRef, playerId + 1), getSelf());
				}
				else	{
					if (oraclemsg.isTheWinner())
						System.out.println("Player"+playerId+": WON! The number was "+guess);
					else
						System.out.println("Player"+playerId+": SOB :(");

					getSender().tell(new TerminateMsg(), getSelf());				
					getContext().stop(getSelf());
				}			
			}
	}

	
	private long generateGuess (long lower, long upper)	{
		return generateRand(lower, upper);
	}

	private long generateRand (long lower, long upper)	{
		Random r = new Random();
		return lower + (long)(r.nextDouble()*(upper - lower));
	}
}
