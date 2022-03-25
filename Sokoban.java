package be.uliege.boigelot.oop.sokoban.main;
import be.uliege.boigelot.oop.sokoban.gui.*;

public class Sokoban
{
    public static void main(String[] args)
    {
        
        int width = 10;
        int height = 10;
		int xPlayer = 5, yPlayer = 5;
			
		Structure st = new Structure();
        Window w =  new Window(height, width, st);
        Player player = new Player(xPlayer, yPlayer, width, height);
		
		w.startWindow(st, player);
	}
	
}