import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Options extends JFrame 
{
    public Options() 
    {
        initUI();
    }

    private void initUI() 
    {   
        createOptions();

        setTitle("Please choose the game type you'd like: ");
        setSize(575, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void createOptions()
    {
		ImageIcon English = new ImageIcon("img/English.png");
		ImageIcon Irish = new ImageIcon("img/Irish.png");
		ImageIcon Corpus = new ImageIcon("img/Corpus.png");

        JButton Eng = new JButton(English);
        JButton Ir = new JButton(Irish);
        JButton Cor = new JButton(Corpus);
        
        Eng.setBackground(new Color(25, 97, 158));
        Ir.setBackground(new Color(72, 163, 37));
        Cor.setBackground(new Color(221, 128, 45));

        Eng.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                Scrabble.run("EnglishDictionary.txt");
                setVisible(false);
                dispose();
            }
        });
        
        Ir.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                Scrabble.run("IrishDictionary.txt");
                setVisible(false);
                dispose();
            }
        });
        
        Cor.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                System.out.println("Corpus Scrabble selected :D");
            }
        });
        
        add(Eng, BorderLayout.NORTH);
        add(Ir, BorderLayout.CENTER);
        add(Cor, BorderLayout.SOUTH);
	}

    public static void main(String[] args) 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                Options ex = new Options();
                ex.setVisible(true);
            }
        });
    }
}
