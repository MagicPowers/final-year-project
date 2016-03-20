import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Rack extends JPanel implements Subject
{
	public Font rackFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);  // This is the font that shall be used to display the letters
	public Letter randomLetter;
	Observer board; // Initialise the observer of this rack
	public boolean tileHasBeenPlaced = true; // boolean to let rack know when a tile has been placed on the board
	private Tile[][] rackGrid = new Tile[1][7];
	public playedTile[] playedTiles = new playedTile[7]; // similar to recentMoves, this is used to track the tiles played
	public int tilesCounter = 0; // similar to MoveCounter, counts amount of tiles that have been played
	Color scrabbleTile = new Color(254, 230, 149);
	Color scrabbleRack = new Color(173, 99, 12);
	public LetterBag bag = new LetterBag();
	public int tilePosition = 0; //position of the tile in the rack
	
	public Rack(LetterBag b)
	{
		bag = b;
		setBackground(scrabbleRack); // Set the colour of the rack
		setLayout(new GridLayout(1, 7, 10, 5));
		ActionListener tileListener = new TileListener();
		
		for (int i = 0; i < rackGrid.length; i++)
		{
			for (int j = 0; j < rackGrid[i].length; j++)
			{
				randomLetter = bag.retrieveRandomLetter();
				Letter t = new Letter(randomLetter.let, randomLetter.value, randomLetter.frequency);
				Tile tile = new Tile(t.let, t, true, tilePosition);
				tilePosition ++;
				tile.setBackground(scrabbleTile);
				Dimension d = new Dimension(60, 60);
				tile.setPreferredSize(d);
				tile.setMinimumSize(d);
				tile.setMaximumSize(d);
				tile.setFont(rackFont);
				tile.addActionListener(tileListener);
				add(tile);
				rackGrid[i][j] = tile;				
			}
		}		
	}
	
	public void newRack()
	{
		for (int i = 0; i < rackGrid.length; i++)
		{
			for (int j = 0; j < rackGrid[i].length; j++)
			{
				if (rackGrid[i][j].filled == true)  // Go through all of the tiles on the rack and put them back in the bag
				{
					randomLetter = rackGrid[i][j].letter;
					Letter t = new Letter(randomLetter.let, randomLetter.value, randomLetter.frequency);
					rackGrid[i][j].filled = false;
					bag.placeLetterBack(t);
				}				
			}
		}
		fillRack(); // after they have all been taken off refill the rack
	}
	
	public void fillRack() 
	{
		for (int i = 0; i < rackGrid.length; i++)
		{
			for (int j = 0; j < rackGrid[i].length; j++)
			{
				if (rackGrid[i][j].filled == false)
				{
					randomLetter = bag.retrieveRandomLetter();
					Letter t = new Letter(randomLetter.let, randomLetter.value, randomLetter.frequency);
					rackGrid[i][j].filled = true;
					rackGrid[i][j].letter = t;
					rackGrid[i][j].setText(t.let);
				}				
			}
		}
	}
	
	public void putLettersBack()
	{
		for(int i = 0; i < tilesCounter; i++)
		{
			Letter t = new Letter(playedTiles[i].letter.let, playedTiles[i].letter.value, playedTiles[i].letter.frequency);
			if (t.value == 0) t = new Letter("", 0, 0); // this if statement makes sure we make the blank tile blank again
			rackGrid[0][playedTiles[i].position].filled = true;
			rackGrid[0][playedTiles[i].position].letter = t;
			rackGrid[0][playedTiles[i].position].setText(t.let);
		}
	}
	
	public void addObserver(Observer o)
	{
		this.board = o;
	}
	
	public void notifyObserver(Letter l)
	{
		board.Update(l);
	}
	
	public void Update()
	{
		tileHasBeenPlaced = true;
	}
	
	private class TileListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(tileHasBeenPlaced == true)
			{
				Tile tile = (Tile) e.getSource();
				if(tile.filled == true)
				{
					if(tile.letter.value == 0)
					{
						String letterWanted = JOptionPane.showInputDialog("What Letter would you like this tile to be?"); // This if statement asks the user what they want to place for a blank tile
						if(letterWanted.length() != 1)
						{
							JOptionPane.showMessageDialog(null, "Can only put in one letter!", "Too many letters", JOptionPane.ERROR_MESSAGE);
							letterWanted = JOptionPane.showInputDialog("What Letter would you like this tile to be?");
						}
						tile.letter = new Letter(letterWanted, 0, 0);
					}
					notifyObserver(tile.letter);
					playedTiles[tilesCounter] = new playedTile(tile.letter, tile.position);
					tile.setFilled(false);
					tileHasBeenPlaced = false;
					tile.letter = new Letter("", 0, 0);
					tile.setText(tile.letter.let);
					tilesCounter ++;
				}
			}
		}
	}
}
