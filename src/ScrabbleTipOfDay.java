import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;


public class ScrabbleTipOfDay extends JDialog {


    public ScrabbleTipOfDay() {

        initUI();
    }

    public final void initUI() 
    {
        JPanel basic = new JPanel();
        basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
        add(basic);
        
        JPanel topPanel = new JPanel(new BorderLayout(0, 0));
        topPanel.setMaximumSize(new Dimension(450, 0));
        JLabel hint = new JLabel("David's Scrabble Hints");
        hint.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        topPanel.add(hint);
		
		String[] icons = new String[4];
		icons[0] = "img/Mario.png";
		icons[2] = "img/Retro Flower - Fire 2.png";
		icons[3] = "img/Retro Coin.png";
		icons[1] = "img/Retro Mushroom - Super 2.png";
		int i = ((int)(Math.random() * 4)); 
        ImageIcon icon = new ImageIcon(icons[i]);
        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(label, BorderLayout.EAST);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);

        topPanel.add(separator, BorderLayout.SOUTH);

        basic.add(topPanel);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JTextPane pane = new JTextPane();

        pane.setContentType("text/html");
        String hint1 = "<p><b>Two's Company</b></p>" +
            "<p>The first step in mastering Scrabble is not to memorize the dictionary (though you can if you fancy) but to learn all the two-letter words. There are 124 of them. Step two is probably to learn all the three-letter words, but there’s 1310 of these, so let’s stick to the twos. One of the reasons they’re so important is they’ll let you play off other words almost anywhere on the board.</p>";
         
        String hint2 = "<p><b>Emptying Your Vowels</b></p>" +
            "<p>If you have failed to keep things balanced you can end up with a rack full of vowels that are tough to get rid of. It can take two or three turns to restore the balance, and in that time you’re unlikely to score well since the vowels are worth only one point. </p>";
         
        String hint3 = "<p><b>Rack Management</b></p>" +
            "<p>World-class Scrabble, like chess and successful picnics, is all about planning ahead. It might seem like a good idea to play the highest-scoring word you can every go, but if you do that you'll find that you rarely get a very big word and often end up with an unplayable rack of letters. Instead, try playing words that leave you with a good balance of letters left on your rack for your next go.</p>";
         
        String hint4 = "<p><b>Don't be Afraid of Change</b></p>" +
            "<p>It’s as true in Scrabble as it is in life: some people hate having to change. Don’t be afraid. You may have to score zero for one turn if you change your letters, but if the alternative is to be left with a horrible combination of letters, like IIIUVVW, then chuck them in. </p>";
        
        String[] hints = new String[4];
        hints[0] = hint1;
        hints[1] = hint2;
        hints[2] = hint3;
        hints[3] = hint4;
        int j = ((int)(Math.random() * 4));            
        pane.setText(hints[j]);
        pane.setEditable(false);
        textPanel.add(pane);

        basic.add(textPanel);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton close = new JButton("Close");
        close.setMnemonic(KeyEvent.VK_C);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                dispose();
            }
        });

        bottom.add(close);
        basic.add(bottom);

        bottom.setMaximumSize(new Dimension(450, 0));

        setTitle("Tip of the Day");
        setSize(new Dimension(450, 350));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    public static void run() 
    {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                ScrabbleTipOfDay ex = new ScrabbleTipOfDay();
                ex.setVisible(true);
            }
        });
    }
}
