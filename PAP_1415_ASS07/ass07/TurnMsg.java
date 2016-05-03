package ass07;

import akka.actor.ActorRef;

public class TurnMsg {
	
	private  int i;
	private ActorRef[] pvect;
	private ActorRef oracle;
	int playerId;
	
	public TurnMsg (ActorRef [] p, int i, ActorRef oracle, int playerId)	{
		this.pvect = p;
		this.i = i;
		this.oracle = oracle;
	}
	
	public ActorRef [] getPlayers ()	{
		return pvect;
	}

	public int getIndex ()	{
		return i;
	}
	
	public ActorRef getOracle ()	{
		return oracle;
	}
	
	public int getPlayerId ()	{
		return playerId;
	}

}
