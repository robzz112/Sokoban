package be.uliege.boigelot.oop.sokoban.main;
import be.uliege.boigelot.oop.sokoban.gui.*;

import java.util.*;
import java.lang.*;

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
	private Box listBoxes[]= new Box[nbBoxes];//Array containing boxes
	private int nbStored;

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
		nbStored = 0;
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
				
			placeTarget(2, 4);
			placeTarget(2, 2);

			//placeRandomBoxes();
			placeBox(8, 1);
			placeBox(6, 5);

            //placeRandomPlayer();
			int xPlayer = 5;
			int yPlayer = 5;
			player.setX(xPlayer);
			player.setY(yPlayer);
			String playerPos = xPlayer + "," + yPlayer;
			placeWalkedArea(5, 5);

			w.getDisplay().setCell(player.getX(), player.getY(), w.getPlayerImage());	
            w.getDisplay().show();
			
			Map<String, Boolean> m = createReachableMap();
			
			Box q = chooseBox(m);
			System.out.println("Chosen Box is at " + q.generateStringPos());

			String goal = 8 + "," + 7;
			//int chosenDirection = choosePushingDirection(q, m);
			int chosenDirection = 2;
			String di = "";
			if(chosenDirection == 0)//UP
			{
				di = "up";
				goal = getXFromString(q.generateStringPos()) + "," + (getYFromString(q.generateStringPos())+1);
			}
			else if(chosenDirection == 1)//DOWN
			{
				di = "down";
				goal = getXFromString(q.generateStringPos()) + "," + (getYFromString(q.generateStringPos())-1);
			}
			else if(chosenDirection == 2)//LEFT
			{
				di = "left";
				goal = (getXFromString(q.generateStringPos())+1) + "," + getYFromString(q.generateStringPos());
			}
			else if(chosenDirection == 3)//Right
			{
				di = "right";
				goal = (getXFromString(q.generateStringPos())-1) + "," + getYFromString(q.generateStringPos());
			}

			System.out.println("Chosen Direction to push is " + di);
			System.out.println("Goal " + goal);
			
			System.out.println("Closest tile is in " + getNearestNeighbor(playerPos, goal, m));
			generatePath(playerPos, goal, m);

			/*int direc = choosePushingDirection(q, m);
			System.out.println("Chosen pushing direction is " + direc);
			String goal = 0 + "," + 0;
			switch(direc)
			{
				case 0://UP
					goal = getDownNeighbor(q.generateStringPos());

				case 1://DOWN
					goal = getUpNeighbor(q.generateStringPos());

				case 2://LEFT
					goal = getRightNeighbor(q.generateStringPos());				

				case 3://RIGHT
					goal = getLeftNeighbor(q.generateStringPos());
				
			}*/
			

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
		listBoxes[nbStored] = b;
		nbStored++;
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

	public boolean boxIsInCorner(Box b)
	{
		if(getXFromString(b.generateStringPos()) == 1 && getYFromString(b.generateStringPos()) == 1)//TOP LEFT CORNER
				return true;

		if(getXFromString(b.generateStringPos()) == w.getWidth() - 2 && getYFromString(b.generateStringPos()) == 1)//TOP RIGHT CORNER
				return true;

		if(getXFromString(b.generateStringPos()) == 1 && getYFromString(b.generateStringPos()) == w.getHeight() - 2)//BOTTOM LEFT CORNER
			return true;

		if(getXFromString(b.generateStringPos()) == w.getWidth() - 2 && getYFromString(b.generateStringPos()) == w.getHeight() - 2)//BOTTOM RIGHT CORNER
			return true;

		return false;
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
		int x = Character.getNumericValue(s.charAt(0));  
		return x;
	}

	public int getYFromString(String s)
	{
		int y = Character.getNumericValue(s.charAt(2)); 
		return y;
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
			System.out.println("removed from queue x = " + getXFromString(pos) + " y = " + getYFromString(pos));
			if(accesible.containsKey(getUpNeighbor(pos)) == false && getYFromString(pos) != 1  && st.wallCheck(getUpNeighbor(pos)) == false && BoxesPosition.containsKey(getUpNeighbor(pos)) == false)
			{	
				accesible.put(getUpNeighbor(pos), true);
				System.out.println("added up x = " + getXFromString(getUpNeighbor(pos)) + " added up y = " + getYFromString(getUpNeighbor(pos)));
				q.add(getUpNeighbor(pos));
			}
			if(accesible.containsKey(getDownNeighbor(pos)) == false && getYFromString(pos) != w.getHeight() - 1  && st.wallCheck(getDownNeighbor(pos)) == false && BoxesPosition.containsKey(getDownNeighbor(pos)) == false)
			{
				accesible.put(getDownNeighbor(pos), true);
				System.out.println("added down x = " + getXFromString(getDownNeighbor(pos)) + " added down y = " + getYFromString(getDownNeighbor(pos)));
				q.add(getDownNeighbor(pos));
			}	

			if(accesible.containsKey(getLeftNeighbor(pos)) == false && getXFromString(pos) != 1  && st.wallCheck(getLeftNeighbor(pos)) == false && BoxesPosition.containsKey(getLeftNeighbor(pos)) == false)
			{
				accesible.put(getLeftNeighbor(pos), true);
				System.out.println("added left x = " + getXFromString(getLeftNeighbor(pos)) + " added left y = " + getYFromString(getLeftNeighbor(pos)));
				q.add(getLeftNeighbor(pos));
			}	

			if(accesible.containsKey(getRightNeighbor(pos)) == false && getXFromString(pos) != w.getWidth() - 1 && st.wallCheck(getRightNeighbor(pos)) == false && BoxesPosition.containsKey(getRightNeighbor(pos)) == false)
			{
				accesible.put(getRightNeighbor(pos), true);
				System.out.println("added right x = " + getXFromString(getRightNeighbor(pos)) + " added right y = " + getYFromString(getRightNeighbor(pos)));
				q.add(getRightNeighbor(pos));
			}
		}

		return accesible;
	}

 	public Box chooseBox(Map<String, Boolean> accessible)
	{
		int rand = r.nextInt(nbBoxes);
		String pos = listBoxes[rand].generateStringPos();

		String upNeighbor = getUpNeighbor(pos);
		String downNeighbor = getDownNeighbor(pos);
		String leftNeighbor = getLeftNeighbor(pos);
		String rightNeighbor = getDownNeighbor(pos);

		if(accessible.containsKey(upNeighbor) == true || accessible.containsKey(downNeighbor) == true || accessible.containsKey(leftNeighbor) == true || accessible.containsKey(rightNeighbor) == true)
		{
			if(boxIsInCorner(listBoxes[rand]) == false)
				return listBoxes[rand];
		}
		while(true)
		{
			rand = r.nextInt(nbBoxes);
			if(boxIsInCorner(listBoxes[rand]) == true)
				continue;

			pos = listBoxes[rand].generateStringPos();

			upNeighbor = getUpNeighbor(pos);
			downNeighbor = getDownNeighbor(pos);
			leftNeighbor = getLeftNeighbor(pos);
			rightNeighbor = getDownNeighbor(pos);

			if(accessible.containsKey(upNeighbor) == true || accessible.containsKey(downNeighbor) == true || accessible.containsKey(leftNeighbor) == true || accessible.containsKey(rightNeighbor) == true)
				return listBoxes[rand];
		}
	}

	/* 	
	Returns		 0 if box will move up
	*			 1 if box will move down	
	*			 2 if box will move left
	*			 3 if box will move right
	*/
	public int choosePushingDirection(Box b, Map<String, Boolean> accessible)
	{
		String boxPos = b.generateStringPos();
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;

		if(accessible.containsKey(getDownNeighbor(boxPos)) == true && BoxesPosition.containsKey(getUpNeighbor(boxPos)) == false && getYFromString(boxPos) != 1)
			up = 1;
		if(accessible.containsKey(getUpNeighbor(boxPos)) == true && BoxesPosition.containsKey(getDownNeighbor(boxPos)) == false && getYFromString(boxPos) != w.getHeight() - 2)
			down = 1;
		if(accessible.containsKey(getRightNeighbor(boxPos)) == true && BoxesPosition.containsKey(getLeftNeighbor(boxPos)) == false && getXFromString(boxPos) != 1)
			left = 1;
		if(accessible.containsKey(getLeftNeighbor(boxPos)) == true && BoxesPosition.containsKey(getRightNeighbor(boxPos)) == false && getXFromString(boxPos) != w.getWidth() - 2)
			right = 1;

		while(true)
		{
			int rand = r.nextInt(4);
			switch(rand)
			{
				case 0://UP
				{
					if(up == 1)
						return 0;
					else
						continue;
				}

				case 1://DOWN
				{
					if(down == 1)
						return 1;
					else
						continue;
				}

				case 2://LEFT
				{
					if(left == 1)
						return 2;
					else
						continue;
				}

				case 3://RIGHT
				{
					if(right == 1)
						return 3;
					else
						continue;
				}
			}
		}
	}

	public double manhattanDistance(String pos1, String pos2)
	{
		double x1 = getXFromString(pos1);
		double y1 = getYFromString(pos1);
		double x2 = getXFromString(pos2);
		double y2 = getYFromString(pos2);

		return (Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
	}

/*RETURNS CLOSEST TILE DIRECTION
*			0 UP
*			1 DOWN
*			2 LEFT
*			3 RIGHT
*			-1 if pos == goal
*/	
	public int getNearestNeighbor(String pos, String goal, Map<String, Boolean> accessible)
	{
		int x = getXFromString(pos);
		int y = getYFromString(pos);

		int xGoal = getXFromString(goal);
		int yGoal = getYFromString(goal);

		if(x == xGoal && y == yGoal)
			return -1;

		double min = 100;
		double temp = 100;
		int direction = -1;		

		if(accessible.containsKey(getUpNeighbor(pos)) == true)
		{	
			temp = manhattanDistance(getUpNeighbor(pos), goal);
			System.out.println("UP distance = " + temp);
			if(temp <= min)
			{
				min = temp;
				direction = 0;
			}

		}
		if(accessible.containsKey(getDownNeighbor(pos)) == true)
		{
			temp = manhattanDistance(getDownNeighbor(pos), goal);
			System.out.println("DOWN distance = " + temp);
			if(temp <= min)
			{
			min = temp;
			direction = 1;
			}
		}	
			

		if(accessible.containsKey(getLeftNeighbor(pos)) == true)
		{
			temp = manhattanDistance(getLeftNeighbor(pos), goal);
			System.out.println("LEFT distance = " + temp);
			if(temp <= min)
			{
				min = temp;
				direction = 2;
			}
		}	
		
		if(accessible.containsKey(getRightNeighbor(pos)) == true)
		{	
			temp = manhattanDistance(getRightNeighbor(pos), goal);
			System.out.println("RIGHT distance = " + temp);
			if(temp <= min)
			{
				min = temp;
				direction = 3;
			}
		}
		return direction;
	}

	/*Marks path as visited */
	public void generatePath(String playerPos, String goal, Map<String, Boolean> accessible)
	{
		int xPlayer = getXFromString(playerPos);
		int yPlayer = getYFromString(playerPos);

		int xGoal = getXFromString(goal);
		int yGoal = getYFromString(goal);
		
		int i = 0;

		String currentPos = xPlayer + "," + yPlayer;
		int xPos = getXFromString(currentPos);
		int yPos = getYFromString(currentPos);

		while(getNearestNeighbor(currentPos, goal, accessible)!= -1)
		{	
			i++;
			if(i == 10)
				break;

			int closestTile = getNearestNeighbor(currentPos, goal, accessible);
			switch(closestTile)
			{
				case 0://UP
				{
					String upPos = getUpNeighbor(currentPos);
					currentPos = upPos;
					System.out.println("Marked visited " + upPos);
					placeWalkedArea(getXFromString(upPos), getYFromString(upPos));
				}

				case 1://DOWN
				{
					String downPos = getDownNeighbor(currentPos);
					currentPos = downPos;
					System.out.println("Marked visited " + downPos);
					placeWalkedArea(getXFromString(downPos), getYFromString(downPos));
				}

				case 2://LEFT
				{
					String leftPos = getLeftNeighbor(currentPos);
					currentPos = leftPos;
					System.out.println("Marked visited " + leftPos);
					placeWalkedArea(getXFromString(leftPos), getYFromString(leftPos));
				}

				case 3://RIGHT
				{
					String rightPos = getRightNeighbor(currentPos);
					currentPos = rightPos;
					System.out.println("Marked visited " + rightPos);
					placeWalkedArea(getXFromString(rightPos), getYFromString(rightPos));
				}
			} 
			xPos = getXFromString(currentPos);
			yPos = getYFromString(currentPos);
		}
	}

	public void forwardProcessInit()
	{
		placeRandomBoxes();
		placeRandomPlayer();
	}
	public void forwardProcess()
	{

	}
	public void generateRandomLevel()
	{
		

	}

}