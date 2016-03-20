import javax.swing.*;

public class Square extends JButton
{
	public Letter letter;
	public boolean occupied;
	public int X; // X coordinate of Square in grid
	public int Y; // Y coordinate of Square in grid
	
	public Square(String text, Letter l, boolean o, int x, int y)
	{
		super(text);
		this.letter = l;
		this.occupied = o;
		this.X = x;
		this.Y = y;
	}
	
	public String getLetter()
	{
		return letter.let;
	}
}
