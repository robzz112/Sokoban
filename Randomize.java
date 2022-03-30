package be.uliege.boigelot.oop.sokoban.main;
import be.uliege.boigelot.oop.sokoban.gui.*;

import java.util.*;

public class Randomize
{
	private int nbBoxes;
	private Structure st;
	private Window w;
	private Player player;
	private Random r;
	private Map<String, Box> initialBoxesPosition;
	private Map<String, Box> BoxesPosition;
	private Map<String, Boolean> walkedArea;//Contains where, at the end, walls can't be placed
	private Box listBoxes[ ]= new Box[nbBoxes];//Array containing boxes

	public Randomize(int nbBoxes, Structure st, Window w, Player player)
	{
		this.nbBoxes = nbBoxes;
		this.st = st;
		this.w = w;
		this.player = player;
		this.r = new Random();
		this.initialBoxesPosition = new HashMap<String, Box>();
		this.BoxesPosition = new HashMap<String, Box>();
		this.walkedArea = new HashMap<String, Boolean>();
		this.listBoxes= new Box[nbBoxes];
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
			/*
            placeBox(4, 4);
			placeBox(4, 2);
			*/
			placeTarget(2, 4);
			placeTarget(2, 2);
			placeRandomBoxes();
            

            placeRandomPlayer();
			
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

		String s = x + "," + y;
		initialBoxesPosition.put(s, b);
		BoxesPosition.put(s, b);
		placeWalkedArea(x, y);

		////////////////////////////////////////////////////////////////////////////////////
		//NE PAS OUBLIER D'ENLEVER LE SETCELL(try catch) POUR NE PAS QUE CA AFFICHE LES BOITES QUAND CA GENERERA LE NIVEAU ALEATOIREMENT
		try
		{
			w.getDisplay().setCell(x, y, w.getCrateImage());
		}
		catch(SokobanError e)
		{
			System.out.println(e);
		}
		//////////////////////////////////////////////////////////////////////////////////////
	}

	public void placeWalkedArea( int x, int y)
	{
		String s = x + "," + y;
		walkedArea.put(s, true);
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

	public void placeRandomBoxes()
	{
		int low = 1;
		int highX = w.getWidth();
		int highY = w.getHeight(); 

		for(int i = 0; i < nbBoxes; i++)
		{
			int x = r.nextInt(highX - low) + low;
			int y = r.nextInt(highY - low) + low;

			String s = x + "," + y;

			if(initialBoxesPosition.containsKey(s) == false && st.wallCheck(s) == false && st.targetCheck(s) == false)
			{
				placeBox(x, y);
				continue;
			}

			while(initialBoxesPosition.containsKey(s) == true || st.wallCheck(s) == true || st.targetCheck(s) == true)
			{
				x = r.nextInt(highX - low) + low;
				y = r.nextInt(highY - low) + low;
				
				s = x + "," + y;
			}

			placeBox(x, y);
		}
	}

	public void placeRandomPlayer()
	{
		int low = 1;
		int highX = w.getWidth();
		int highY = w.getHeight(); 

		int xPlayer = r.nextInt(highX - low) + low;
		int yPlayer = r.nextInt(highY - low) + low;

		String s = xPlayer + "," + yPlayer;

		if(initialBoxesPosition.containsKey(s) == false && st.wallCheck(s) == false)
		{
			player.setX(xPlayer);
			player.setY(yPlayer);
			placeWalkedArea(xPlayer, yPlayer);
			return;
		}

		while(initialBoxesPosition.containsKey(s) == true || st.wallCheck(s) == true)
		{
			xPlayer = r.nextInt(w.getWidth());
			yPlayer = r.nextInt(w.getHeight());

			s = xPlayer + "," + yPlayer;
		}
		
		player.setX(xPlayer);
		player.setY(yPlayer);
		placeWalkedArea(xPlayer, yPlayer);
	}

	public char[] convertStringToChar(String s)
	{
		char[] ch = new char[s.length()];

		for(int i = 0; i < s.length(); i++)
            ch[i] = s.charAt(i);

		return ch;
	}

	public int getXFromString(String s)
	{
		return s.charAt(0);
	}

	public int getYFromString(String s)
	{
		return s.charAt(2);
	}

	public String getUpNeighbor(String pos)
	{
		int xPos = getXFromString(pos);
		int yPos = getYFromString(pos);
		return xPos + "," + (yPos - 1);
	}

	public String getDownNeighbor(String pos)
	{
		int xPos = getXFromString(pos);
		int yPos = getYFromString(pos);
		return xPos + "," + (yPos + 1);
	}

	public String getLeftNeighbor(String pos)
	{
		int xPos = getXFromString(pos);
		int yPos = getYFromString(pos);
		return (xPos - 1) + "," + yPos;
	}

	public String getRightNeighbor(String pos)
	{
		int xPos = getXFromString(pos);
		int yPos = getYFromString(pos);
		return (xPos + 1) + "," + yPos;
	}

	public Map<String, Boolean> createReachableMap()
	{
		Map<String, Boolean> accesible = new HashMap<String, Boolean>();//Contains positions of accessible tiles for a given player intial position
		Queue<String> q = new LinkedList<>();

		String startingPosition = player.getX() + "," + player.getY();
		q.add(startingPosition);

		while(q.size() != 0)
		{
			String pos = q.remove();
			
			if(accesible.containsKey(getUpNeighbor(pos)) == false && getYFromString(pos) != 0 && BoxesPosition.containsKey(pos) && st.wallCheck(pos) == false)
			{	
				accesible.put(getUpNeighbor(pos), true);
				q.add(getUpNeighbor(pos));
			}
			if(accesible.containsKey(getDownNeighbor(pos)) == false && getYFromString(pos) != w.getHeight() && BoxesPosition.containsKey(pos) && st.wallCheck(pos) == false)
			{
				accesible.put(getDownNeighbor(pos), true);
				q.add(getDownNeighbor(pos));
			}	

			if(accesible.containsKey(getLeftNeighbor(pos)) == false && getXFromString(pos) != 0 && BoxesPosition.containsKey(pos) && st.wallCheck(pos) == false)
			{
				accesible.put(getLeftNeighbor(pos), true);
				q.add(getLeftNeighbor(pos));
			}	

			if(accesible.containsKey(getRightNeighbor(pos)) == false && getXFromString(pos) != w.getWidth() && BoxesPosition.containsKey(pos) && st.wallCheck(pos) == false)
			{
				accesible.put(getRightNeighbor(pos), true);
				q.add(getRightNeighbor(pos));
			}
		}

		return accesible;
	}

 	public Box chooseBox(Map<String, Boolean> accesible)
	{
		int rand = r.nextInt(nbBoxes - 1);
		String pos = listBoxes[rand].generateStringPos();

		if(accesible.containsKey(pos) == true)
			return listBoxes[rand];

		while(accesible.containsKey(pos) != true)
		{
			rand = r.nextInt(nbBoxes - 1);
			pos = listBoxes[rand].generateStringPos();
		}

		return listBoxes[rand];
	}

	/*
	* Returns 	-1 if box can't move
	* 			 1 if box will move up
	*			 2 if box will move right	
	*			 3 if box will move down
	*			 4 if box will move left
	*/
	public int chooseDirection(Box b, Map<String, Boolean> accesible)
	{
		int rand = r.nextInt(3);
		int x = getXFromString(b.generateStringPos());
		int y = getYFromString(b.generateStringPos());

		switch(rand)
		{
			case 0://UP
			{
				String futurePos = x + "," + (y - 1);
				String playerPos = x + "," + (y + 1);
				if(BoxesPosition.containsKey(futurePos) == false && accesible.containsKey(playerPos) == false)
					return 1;
				else
					return chooseDirection(b, accesible);				
			}
			case 1://RIGHT
			{
				String futurePos = (x + 1) + "," + y;
				String playerPos =  (x - 1) + "," + y;
				if(BoxesPosition.containsKey(futurePos) == false && accesible.containsKey(playerPos) == false)
					return 2;
				else
					return chooseDirection(b, accesible);
			}
			case 2://DOWN
			{
				String futurePos = x + "," + (y + 1);
				String playerPos = x + "," + (y - 1);
				if(BoxesPosition.containsKey(futurePos) == false && accesible.containsKey(playerPos) == false)
					return 3;
				else
					return chooseDirection(b, accesible);
			}
			case 3://LEFT
			{
				String futurePos = (x - 1) + "," + y;
				String playerPos = (x + 1) + "," + y;
				if(BoxesPosition.containsKey(futurePos) == false && accesible.containsKey(playerPos) == false)
					return 4;
				else
					return chooseDirection(b, accesible);
			}
		}
			
		return -1;

		
	}

	public void generateRandomLevel()
	{
		placeRandomBoxes();
		placeRandomPlayer();

	}

}