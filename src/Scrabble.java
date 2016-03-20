import javax.swing.*;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import java.io.*;

public class Scrabble extends JFrame
{
	public String playerName = "";
	public int playerScore = 0;
	public int computerScore = 0;
	public Dictionary dictionary = new Dictionary(); // declaring the new dictionary
	String selectedDictionary = "";
	public boolean isInDictionary = false;
	public boolean adjacentWordsInDictionary = true;
	public boolean Corpus = false;
	public corpusMethods corpus = new corpusMethods(); // declaring corpus methods
	
	public Scrabble(String d)
	{
		playerName = getPlayerName();
		selectedDictionary = d;
		initUI();
	}
	
	private void initUI()
	{
		createScrabble();
		
		setTitle("Epic Scrabble");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void createScrabble()
	{
		LetterBag bag = new LetterBag(); 
		final Rack rack = new Rack(bag);
		final Rack computerRack = new Rack(bag);
		final Board board = new Board();
		setDictionary();
		rack.addObserver(board);
		board.addSubject(rack);
		ImageIcon PlayWord = new ImageIcon("img/PlayWord.png");
		JButton playWord = new JButton(PlayWord);
		ImageIcon NewRack = new ImageIcon("img/NewRack.png");
		JButton newRack = new JButton(NewRack);
		add(playWord, BorderLayout.WEST);
		add(newRack, BorderLayout.CENTER);
		add(board, BorderLayout.EAST);
		add(rack, BorderLayout.NORTH);
		
		playWord.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                String[] words = board.getWord();
                if(words.length == 1) // If there is only one word, no adjacent words need to be checked
                {
					isInDictionary = dictionary.findWord(words[0].toLowerCase());                
					if (isInDictionary)
					{
						JOptionPane.showMessageDialog(null, "Score for "+ words[0] +" is: " + board.getScore(), "Word Score", JOptionPane.INFORMATION_MESSAGE);
						playerScore = playerScore + board.getScore();
						rack.fillRack();
						board.score = 0;
						board.isFirstLetter = true;
						board.recentMoves = new MoveCoordinates[7];
						board.MoveCounter = 0;
						rack.playedTiles = new playedTile[7];
						rack.tilesCounter = 0;
						rack.tilePosition = 0;
					}else
					{
						JOptionPane.showMessageDialog(null, words[0] + " does not exist in dictionary, INVALID!", "Word Not Found", JOptionPane.ERROR_MESSAGE);
						board.takeLettersOff();
						rack.putLettersBack();
						board.score = 0;
						board.isFirstLetter = true;
						board.recentMoves = new MoveCoordinates[7];
						board.MoveCounter = 0;
						rack.playedTiles = new playedTile[7];
						rack.tilesCounter = 0;
						rack.tilePosition = 0;
					}
				}else // If there were adjacent words
				{
					int wordNotInDictionary = 0; // This int is going to be used to store the index of a word that if found to not be in the dictionary
					adjacentWordsInDictionary = true;
					for(int i = 0; i < words.length; i++)
					{
						isInDictionary = dictionary.findWord(words[i].toLowerCase());
						if(!isInDictionary)
						{
							adjacentWordsInDictionary = false;
							wordNotInDictionary = i;
							break; // break out of the for loop if we have found a word not in the dictionary, as we do not need to check any more of the words
						}
					}
					if(adjacentWordsInDictionary)
					{
						int adjacentWordScore = 0;
						String messageToUser = "Score for "+ words[0] +" is: " + board.getScore();
						scoreArray checkScore = new scoreArray(selectedDictionary);
						for (int i = 1; i < words.length; i++)
						{
							adjacentWordScore = 0;
							for (int j = 0; j < words[i].length(); j++)
							{
								adjacentWordScore = adjacentWordScore + checkScore.returnScoreOfLetter(words[i].charAt(j));
							}
							playerScore = playerScore + adjacentWordScore;
							messageToUser = messageToUser + "\nScore for "+ words[i] +" is: " + adjacentWordScore;
						}
						JOptionPane.showMessageDialog(null, messageToUser, "Word Score", JOptionPane.INFORMATION_MESSAGE);
						playerScore = playerScore + board.getScore();
						rack.fillRack();
						board.score = 0;
						board.isFirstLetter = true;
						board.recentMoves = new MoveCoordinates[7];
						board.MoveCounter = 0;
						rack.playedTiles = new playedTile[7];
						rack.tilesCounter = 0;
						rack.tilePosition = 0;
					}else
					{
						JOptionPane.showMessageDialog(null, words[wordNotInDictionary] + " does not exist in dictionary, INVALID!", "Word Not Found", JOptionPane.ERROR_MESSAGE);
						board.takeLettersOff();
						rack.putLettersBack();
						board.score = 0;
						board.isFirstLetter = true;
						board.recentMoves = new MoveCoordinates[7];
						board.MoveCounter = 0;
						rack.playedTiles = new playedTile[7];
						rack.tilesCounter = 0;
						rack.tilePosition = 0;
					}
				}
			}
        });
        
        newRack.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                rack.newRack();
            }
        });
        
        JMenuBar menubar = new JMenuBar();
        
        ImageIcon newGame = new ImageIcon("img/new.png");
        ImageIcon save = new ImageIcon("img/save.png");
        ImageIcon exit = new ImageIcon("img/exit.png");
        ImageIcon question = new ImageIcon("img/Question.png");
        ImageIcon score = new ImageIcon("img/score.png");
        final ImageIcon musicOn = new ImageIcon("img/SoundOn.png");
        final ImageIcon musicOff = new ImageIcon("img/Mute.png");
        
        JMenu gameMenu = new JMenu("Game");
        JMenu hintsMenu = new JMenu("Hints");
        
        JMenuItem hints = new JMenuItem("Get Hint", question);
        hints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ScrabbleTipOfDay tip = new ScrabbleTipOfDay();
                tip.run();
            }
        });
        hintsMenu.add(hints);
        
        JMenuItem newItem = new JMenuItem("New Game", newGame);
        JMenuItem saveItem = new JMenuItem("Save", save);
        JMenuItem exitItem = new JMenuItem("Exit", exit);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        newItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                Scrabble.run(selectedDictionary); // run a new game with the same dictionary
                setVisible(false);
                dispose();
            }
        });
        gameMenu.add(newItem);
        gameMenu.add(saveItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        final JButton soundButton = new JButton("Music", musicOn);
        final backgroundMusic music = new backgroundMusic();
        music.playBackgroundMusic();
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (soundButton.getIcon() == musicOn)
                {
					soundButton.setIcon(musicOff);
					music.stopMusic();
					
				}else
				{
					soundButton.setIcon(musicOn);
					music.playBackgroundMusic();
				}
            }
        });
        
        JButton checkScore = new JButton("Check Score", score);
        checkScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, playerName + ": " + playerScore + "\nComputer: " + computerScore, "Score Board", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        menubar.add(gameMenu);
        menubar.add(hintsMenu);        
        menubar.add(Box.createHorizontalGlue());
        menubar.add(checkScore);
		menubar.add(soundButton);
        
        setJMenuBar(menubar);
	}
	
	
	public void setDictionary()
	{
		try // buffered reader which reads in the dictionary from the text into the Dictionary trie data structure
		{
			BufferedReader input = new BufferedReader(new FileReader( new File(selectedDictionary)));
			String word;
			while((word = input.readLine()) != null)
			{
				dictionary.insertWord(word);
				if (Corpus) corpus.insertLetters(word);
			}
			input.close();
		}catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public String getPlayerName()
	{
		String name = JOptionPane.showInputDialog("What is your name?"); 
		return name;
	}
	
	public static void run(final String d) 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                Scrabble ex = new Scrabble(d);
                ex.setVisible(true);
            }
        });
    }
}
