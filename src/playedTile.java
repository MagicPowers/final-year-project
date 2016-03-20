public class playedTile
{
	Letter letter; // the actual letter itself
	int position; // the position it was in on the rack
	
	public playedTile(Letter l, int p)
	{
		letter = new Letter (l.let, l.value, l.frequency); // the actual letter itself
		position = p;
	}
}
