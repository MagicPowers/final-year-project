public interface Subject
{
	void addObserver(Observer o);
	void notifyObserver(Letter l);
	public void Update();
}
