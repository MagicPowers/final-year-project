public class Letter
{
	String let = ""; // the actual letter itself
	int value; // the value associated with that letter
	int frequency; // the frequency with which that letter occurs
	
	public Letter(String l, int v, int f)
	{
		let = l; // the actual letter itself
		value = v; // the value associated with that letter
		frequency = f; // the frequency with which that letter occurs
	}
	
	public void setLetter (String l)
	{
		this.let = l;
	}
	
	public String getLetter()
	{
		return this.let;
	}

}
