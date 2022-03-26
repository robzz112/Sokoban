package be.uliege.boigelot.oop.sokoban.main;
import java.util.*;

public class Structure
{
    private Map<String, Boolean> wallsMap;
    private Map<String, Boolean> targetsMap;
    private Map<String, Box> boxesMap;
	private int boxesOnTargets = 0;

    public Structure()
    {
        wallsMap = new HashMap<String, Boolean>();
        targetsMap = new HashMap<String, Boolean>();
        boxesMap = new HashMap<String, Box>();
    }

    public void addWall(int xCoord, int yCoord)
    {
        String st = xCoord + "," + yCoord;
        this.wallsMap.put(st, true);
    }

    public void addTarget(int xCoord, int yCoord)
    {
        String st = xCoord + "," + yCoord;
        targetsMap.put(st, true);
    }

    public void addBox(Box b)
    {
        String st = b.generateStringPos();
        boxesMap.put(st, b);
        System.out.println("Box placed at " + st);
    }

    public boolean wallCheck(String pos)
    {
        return this.wallsMap.containsKey(pos);
    }

    public boolean targetCheck(String pos)
    {
        return this.targetsMap.containsKey(pos);
    }

    public boolean boxCheck(String pos)
    {
        return boxesMap.containsKey(pos);
    }

	public void increaseCounter()
    {
        this.boxesOnTargets++;
        System.out.println("Number of boxes on target = " + boxesOnTargets);
    }

    public void decreaseCounter()
    {
        this.boxesOnTargets--;
        System.out.println("Number of boxes on target = " + boxesOnTargets);
    }
	
    public Box getBox(String pos)
    {
        return boxesMap.get(pos);
    }

    public void changeCoordsInMap(Box b, int dx, int dy)//Changes the key of the box and updates coordinates
    {
        boxesMap.remove(b.generateStringPos());
        b.changeX(dx);
        b.changeY(dy);
        addBox(b);
    }

    public void removeWall(String pos)
    {
        this.wallsMap.remove(pos, true);
    }

    public void removetarget(String pos)
    {
        this.targetsMap.remove(pos, true);
    }

    public void removeBox(String pos, Box b)
    {
        this.boxesMap.remove(pos, b);
    }

}
