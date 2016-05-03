package pap.ass04;

import java.util.Random;

/**
 * Class representing a Secret to be guessed.
 * 
 * @author aricci
 *
 */
public class Secret {

	private static final int seed = 11;
	private long secret;
	private boolean no_print = false;
	
	/**
	 * Create a secret between 0 and max 
	 * 
	 * @param max
	 */
	public Secret(long max){
		this.secret = Long.remainderUnsigned(new Random(seed).nextLong(),max);
		if(!no_print) 
		{
			//System.out.println("THE SECRET IS: "+secret);
			no_print = true;
		}
	}

	/**
	 * Guess the number
	 * 
	 * @param number guess
	 * @return true if the number is right
	 */
	public boolean guess(long number){
		return secret == number;
	}
	
}