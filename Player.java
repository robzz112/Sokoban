package be.uliege.boigelot.oop.sokoban.main;
import be.uliege.boigelot.oop.sokoban.gui.*;

public class Player
{
    private int x;
    private int y;
    private int width;
    private int height;
	private Structure s;
    
    public Player(int x, int y, int width, int height, Structure s)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
		this.s = s;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean moveUp(Window w)
    {
        if(this.y == 0)
            return false;

        String st = x + "," + (y - 1);

        if(s.boxCheck(st) == true)
        {   
            System.out.println("BOX DETECTED");
            Box b = s.getBox(st);
            if(b.moveUp(s, w) == false)
                return false;
            else
                this.y--;
            return true;
        }
    
        if(s.wallCheck(st) == true)
        {
            System.out.println("WALL DETECTED");
            return false;
        }

        this.y--;
        return true;
    }

    public boolean moveDown(Window w)
    {
        if(this.y == this.height - 1)
            return false;

        String st = x + "," + (y + 1);
        
        if(s.boxCheck(st) == true)
        {   
            System.out.println("BOX DETECTED");
            Box b = s.getBox(st);
            if(b.moveDown(s, w) == false)
                return false;
            else
                this.y++;
            return true;

        }

        if(s.wallCheck(st) == true)
        {
            System.out.println("WALL DETECTED");
            return false;
        }
        
        this.y++;
        return true;
        
    }

    public boolean moveLeft(Window w)
    {
        if(this.x == 0)
            return false;

        String st = (x-1) + "," + y;
        
        if(s.boxCheck(st) == true)
        {   
            System.out.println("BOX DETECTED");
            Box b = s.getBox(st);
            if(b.moveLeft(s, w) == false)
                return false;
            else
                this.x--;
            return true;
        }

        if(s.wallCheck(st) == true)
        {
            System.out.println("WALL DETECTED");
            return false;
        }

        this.x--;
        return true;
    }

    public boolean moveRight(Window w)
    {
        if(this.x == this.width - 1)
            return false;

        String st = (x+1) + "," + y;
        
        if(s.boxCheck(st) == true)
        {   
            System.out.println("BOX DETECTED");
            Box b = s.getBox(st);
            if(b.moveRight(s, w) == false)
                return false;
            else
                this.x++;
            return true;
        }

        if(s.wallCheck(st) == true)
        {
            System.out.println("WALL DETECTED");
            return false;
        }


        this.x++;
        return true;
    }

    public void moveAfterCheck(Window w, int prevX, int prevY)
    {
        String stringPos = prevX + "," + prevY;

        try
        {
            w.getDisplay().setCell(this.getX(), this.getY(), w.getPlayerImage());

            if(s.targetCheck(stringPos) == true)
                w.getDisplay().setCell(prevX, prevY, w.getTargetImage());
            else
                w.getDisplay().setCell(prevX, prevY, w.getEmptyImage());
            w.getDisplay().show();
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

    public void move(Window w, int directionNumber)
    {
        int prevX = this.getX();
        int prevY = this.getY();
                   
        switch(directionNumber)
        {
            case 1:
			{
				boolean makeMove = this.moveUp(w);
                    
				if(makeMove != false)
					this.moveAfterCheck(w, prevX, prevY);
				break;
			}
            case 2:
			{
				boolean makeMove = this.moveRight(w);
                    
				if(makeMove != false)
					this.moveAfterCheck(w, prevX, prevY);
				break;
			}
			case 3:
			{
				boolean makeMove = this.moveDown(w);
                    
				if(makeMove != false)
					this.moveAfterCheck(w, prevX, prevY);
				break;
			}	
			case 4:
			{
				boolean makeMove = this.moveLeft(w);
                    
				if(makeMove != false)
					this.moveAfterCheck(w, prevX, prevY);
				break;
			}
        }   
    }
}