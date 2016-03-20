public class scoreArray
{
	alphabetScores[] score = new alphabetScores[26]; // the letter
	
	public scoreArray(String dict)
	{
		if(dict == "EnglishDictionary.txt") makeEnglishScore();
	}
	
	public int returnScoreOfLetter(char l)
	{
		for (int i = 0; i < 26; i ++)
		{
			if (score[i].let == l) return score[i].score;
		}
		return 0;
	}
	
	public void makeEnglishScore()
	{
		score[0] = new alphabetScores('A',1);
        score[1] = new alphabetScores('B',3);
        score[2] = new alphabetScores('C',3);
        score[3] = new alphabetScores('D',2);
        score[4] = new alphabetScores('E',1);
        score[5] = new alphabetScores('F',4);
        score[6] = new alphabetScores('G',2);
        score[7] = new alphabetScores('H',4);
        score[8] = new alphabetScores('I',1);
        score[9] = new alphabetScores('J',8);
        score[10] = new alphabetScores('K',5);
        score[11] = new alphabetScores('L',1);
        score[12] = new alphabetScores('M',3);
        score[13] = new alphabetScores('N',1);
        score[14] = new alphabetScores('O',1);
        score[15] = new alphabetScores('P',3);
        score[16] = new alphabetScores('Q',10);
        score[17] = new alphabetScores('R',1);
        score[18] = new alphabetScores('S',1);
        score[19] = new alphabetScores('T',1);
        score[20] = new alphabetScores('U',1);
        score[21] = new alphabetScores('V',4);
        score[22] = new alphabetScores('W',8);
        score[23] = new alphabetScores('X',4);
        score[24] = new alphabetScores('Y',4);
        score[25] = new alphabetScores('Z',10);
		
	}
}

	
