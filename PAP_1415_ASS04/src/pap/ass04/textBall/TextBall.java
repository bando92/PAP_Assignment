package pap.ass04.textBall;

import java.util.ArrayList;

public class TextBall {

	public static void main(String[] args) {
		int n_star = 0;
		try	{
			n_star = Integer.parseInt(args[0]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		TextLib lib = TextLibFactory.getInstance();

		lib.cls();
		
		//dimensione finestra 75x50
		for (int i = 0; i <= 75; i++)	{
			//row
			lib.writeAt(i, 0,"x",7);
			lib.writeAt(i, 50,"x",7);
			for (int j = 0; j <= 50; j++)	{
				//col
				lib.writeAt(0, j,"x",7);
				lib.writeAt(75, j,"x",7);
			}
		}
		
		//sposto in basso la linea di comando
		lib.writeAt(0, 51,"");
		
		ArrayList<StarThread> thread_list = new ArrayList<StarThread>();

		for (int i = 0; i < n_star; i++)	{
			thread_list.add(i, new StarThread("n."+i, i));
			((Thread) thread_list.toArray()[i]).start();
		}		
	}

}
