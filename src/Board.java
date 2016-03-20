import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;

public class Board extends JPanel implements Observer
{
	public Font squareFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);  // This is the font that shall be used to display the letters
	public Letter playerLetter;
	private Square[][] squareGrid = new Square[15][15]; // make my board of squares, 15 * 15 array
	public boolean canPlaceLetter = false; // This boolean is used to see if the player is allowed to place a letter
	public boolean isFirstTurn = true; // This boolean is used to signal whether this is the first move or not
	public boolean isFirstLetter = true; // This boolean is used to signal whether this is the first letter of the player's current move
	public boolean isDoubleWord = false;
	public boolean isTrippleWord = false;
	public boolean isQuadrupleWord = false;
	public boolean isNonupleWord = false;
	public MoveCoordinates recentMoves[] = new MoveCoordinates[7]; //This array will hold all of the recent moves the player made
	public int MoveCounter = 0; // This will count the amount of moves the player makes
	public int score = 0; // keeps track of players score for a word
	Subject rack; // Initialise the subject of this board
	
	// Below I create the custom colours I want to use for the Scrabble game
	Color scrabbleGreen = new Color(0, 113, 90);
    Color scrabbleBorders = new Color(184, 185, 146);
    Color doubleLetter = new Color(155, 209, 220);
    Color doubleWord = new Color(251, 207, 129);
    Color trippleLetter = new Color(0, 154, 176);
    Color trippleWord = new Color(227, 82, 26);
    
    public Board()
    {
		setBackground(scrabbleBorders); //set the colour of the borders
		setLayout(new GridLayout(15, 15, 5, 5)); // set up the layout of the board, 15 * 15, with a spacing of 5 between squares
		ActionListener squareListener = new SquareListener();
		
		for (int i = 0; i < squareGrid.length; i++)
		{
			for (int j = 0; j < squareGrid[i].length; j++)
			{
				Square square = new Square("", playerLetter, false, i, j); // Initialise the square with its coordinates, in an unoccupied state with a blank letter
				square.setBackground(scrabbleGreen);
				square.setPreferredSize(new Dimension(60, 60));
				square.setFont(squareFont);
				square.addActionListener(squareListener);
				add(square);
				squareGrid[i][j] = square;
			}
		}
		
		setDoubleLetterSquares();
		setDoubleWordSquares();
		setTrippleLetterSquares();
		setTrippleWordSquares();
		
	}
	
	public void setDoubleLetterSquares()
	{
		squareGrid[3][0].setBackground(doubleLetter);
        squareGrid[11][0].setBackground(doubleLetter);
        squareGrid[6][2].setBackground(doubleLetter);
        squareGrid[8][2].setBackground(doubleLetter);
        squareGrid[0][3].setBackground(doubleLetter);
        squareGrid[7][3].setBackground(doubleLetter);
        squareGrid[14][3].setBackground(doubleLetter);
        squareGrid[2][6].setBackground(doubleLetter);
        squareGrid[6][6].setBackground(doubleLetter);
        squareGrid[8][6].setBackground(doubleLetter);
        squareGrid[12][6].setBackground(doubleLetter);
        squareGrid[3][7].setBackground(doubleLetter);
        squareGrid[11][7].setBackground(doubleLetter);
        squareGrid[2][8].setBackground(doubleLetter);
        squareGrid[6][8].setBackground(doubleLetter);
        squareGrid[8][8].setBackground(doubleLetter);
        squareGrid[12][8].setBackground(doubleLetter);
        squareGrid[0][11].setBackground(doubleLetter);
        squareGrid[7][11].setBackground(doubleLetter);
        squareGrid[14][11].setBackground(doubleLetter);
        squareGrid[6][12].setBackground(doubleLetter);
        squareGrid[8][12].setBackground(doubleLetter);
        squareGrid[3][14].setBackground(doubleLetter);
        squareGrid[11][14].setBackground(doubleLetter);
	}
	
	public void setDoubleWordSquares()
	{
		squareGrid[1][1].setBackground(doubleWord);
		squareGrid[13][1].setBackground(doubleWord);
		squareGrid[2][2].setBackground(doubleWord);
		squareGrid[12][2].setBackground(doubleWord);
		squareGrid[3][3].setBackground(doubleWord);
		squareGrid[11][3].setBackground(doubleWord);
		squareGrid[4][4].setBackground(doubleWord);
		squareGrid[10][4].setBackground(doubleWord);
		squareGrid[4][10].setBackground(doubleWord);
		squareGrid[10][10].setBackground(doubleWord);
		squareGrid[3][11].setBackground(doubleWord);
		squareGrid[11][11].setBackground(doubleWord);
		squareGrid[2][12].setBackground(doubleWord);
		squareGrid[12][12].setBackground(doubleWord);
		squareGrid[1][13].setBackground(doubleWord);
		squareGrid[13][13].setBackground(doubleWord);
	}
	
	public void setTrippleLetterSquares()
	{
		squareGrid[5][1].setBackground(trippleLetter);
		squareGrid[9][1].setBackground(trippleLetter);
		squareGrid[1][5].setBackground(trippleLetter);
		squareGrid[5][5].setBackground(trippleLetter);
		squareGrid[9][5].setBackground(trippleLetter);
		squareGrid[13][5].setBackground(trippleLetter);
		squareGrid[1][9].setBackground(trippleLetter);
		squareGrid[5][9].setBackground(trippleLetter);
		squareGrid[9][9].setBackground(trippleLetter);
		squareGrid[13][9].setBackground(trippleLetter);
		squareGrid[5][13].setBackground(trippleLetter);
		squareGrid[9][13].setBackground(trippleLetter);
	}
	
	public void setTrippleWordSquares()
	{
		squareGrid[0][0].setBackground(trippleWord);
		squareGrid[7][0].setBackground(trippleWord);
		squareGrid[14][0].setBackground(trippleWord);
		squareGrid[0][7].setBackground(trippleWord);
		squareGrid[14][7].setBackground(trippleWord);
		squareGrid[0][14].setBackground(trippleWord);
		squareGrid[7][14].setBackground(trippleWord);
		squareGrid[14][14].setBackground(trippleWord);	
	}
	
	public int checkMultiplier(int row, int column)
	{
		int multiplier = 0;
	    if (squareGrid[row][column].getBackground() == scrabbleGreen)
		{
			multiplier = 0;
		}
		if (squareGrid[row][column].getBackground() == doubleLetter)
		{
			multiplier = 1;
		}
		if (squareGrid[row][column].getBackground() == doubleWord)
		{
			multiplier = 2;
		}
		if (squareGrid[row][column].getBackground() == trippleLetter)
		{
			multiplier = 3;
		}
		if (squareGrid[row][column].getBackground() == trippleWord)
		{
			multiplier = 4;
		}
		return multiplier;
	}
	
	public void addSubject(Subject s)
	{
		this.rack = s;
	}
	
	public void notifySubject()
	{
		rack.Update();
	}
	
	public void Update(Letter l)
	{
		this.playerLetter = new Letter(l.let, l.value, l.frequency);
		canPlaceLetter = true;
	}
	
	public boolean isValidMove(int x, int y)
	{
		boolean validity = false;
		boolean linkBroken = false;
		int firstMove = 0; // The coordinates of the first move are going to be located in the first slot of the array
		int previousMove = MoveCounter - 1; // The coordinates of the previous move are going to be located in the previous slot in the array
		if (squareGrid[x][y].occupied) return false; // If the square is already occupied, it is not a valid move
		if (x == recentMoves[firstMove].X && x == recentMoves[previousMove].X) // If the x coordinate of this move is the same as the first move
		{
			if(y > recentMoves[firstMove].Y) // if the current move's y index is greater than the first move's y index
			{
				for (int i = recentMoves[firstMove].Y; i < y; i++)
				{
					if(!squareGrid[x][i].occupied) linkBroken = true; // if at any of the tiles in between the first tile and the current tile are unoccupied it means it is an invalid move
				}
			} else
			{
				for (int i = y + 1; i < recentMoves[firstMove].Y; i++)
				{
					if(!squareGrid[x][i].occupied) linkBroken = true; // if at any of the tiles in between the first tile and the current tile are unoccupied it means it is an invalid move
				}
			}
			if(!linkBroken) validity = true;
		}else if (y == recentMoves[firstMove].Y && y == recentMoves[previousMove].Y) // If the y coordinate of this move is the same as the first move
		{
			if(x > recentMoves[firstMove].X) // if the current move's x index is greater than the first move's x index
			{
				for (int i = recentMoves[firstMove].X; i < x; i++)
				{
					if(!squareGrid[i][y].occupied) linkBroken = true; // if at any of the tiles in between the first tile and the current tile are unoccupied it means it is an invalid move
				}
			} else
			{
				for (int i = x + 1; i < recentMoves[firstMove].X; i++)
				{
					if(!squareGrid[i][y].occupied) linkBroken = true; // if at any of the tiles in between the first tile and the current tile are unoccupied it means it is an invalid move
				}
			}
			if(!linkBroken) validity = true;  
		}
		return validity;
	}
	
	public boolean isValidFirstMove(int x, int y)
	{
		boolean validity = false;
		if (squareGrid[x][y].occupied) return false; // If the square is already occupied, it is not a valid move
		if (x != 0 && y != 0 && x != 14 && y != 14) // if neither of the coordinates are at the edge, check all around them
		{
			if(squareGrid[x + 1][y].occupied) validity = true;
			if(squareGrid[x - 1][y].occupied) validity = true;
			if(squareGrid[x][y + 1].occupied) validity = true;
			if(squareGrid[x][y - 1].occupied) validity = true;
		}
		else if (x == 0 && y == 0)
		{
			if(squareGrid[x + 1][y].occupied) validity = true;
			if(squareGrid[x][y + 1].occupied) validity = true;
		}else if(x == 14 && y == 14)
		{
			if(squareGrid[x - 1][y].occupied) validity = true;
			if(squareGrid[x][y - 1].occupied) validity = true;
		}else if(x == 0 && y == 14)
		{
			if(squareGrid[x + 1][y].occupied) validity = true;
			if(squareGrid[x][y - 1].occupied) validity = true;
		}else if(x == 14 && y == 0)
		{
			if(squareGrid[x - 1][y].occupied) validity = true;
			if(squareGrid[x][y + 1].occupied) validity = true;
		}else if(x == 0 && y != 14)
		{
			if(squareGrid[x + 1][y].occupied) validity = true;
			if(squareGrid[x][y + 1].occupied) validity = true;
			if(squareGrid[x][y - 1].occupied) validity = true;
		}else if(x != 14 && y == 0)
		{
			if(squareGrid[x - 1][y].occupied) validity = true;
			if(squareGrid[x + 1][y].occupied) validity = true;
			if(squareGrid[x][y + 1].occupied) validity = true;
		}else if(x == 14 && y != 0)
		{
			if(squareGrid[x - 1][y].occupied) validity = true;
			if(squareGrid[x][y - 1].occupied) validity = true;
			if(squareGrid[x][y + 1].occupied) validity = true;
		}else if(x != 0 && y == 14)
		{
			if(squareGrid[x - 1][y].occupied) validity = true;
			if(squareGrid[x + 1][y].occupied) validity = true;
			if(squareGrid[x][y - 1].occupied) validity = true;
		}	
		return validity;
	}
	
	public String[] getWord()
	{
		isDoubleWord = false;
		isTrippleWord = false;
		isQuadrupleWord = false;  // Setting all of the possible word multipliers to false at the new word check
		isNonupleWord = false;
		String[] allWords = new String[16]; // Make an array of Strings for checking for adjacent words, the array is 16 elements long, as the most amount of words you can make in one turn is 16, N + 1 where N is the number of squares in a row
		String[] finalListOfWords = new String[16]; // This array of Strings shall be re initialised once we know how many adjacent words there are
		StringBuilder word = new StringBuilder("***************");
		String wordFound = "";
		String adjacentWord = "";
		int initialLetterIndex = 0;
		int finalLetterIndex = 0;
		int adjacentWordsFound = 1; // This int shall be used to store any adjacent words found, so it needs to start at one, as that will be the index of the first adjacent word as the played word will be in index 0
		
		if(MoveCounter > 1)
		{
			if(recentMoves[0].X == recentMoves[1].X) // X plane is the same
			{
				for(int i = 0; i < MoveCounter; i++) // this initial for loop is to deal with adjacent words only
				{
					if(recentMoves[i].X != 0 && recentMoves[i].X != 14)
					{
						if(squareGrid[recentMoves[i].X - 1][recentMoves[i].Y].occupied || squareGrid[recentMoves[i].X + 1][recentMoves[i].Y].occupied) // If either x - 1, or x + 1 are occupied, it means we have an adjacent word
						{
							adjacentWord = squareGrid[recentMoves[i].X][recentMoves[i].Y].letter.let;
							int preX = recentMoves[i].X - 1;
							while (squareGrid[preX][recentMoves[i].Y].occupied)
							{
								adjacentWord = squareGrid[preX][recentMoves[i].Y].letter.let + adjacentWord;
								if(preX == 0) break;
								preX --;
							}
							int nextX = recentMoves[i].X + 1;
							while (squareGrid[nextX][recentMoves[i].Y].occupied)
							{
								adjacentWord = adjacentWord + squareGrid[nextX][recentMoves[i].Y].letter.let;
								if(nextX == 14) break;
								nextX ++;
							}
							allWords[adjacentWordsFound] = adjacentWord;
							adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
						}
					}else if(recentMoves[i].X != 0)
					{
						if(squareGrid[recentMoves[i].X + 1][recentMoves[i].Y].occupied) // If x + 1 is occupied it means we have an adjacent word
						{
							adjacentWord = squareGrid[recentMoves[i].X][recentMoves[i].Y].letter.let;
							int preX = recentMoves[i].X - 1;
							while (squareGrid[preX][recentMoves[i].Y].occupied)
							{
								adjacentWord = squareGrid[preX][recentMoves[i].Y].letter.let + adjacentWord;
								if(preX == 0) break;
								preX --;
							}
							allWords[adjacentWordsFound] = adjacentWord;
							adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
						}
					} else if(recentMoves[i].X != 14)
					{
						if(squareGrid[recentMoves[i].X - 1][recentMoves[i].Y].occupied) // If either x - 1 is occupied it means we have an adjacent word
						{
							adjacentWord = squareGrid[recentMoves[i].X][recentMoves[i].Y].letter.let;
							int nextX = recentMoves[i].X + 1;
							while (squareGrid[nextX][recentMoves[i].Y].occupied)
							{
								adjacentWord = adjacentWord + squareGrid[nextX][recentMoves[i].Y].letter.let;
								if(nextX == 14) break;
								nextX ++;
							}
							allWords[adjacentWordsFound] = adjacentWord;
							adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
						}
					}	
				}
				for(int i = 0; i < MoveCounter; i++) // add in the letters of the recent moves
				{
					word.deleteCharAt(recentMoves[i].Y);
					word.insert(recentMoves[i].Y, recentMoves[i].letter.let);
					score = updateScore(recentMoves[0].X, recentMoves[i].Y, squareGrid[recentMoves[0].X][recentMoves[i].Y].letter.value);
					wordFound = word.toString();
				} 
				for (int i = 0; i < word.length(); i++) // find out the initial letter's index
				{
					if(word.charAt(i) != '*')
					{
						initialLetterIndex = i;
						break;
					}
				}
				if (initialLetterIndex != 0)
				{
					if(squareGrid[recentMoves[0].X][initialLetterIndex - 1].occupied)
					{
						int preY = initialLetterIndex - 1;
						while(squareGrid[recentMoves[0].X][preY].occupied)
						{
							word.deleteCharAt(preY);
							word.insert(preY, squareGrid[recentMoves[0].X][preY].letter.let);
							score = updateScore(recentMoves[0].X, preY, squareGrid[recentMoves[0].X][preY].letter.value);
							initialLetterIndex --; // decrement the initialLetterIndex by 1
							if(preY == 0) break; // don't want to send the array out of index
							preY --;
						}
					}
				}
				wordFound = word.toString();
				for (int i = word.length() - 1; i > initialLetterIndex; i--) // find out the final letter's index
				{
					if(word.charAt(i) != '*')
					{
						finalLetterIndex = i;
						break;
					}
				}
				if (finalLetterIndex != 14) 
				{
					if(squareGrid[recentMoves[0].X][finalLetterIndex + 1].occupied)
					{
						int nextY = finalLetterIndex + 1;
						while(squareGrid[recentMoves[0].X][nextY].occupied)
						{
							word.deleteCharAt(nextY);
							word.insert(nextY, squareGrid[recentMoves[0].X][nextY].letter.let);
							score = updateScore(recentMoves[0].X, nextY, squareGrid[recentMoves[0].X][nextY].letter.value);
							finalLetterIndex ++; // increment the finalLetterIndex by 1
							if(nextY == 14) break; // don't want to send the array out of index
							nextY ++;
						}
					}
				}
				// Now we need to check if there are any letters in between the first and last one still to enter
				StringBuilder checker = new StringBuilder(word.toString());
				for(int i = initialLetterIndex; i < finalLetterIndex; i++)
				{
					if (checker.charAt(i) == '*')
					{
						word.deleteCharAt(i);
						word.insert(i, squareGrid[recentMoves[0].X][i].letter.let);
					}
				}
				word.delete(0, initialLetterIndex); // delete all of the characters before the first letter of the word
				finalLetterIndex = finalLetterIndex - initialLetterIndex; // because we have taken the elements before initialLetterIndex off the board, we must update finalLetterIndex
				word.delete(finalLetterIndex + 1, word.length()); // delete all of the characters after the final letter of the word
				wordFound = word.toString();
			}else // as it is a valid move, if the X plane is not the same, the Y plane must be
			{
				for(int i = 0; i < MoveCounter; i++) // this initial for loop is to deal with adjacent words only
				{
					if(recentMoves[i].Y != 0 && recentMoves[i].Y != 14)
					{
						if(squareGrid[recentMoves[i].X][recentMoves[i].Y - 1].occupied || squareGrid[recentMoves[i].X][recentMoves[i].Y + 1].occupied) // If either y - 1, or y + 1 are occupied, it means we have an adjacent word
						{
							adjacentWord = squareGrid[recentMoves[i].X][recentMoves[i].Y].letter.let;
							int preY = recentMoves[i].Y - 1;
							while (squareGrid[recentMoves[i].X][preY].occupied)
							{
								adjacentWord = squareGrid[recentMoves[i].X][preY].letter.let + adjacentWord;
								if(preY == 0) break;
								preY --;
							}
							int nextY = recentMoves[i].Y + 1;
							while (squareGrid[recentMoves[i].X][nextY].occupied)
							{
								adjacentWord = adjacentWord + squareGrid[recentMoves[i].X][nextY].letter.let;
								if(nextY == 0) break;
								nextY ++;
							}
							allWords[adjacentWordsFound] = adjacentWord;
							adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
						}
					}else if(recentMoves[i].Y != 0)
					{
						if(squareGrid[recentMoves[i].X][recentMoves[i].Y + 1].occupied) // If y + 1 is occupied it means we have an adjacent word
						{
							adjacentWord = squareGrid[recentMoves[i].X][recentMoves[i].Y].letter.let;
							int preY = recentMoves[i].Y - 1;
							while (squareGrid[recentMoves[i].X][preY].occupied)
							{
								adjacentWord = squareGrid[recentMoves[i].X][preY].letter.let + adjacentWord;
								if(preY == 0) break;
								preY --;
							}
							allWords[adjacentWordsFound] = adjacentWord;
							adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
						}
					} else if(recentMoves[i].Y != 14)
					{
						if(squareGrid[recentMoves[i].X][recentMoves[i].Y - 1].occupied) // If either y - 1 is occupied it means we have an adjacent word
						{
							adjacentWord = squareGrid[recentMoves[i].X][recentMoves[i].Y].letter.let;
							int nextY = recentMoves[i].Y + 1;
							while (squareGrid[recentMoves[i].X][nextY].occupied)
							{
								adjacentWord = adjacentWord + squareGrid[recentMoves[i].X][nextY].letter.let;
								if(nextY == 14) break;
								nextY ++;
							}
							allWords[adjacentWordsFound] = adjacentWord;
							adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
						}
					}	
				}
				for(int i = 0; i < MoveCounter; i++) // add in the letters of the recent moves
				{
					word.deleteCharAt(recentMoves[i].X);
					word.insert(recentMoves[i].X, recentMoves[i].letter.let);
					score = updateScore(recentMoves[i].X, recentMoves[0].Y, squareGrid[recentMoves[i].X][recentMoves[0].Y].letter.value);
				}
				for (int i = 0; i < word.length(); i++) // find out the initial letter's index
				{
					if(word.charAt(i) != '*')
					{
						initialLetterIndex = i;
						break;
					}
				}
				if (initialLetterIndex != 0)
				{
					if(squareGrid[initialLetterIndex - 1][recentMoves[0].Y].occupied)
					{
						int preX = initialLetterIndex - 1;
						while(squareGrid[preX][recentMoves[0].Y].occupied)
						{
							word.deleteCharAt(preX);
							word.insert(preX, squareGrid[preX][recentMoves[0].Y].letter.let);
							score = updateScore(preX, recentMoves[0].Y, squareGrid[preX][recentMoves[0].Y].letter.value);
							initialLetterIndex --; // decrement the initialLetterIndex by 1
							if(preX == 0) break; // don't want to send the array out of index
							preX --;
						}
					}
				}
				for (int i = word.length() - 1; i > initialLetterIndex; i--) // find out the final letter's index
				{
					if(word.charAt(i) != '*')
					{
						finalLetterIndex = i;
						break;
					}
				}
				if (finalLetterIndex != 14)
				{
					if(squareGrid[finalLetterIndex + 1][recentMoves[0].Y].occupied)
					{
						int nextX = finalLetterIndex + 1;
						while(squareGrid[nextX][recentMoves[0].Y].occupied)
						{
							word.deleteCharAt(nextX);
							word.insert(nextX, squareGrid[nextX][recentMoves[0].Y].letter.let);
							score = updateScore(nextX, recentMoves[0].Y, squareGrid[nextX][recentMoves[0].Y].letter.value);
							finalLetterIndex ++; // increment the finalLetterIndex by 1
							if(nextX == 14) break; // don't want to send the array out of index
							nextX ++;
						}
					}
				}
				// Now we need to check if there are any letters in between the first and last one still to enter
				StringBuilder checker = new StringBuilder(word.toString());
				for(int i = initialLetterIndex; i < finalLetterIndex; i++)
				{
					if (checker.charAt(i) == '*')
					{
						word.deleteCharAt(i);
						word.insert(i, squareGrid[i][recentMoves[0].Y].letter.let);
					}
				}
				word.delete(0, initialLetterIndex); // delete all of the characters before the first letter of the word
				finalLetterIndex = finalLetterIndex - initialLetterIndex; // because we have taken the elements before initialLetterIndex off the board, we must update finalLetterIndex
				word.delete(finalLetterIndex + 1, word.length()); // delete all of the characters after the final letter of the word
				wordFound = word.toString();
			}
			allWords[0] = wordFound; // place the word Found in the allWords String array
			finalListOfWords = new String[adjacentWordsFound]; // create a new String array that is as long as the amount of words found
			for (int i = 0; i < adjacentWordsFound; i++)
			{
				finalListOfWords[i] = allWords[i];  // Go through all of the words found and put them in the final list of words
			}
		}else // if the user only played one letter
		{
			allWords[adjacentWordsFound] = adjacentWord;
			adjacentWordsFound  = 0; // set adjacentWordsFound to 0 as if the user only entered one letter, they need another letter to make a word, so the letter itself cannot be added as a word 
			wordFound = squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.let;
			if (recentMoves[0].Y != 0)
			{
				if(squareGrid[recentMoves[0].X][recentMoves[0].Y - 1].occupied)
				{
					int preY = recentMoves[0].Y - 1;
					while(squareGrid[recentMoves[0].X][preY].occupied)
					{
						wordFound = squareGrid[recentMoves[0].X][preY].letter.let + wordFound;							
						score = updateScore(recentMoves[0].X, preY, squareGrid[recentMoves[0].X][preY].letter.value);
						if(preY == 0) break; // don't want to send the array out of index
						preY --;
					}
					score = updateScore(recentMoves[0].X, recentMoves[0].Y, squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.value);
					allWords[adjacentWordsFound] = wordFound;
					adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
				}
			}
			if (recentMoves[0].X != 0)
			{
				wordFound = squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.let;
				if(squareGrid[recentMoves[0].X - 1][recentMoves[0].Y].occupied)
				{
					int preX = recentMoves[0].X - 1;
					while(squareGrid[preX][recentMoves[0].Y].occupied)
					{
						wordFound = squareGrid[preX][recentMoves[0].Y].letter.let + wordFound;							
						score = updateScore(preX, recentMoves[0].Y, squareGrid[preX][recentMoves[0].Y].letter.value);
						if(preX == 0) break; // don't want to send the array out of index
						preX --;
					}
					score = updateScore(recentMoves[0].X, recentMoves[0].Y, squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.value);
					allWords[adjacentWordsFound] = wordFound;
					adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
				}
			}
			if (recentMoves[0].Y != 14)
			{
				wordFound = squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.let;
				if(squareGrid[recentMoves[0].X][recentMoves[0].Y + 1].occupied)
				{
					int nextY = recentMoves[0].Y + 1;
					while(squareGrid[recentMoves[0].X][nextY].occupied)
					{
						wordFound = wordFound + squareGrid[recentMoves[0].X][nextY].letter.let;							
						score = updateScore(recentMoves[0].X, nextY, squareGrid[recentMoves[0].X][nextY].letter.value);
						if(nextY == 14) break; // don't want to send the array out of index
						nextY ++;
					}
					score = updateScore(recentMoves[0].X, recentMoves[0].Y, squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.value);
					allWords[adjacentWordsFound] = wordFound;
					adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
				}
			}
			if (recentMoves[0].X != 14)
			{
				wordFound = squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.let;
				if(squareGrid[recentMoves[0].X + 1][recentMoves[0].Y].occupied)
				{
					int nextX = recentMoves[0].X + 1;
					while(squareGrid[nextX][recentMoves[0].Y].occupied)
					{
						wordFound = wordFound + squareGrid[nextX][recentMoves[0].Y].letter.let;							
						score = updateScore(nextX, recentMoves[0].Y, squareGrid[nextX][recentMoves[0].Y].letter.value);
						if(nextX == 14) break; // don't want to send the array out of index
						nextX ++;
					}
					score = updateScore(recentMoves[0].X, recentMoves[0].Y, squareGrid[recentMoves[0].X][recentMoves[0].Y].letter.value);
					allWords[adjacentWordsFound] = wordFound;
					adjacentWordsFound ++; // Increment the adjacentWordsFound as if another one is found it shall have to go into the index
				}
			}
			finalListOfWords = new String[adjacentWordsFound]; // create a new String array that is as long as the amount of words found
			for (int i = 0; i < adjacentWordsFound; i++)
			{
				finalListOfWords[i] = allWords[i];  // Go through all of the words found and put them in the final list of words
			}
		}
		return finalListOfWords;
	}
	
	public int getScore()
	{
		if (isQuadrupleWord) score = score * 4;
		else if (isDoubleWord) score = score * 2;
		if (isNonupleWord) score = score * 9;
		else if (isTrippleWord) score = score * 3;
		return score;
	}
	
	public int updateScore(int x, int y, int tileScore)
	{
		int m = checkMultiplier(x, y);
		if (m == 0) score = score + tileScore;
		else if (m == 1) score = score + (tileScore * 2); // Double Letter
		else if (m == 2)
		{
			if (isDoubleWord) isQuadrupleWord = true;
			else isDoubleWord = true;	
		}else if (m == 3) score = score + (tileScore * 3);// Tripple Letter
		else if (m == 4)
		{
			if (isTrippleWord) isNonupleWord = true;
			else isTrippleWord = true;
		}
		
		return score;
	}
	
	public MoveCoordinates getAppropriateIndexForComputer()
	{
		int x = 0;
		int y = 0;
		for (int i = 0; i < 15; i++)
		{
			for (int j = 0; j < 15; j++)
			{
				if(squareGrid[i][j].occupied)
				{
					if(i > 3 && i < 12 && j > 3 && j < 12)
					{
						if((squareGrid[i + 1][j].occupied) || (squareGrid[i - 1][j].occupied))
						{
							x = 3;;
						}else if((squareGrid[i][j + 1].occupied) || (squareGrid[i][j - 1].occupied))
						{
							y = 8;
						} 
					}
				}
			}
		}
		MoveCoordinates appropriateMove = new MoveCoordinates(null, x, y);
		return appropriateMove;
	}
	
	public void takeLettersOff()
	{
		for(int i = 0; i < MoveCounter; i++)
		{
			squareGrid[recentMoves[i].X][recentMoves[i].Y].letter = new Letter("", 0, 0);
			squareGrid[recentMoves[i].X][recentMoves[i].Y].occupied = false;
			squareGrid[recentMoves[i].X][recentMoves[i].Y].setText("");
		}
	}
	
	private class SquareListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Square square = (Square) e.getSource();
			if (canPlaceLetter)
			{
				if(!squareGrid[7][7].occupied) isFirstTurn = true;
				if(isFirstLetter == true && isFirstTurn == true)
				{
					if(square.X == 7 && square.Y == 7)
					{
						square.letter = new Letter(playerLetter.let, playerLetter.value, playerLetter.frequency);
						square.occupied = true;
						square.setText(square.letter.let);
						canPlaceLetter = false;
						notifySubject(); //Notify the subject that the tile has been placed
						recentMoves[MoveCounter] = new MoveCoordinates(square.letter, square.X, square.Y); 
						MoveCounter ++;
						isFirstLetter = false;
						isFirstTurn = false;
					} else
					{
						System.out.println("Warning!!! Illegal move, must be on center tile");
						JOptionPane.showMessageDialog(null, "Warning!!! Illegal move, first move must be on center tile", "Illegal Move", JOptionPane.ERROR_MESSAGE);
					}
				}else if(isFirstLetter == true)
				{
					if(isValidFirstMove(square.X, square.Y))
					{
						square.letter = new Letter(playerLetter.let, playerLetter.value, playerLetter.frequency);
						square.occupied = true;
						square.setText(square.letter.let);
						canPlaceLetter = false;
						notifySubject(); //Notify the subject that the tile has been placed
						recentMoves[MoveCounter] = new MoveCoordinates(square.letter, square.X, square.Y);
						MoveCounter ++;
						isFirstLetter = false;
					}else
					{
						System.out.println("Warning!!! Illegal move, must be next to tile already on the board");
						JOptionPane.showMessageDialog(null, "Warning!!! Illegal move, must be next to a tile already on the board", "Illegal Move", JOptionPane.ERROR_MESSAGE);
					}
				}else
				{
					if(isValidMove(square.X, square.Y))
					{
						square.letter = new Letter(playerLetter.let, playerLetter.value, playerLetter.frequency);
						square.occupied = true;
						square.setText(square.letter.let);
						canPlaceLetter = false;
						notifySubject(); //Notify the subject that the tile has been placed
						recentMoves[MoveCounter] = new MoveCoordinates(square.letter, square.X, square.Y);
						MoveCounter ++;
					}else
					{
						System.out.println("Warning!!! Illegal move, must be next to the recent tile you placed on the board");
						JOptionPane.showMessageDialog(null, "Warning!!! Illegal move, must be next to the recent tile you placed on the board", "Illegal Move", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
    
}
