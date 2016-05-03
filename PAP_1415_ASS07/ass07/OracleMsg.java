package ass07;

public class OracleMsg {
	
	private boolean justCreated;
	
	private boolean isHigher;
	private boolean endFlag;
	private long prevRand;
	private int id;

	private boolean hasWon;

	public OracleMsg (boolean isHigher, boolean endFlag, long prevRand)	{
		this.isHigher = isHigher;
		this.prevRand =  prevRand;
		this.endFlag = endFlag;
	}
	
	public OracleMsg (boolean isHigher, boolean endFlag, long prevRand, boolean hasWon)	{
		this.isHigher = isHigher;
		this.prevRand =  prevRand;
		this.endFlag = endFlag;
		this.hasWon = hasWon;
	}

	public OracleMsg(int id) {
		justCreated = true;
		this.id = id;
	}

	public boolean isJustCreated ()	{
		return justCreated;
	}
	public void setJustCreated (boolean justCreated)	{
		this.justCreated = justCreated;
	}

	public boolean isTerminated ()	{
		return endFlag;
	}
	
	public boolean isHigher ()	{
		return isHigher;
	}
	
	public long getPreviousRandomNumber()	{
		return prevRand;
	}
	
	public boolean isTheWinner ()	{
		return hasWon;
	}
	
	public int getId ()	{
		return id;
	}

}
