package be.uliege.boigelot.oop.sokoban.main;

public class Sokoban
{
    public static void main(String[] args)
    {
        int width = 10;
        int height = 10;
		int xPlayer = 5, yPlayer = 5;
			
		Structure st = new Structure();
		Player player = new Player(xPlayer, yPlayer, width, height, st);
        Window w =  new Window(height, width, st, player);
		
		w.startWindow(st, player);
	}
}