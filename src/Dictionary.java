// this java file is going to be used to store the dictionary
public class Dictionary
{
	Node head;
	Node currentNode;
	Node child;
	 
	public Dictionary()
	{
		head = new Node(' '); // initial node is not going to contain any letter, just a space
	}
	
	public void insertWord(String word)
	{
		currentNode = head;
		int endOfWord = word.length() - 1;
		char currentLetter = ' ';
		for (int i = 0; i < word.length(); i++)
		{
			currentLetter = word.charAt(i);
			child = currentNode.subNode(currentLetter);
			if(child == null) // if the character we are currently looking for is not a child of the current node, create a new node with that character
			{
				currentNode.children.add(new Node(currentLetter));
				currentNode = currentNode.subNode(currentLetter);
			}else
			{
				currentNode = child; // otherwise, if the character is one of the current node's children, simply set the current node to the child node
			}
			if(i == endOfWord) currentNode.endOfWord = true; // mark when we have reached the end of a word
		}
	}
	
	public boolean findWord(String word)
	{
		currentNode = head;
		char currentLetter = ' ';
		while (currentNode != null) // in case our word is not in the dictionary we do not want to be looking at null nodes
		{
			for(int i = 0; i < word.length(); i++)
			{
				currentLetter = word.charAt(i);
				if(currentNode.subNode(currentLetter) == null)
				{
					return false;  // if none of the children have the current letter then the Dictionary does not contain the word
				}else
				{
					currentNode = currentNode.subNode(currentLetter);
				}
			}
			// if we have gotten this far, that means that there is a string in the dictionary with all of the letters of the current word, but we need to make sure that this is the end of the word in the dictionary
			return currentNode.endOfWord;
		}
		return false;
	}
}
