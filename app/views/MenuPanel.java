import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel{
    private JButton newGameButton;
    private JButton loadGameButton;
    private JPanel centerPanel;
    private JLabel welcomeLabel;
    public MenuPanel(){
        setLayout(new BorderLayout());
        setBackground(new Color(125,125,125));


        welcomeLabel = new JLabel("Welcome to Realm War");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 50));
        welcomeLabel.setForeground(new Color(0,255,0));
        add(welcomeLabel, BorderLayout.NORTH);




        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(0x292828));
        centerPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        newGameButton = new JButton("New Game");
        newGameButton.setSize(100, 50);
        newGameButton.setFont(new Font("Serif", Font.BOLD, 25));
        centerPanel.add(newGameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        loadGameButton = new JButton("Load Game");
        loadGameButton.setFont(new Font("Serif", Font.BOLD, 25));
        centerPanel.add(loadGameButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

    }
}