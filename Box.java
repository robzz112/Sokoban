package be.uliege.boigelot.oop.sokoban.main;
import be.uliege.boigelot.oop.sokoban.gui.*;

public class Box
{
    private int x;
    private int y;

    public Box(int xCoord, int yCoord)
    {
        this.x = xCoord;
        this.y = yCoord;
    }   

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void changeX(int dx)
    {
        x += dx;
    }
	
    public void changeY(int dy)
    {
        y += dy;
    }
	
    public String generateStringPos()
    {
        return x + "," + y;
    }

    public boolean checkFront(Structure s, String st)//Returns true if the path is free in front and false otherwise
    {
        if(s.wallCheck(st) == true)
            return false;
        else if(s.boxCheck(st) == true)
            return false;
        else
            return true;
    }

    public void updatePosition(int dx, int dy, Structure s, Window w)
    {
        s.changeCoordsInMap(this, dx, dy);
        
        try
        {
            if(s.targetCheck(this.generateStringPos()) == true)
            {
                w.getDisplay().setCell(this.x, this.y, w.getCrate2Image());
                s.increaseCounter();
            }
            else
                w.getDisplay().setCell(this.x, this.y, w.getCrateImage());
        }
        catch(SokobanError e)
        {
            String message = e.getMessage();
            System.err.println("SokobanError : "+ message);
            if(message == "Invalid cell coordinates")
                {
                    System.exit(1);
                }
        }
    }

    public boolean moveUp(Structure s, Window w)
    {
        String st = this.x + "," + (this.y - 1);

        if(checkFront(s, st) == false)
            return false;
        
        this.updatePosition(0, -1, s, w);
        return true;
    }

    public boolean moveDown(Structure s, Window w)
    {
        String st = this.x + "," + (this.y + 1);
        if(checkFront(s, st) == false)
            return false;

        this.updatePosition(0, 1, s, w);
        return true;
    }

    public boolean moveLeft(Structure s, Window w)
    {
        String st = (this.x - 1) + "," + this.y;
        if(checkFront(s, st) == false)
            return false;

        this.updatePosition(-1, 0, s, w);
        return true;
    }

    public boolean moveRight(Structure s, Window w)
    {
        String st = (this.x + 1) + "," + this.y;
        if(checkFront(s, st) == false)
            return false;

        this.updatePosition(1, 0, s, w);
        return true;
    }

}
