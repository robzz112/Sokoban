package be.uliege.boigelot.oop.sokoban.main;
import be.uliege.boigelot.oop.sokoban.gui.*;

public class Randomize
{
	private int nbBoxes;
	
	public Randomize()
	{
		this.nbBoxes = 0;
	}
	
	public void createLevel(Structure st, Window w)
	{
		try
		{
			for(int i = 0; i < w.getWidth(); i++)
			{
				
				for(int j = 0; j < w.getHeight(); j++)
				{
					if(i == 0 || j == 0 || i == w.getWidth() - 1 || j == w.getHeight() - 1)
					{
						w.getDisplay().setCell(i, j, w.getWallImage());
						st.addWall(i, j);
					}
					else
						w.getDisplay().setCell(i, j, w.getEmptyImage());
				}
			}
			
			w.getDisplay().setCell(4, 4, w.getCrateImage());
            w.getDisplay().setCell(4, 2, w.getCrateImage());
			
            Box b = new Box(4, 4);
            Box otherb = new Box(4, 2);

            st.addBox(otherb);
            st.addBox(b);

            w.getDisplay().setCell(2, 2, w.getTargetImage());
            st.addTarget(2, 2);

            w.getDisplay().setCell(5, 5, w.getPlayerImage());
            w.getDisplay().show();
		}
		catch(SokobanError e)
		{
			System.out.println(e);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}