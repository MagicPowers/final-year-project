import java.util.*; // need this so the program can use the java LinkedList and Collection classes
public class Node
{
	char letter;
	boolean endOfWord;
	Collection<Node> children;
	
	public Node(char currentLetter)
	{
		children = new LinkedList<Node>(); // The children of this node is a collection of other nodes
		endOfWord = false;   // endOfWord boolean is used to see if we have reached the end of a word, it is initially initialised to false
		letter = currentLetter; // the letter of the node is assigned
	}
	
	public Node subNode(char currentLetter)
	{
		if(children != null)  // avoiding the null expression
		{
			for(Node aChild:children) //For-Each loop which will check each child in the collection children
			{
				if(aChild.letter == currentLetter) return aChild; // if there is a child node with the letter currently being searched for, return that Node
			}
		}
		return null; // if no child has the current letter we are looking for return null
	}
}
