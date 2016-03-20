import java.util.Random;
import java.util.*;
public class LetterBag
{
	public Vector bag = new Vector(); 
	public Letter[] alphabet = new Letter[26];
	
    public LetterBag()
    {
        alphabet = this.createAlphabetArray();
        this.arrayToVector(alphabet);
        
        
    }
    
    public Letter[] createAlphabetArray()
    {
        alphabet[0] = new Letter("A",1,9);
        alphabet[1] = new Letter("B",3,2);
        alphabet[2] = new Letter("C",3,2);
        alphabet[3] = new Letter("D",2,4);
        alphabet[4] = new Letter("E",1,12);
        alphabet[5] = new Letter("F",4,2);
        alphabet[6] = new Letter("G",2,3);
        alphabet[7] = new Letter("H",4,2);
        alphabet[8] = new Letter("I",1,9);
        alphabet[9] = new Letter("J",8,1);
        alphabet[10] = new Letter("K",5,1);
        alphabet[11] = new Letter("L",1,4);
        alphabet[12] = new Letter("M",3,2);
        alphabet[13] = new Letter("N",1,6);
        alphabet[14] = new Letter("O",1,8);
        alphabet[15] = new Letter("P",3,2);
        alphabet[16] = new Letter("Q",10,1);
        alphabet[17] = new Letter("R",1,6);
        alphabet[18] = new Letter("S",1,4);
        alphabet[19] = new Letter("T",1,6);
        alphabet[20] = new Letter("U",1,4);
        alphabet[21] = new Letter("V",4,2);
        alphabet[22] = new Letter("W",8,2);
        alphabet[23] = new Letter("X",4,1);
        alphabet[24] = new Letter("Y",4,2);
        alphabet[25] = new Letter("Z",10,1);
        return alphabet;
    }
    
    public Letter[] createIrishAlphabetArray()
    {
        Letter[] alphabet = new Letter[23];
        alphabet[0] = new Letter("A",1,13);
        alphabet[1] = new Letter("B",10,1);
        alphabet[2] = new Letter("C",2,4);
        alphabet[3] = new Letter("D",2,4);
        alphabet[4] = new Letter("E",1,6);
        alphabet[5] = new Letter("F",4,2);
        alphabet[6] = new Letter("G",2,3);
        alphabet[7] = new Letter("H",1,10);
        alphabet[8] = new Letter("I",1,10);
        alphabet[11] = new Letter("L",2,4);
        alphabet[12] = new Letter("M",4,2);
        alphabet[13] = new Letter("N",1,7);
        alphabet[14] = new Letter("O",2,4);
        alphabet[15] = new Letter("P",10,1);
        alphabet[17] = new Letter("R",1,7);
        alphabet[18] = new Letter("S",1,6);
        alphabet[19] = new Letter("T",2,4);
        alphabet[20] = new Letter("U",2,3);
        alphabet[21] = new Letter("Á",4,2);
        alphabet[22] = new Letter("É",8,1);
        alphabet[9] = new Letter("Í",4,2);
        alphabet[10] = new Letter("Ó",8,1);
        alphabet[16] = new Letter("Ú",8,1);
        return alphabet;
    }
    
   // public Letter[] createCorpusAlphabetArray()
    //{
		
//	}
    
    public void arrayToVector(Letter[] alphabet)
    {
        for(int i = 0; i < alphabet.length; i++)
        {
            for(int j = 0; j < alphabet[i].frequency; j++)
            {
                bag.add(alphabet[i]);
            }
        }
        Letter blank = new Letter("", 0, 1);
        bag.add(blank);
        bag.add(blank);  // have to add two blank tiles with no value
        
        //Shall have to add checks in later, make sure less than 100 elements
    }
    
    public Letter retrieveRandomLetter()
    {
        int i = ((int)(Math.random() * bag.size())); // getting random letter using Math and the current letter bag size
        Letter l = (Letter)bag.elementAt(i); // retrieve the letter at the randomly assigned position in the vector
        bag.remove(i); // remove the letter we are about to take out of the letter bag
        return l;
    }
    
    public void placeLetterBack(Letter l) 
    {
		Letter t = new Letter(l.let, l.value, l.frequency);
        bag.add(t); // insert the letter at the last point in the letterbag
    }
}
