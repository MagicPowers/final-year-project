import javax.swing.*;

public class Tile extends JButton
{
	public Letter letter;
	public boolean filled = false;
	public int position;
	
	public Tile(String text, Letter l, boolean f, int p)
	{
		super(text);
		this.letter = l;
		this.filled = f;
		this.position = p;
	}
	
	public String getLetter()
	{
		return letter.let;
	}
	
	public void setLetter(String l)
	{
		this.letter.let = l;
	}
	
	public boolean getFilled()
	{
		return filled;
	}
	
	public void setFilled(boolean f)
	{
		this.filled = f;
	}
}
