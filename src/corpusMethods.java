import java.lang.Object;
import java.util.*;
public class corpusMethods
{
	HashMap frequencies = new HashMap();
	Vector letters = new Vector();
	String punctuation = ".,';:)(*&^%?$£! #~@";
	Boolean isPunctuation = false;
	alphabetFrequencies[] freqArray;
	
	public corpusMethods()
	{
	}
	
	public void insertLetters(String word)
	{
		int count = 0;
		word.toLowerCase();
		while( count< word.length() ) 
		{
			char letter = word.charAt( count++ );
			if(!frequencies.containsKey(letter))
			{
				isPunctuation = false;
				for(int i = 0; i < punctuation.length(); i ++)
				{
					if(letter == punctuation.charAt(i)) isPunctuation = true;
				}
				if(!isPunctuation)
				{
					frequencies.put(letter, 1);
					letters.add(letter);
				}
			}else
			{
				frequencies.put(letter, (int) frequencies.get(letter) + 1);
			}
		}
	}
	
	public void toFrequencyArray()
	{
		freqArray = new alphabetFrequencies[letters.size()];
		for (int i = 0; i < freqArray.length; i++)
		{
			freqArray[i] = new alphabetFrequencies((char)letters.elementAt(i), (int)frequencies.get(letters.elementAt(i)));
		}
		
	}
	
	public void bubbleSortArray()
	{
		int exchange;           // exchange variable will help perform the exchange
		char letExchange; 		// exchange the letters
		boolean swap = true;   // boolean swap checks to make sure that it continues making swaps, once it has stopped making swaps, it will go to false, the loop will end as the array should be sorted
		while (swap == true)
		{
			swap = false;   // declare swap to be false, this way the loop will end if no swaps are made
			for(int j = 0; j < freqArray.length - 1; j++)  // go through the entire array while making the swaps
			{
				
				if (freqArray[j].frequency < freqArray[j+1].frequency) // If the current index has a lesser value than the next value, they are exchanged, thus sorting the array in descending order
				{	
					exchange = freqArray[j].frequency;  //These next three lines do the int exchange
					freqArray[j].frequency = freqArray[j+1].frequency;
					freqArray[j+1].frequency = exchange;
					letExchange = freqArray[j].let;  //These next three lines do the letter exchange
					freqArray[j].let = freqArray[j+1].let;
					freqArray[j+1].let = letExchange;
					swap = true; // if a swap has taken place stay within the loop 
				} 
			}
		}
	}
}
