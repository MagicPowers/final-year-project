public class MoveCoordinates
{
	Letter letter; // the actual letter itself
	int X; // the X coordiante of the move
	int Y; // the Y coordinate of the move
	
	public MoveCoordinates(Letter l, int x, int y)
	{
		letter = new Letter (l.let, l.value, l.frequency); // the actual letter itself
		X = x;
		Y = y;
	}
}
