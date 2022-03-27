package be.uliege.boigelot.oop.sokoban.main;
import be.uliege.boigelot.oop.sokoban.gui.*;

public class Randomize
{
	private int nbBoxes;
	private Structure st;
	private Window w;
	private Player player;
	
	public Randomize(int nbBoxes, Structure st, Window w, Player player)
	{
		this.nbBoxes = nbBoxes;
		this.st = st;
		this.w = w;
		this.player = player;
	}
	
	public void createLevel()
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
			
            placeBox(4, 4);
			placeBox(4, 2);

            placeTarget(2, 4);
			placeTarget(2, 2);

            w.getDisplay().setCell(player.getX(), player.getY(), w.getPlayerImage());
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

	public int getNbBoxes()
	{
		return nbBoxes;
	}

	public void placeBox(int x, int y)
	{
		Box b = new Box(x, y);
		st.addBox(b);

		try
		{
			w.getDisplay().setCell(x, y, w.getCrateImage());
		}
		catch(SokobanError e)
		{
			System.out.println(e);
		}
		
	}

	public void placeTarget(int x, int y)
	{
        st.addTarget(x, y);
		
		try
		{
		w.getDisplay().setCell(x, y, w.getTargetImage());
		}
		catch(SokobanError e)
		{
			System.out.println(e);
		}
	}


	public void createOnlyWalls()
	{
		try
		{
			for(int i = 0; i < w.getWidth(); i++)
			{
				for(int j = 0; j < w.getHeight(); j++)
				{
					w.getDisplay().setCell(i, j, w.getWallImage());
					st.addWall(i, j);
				}
			}
		}
		catch(SokobanError e)
		{
			System.out.println(e);
		}
	}


}