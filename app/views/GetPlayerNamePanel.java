package views;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetPlayerNamePanel extends JPanel {
    private JTextField player1Field;
    private JTextField player2Field;
    private JLabel player1Label;
    private JLabel player2Label;
    private JButton startButton;
    private JButton backButton;

    public GetPlayerNamePanel() {
        setBackground(new Color(0xEDEAE6));
        setPreferredSize(new Dimension(400, 400));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        player1Label=new JLabel("Player 1 Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(player1Label, gbc);

        player1Field = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(player1Field, gbc);

        player2Label=new JLabel("Player 2 Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(player2Label, gbc);

        player2Field = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(player2Field, gbc);

        backButton = new JButton("Back");

        player1Field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player2Field.requestFocusInWindow();
            }
        });
        player2Field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton.doClick();
            }
        });

        backButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        backButton.setFocusable(false);
        backButton.setBackground(new Color(0xCA8D37));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(80, 30));
        backButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(backButton, gbc);

        startButton = new JButton("Start Game");

        startButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        startButton.setFocusable(false);
        startButton.setBackground(new Color(0xCA8D37));
        startButton.setForeground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(150, 30));
        startButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(startButton, gbc);
    }

    public String getPlayer1Name() {
        String name=player1Field.getText().trim();
        player1Field.setText("");
        return name;
    }

    public String getPlayer2Name() {
        String name=player2Field.getText().trim();
        player2Field.setText("");
        return name;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void addStartButtonActionListener(ActionListener l) {
        startButton.addActionListener(l);
    }
    public void addBackButtonActionListener(ActionListener l) {
        backButton.addActionListener(l);
    }

    public JLabel getPlayer1Label() {
        return player1Label;
    }

    public JLabel getPlayer2Label() {
        return player2Label;
    }

    //    public static void main(String[] args) {
//        JFrame frame = new JFrame("Enter Player Names");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//        frame.setLocationRelativeTo(null);
//
//        GetPlayerNamePanel panel = new GetPlayerNamePanel();
//        frame.add(panel);
//
//        // مثال: ثبت عملکرد برای دکمه‌ها
//        panel.getStartButton().addActionListener(e -> {
//            String name1 = panel.getPlayer1Name();
//            String name2 = panel.getPlayer2Name();
//            JOptionPane.showMessageDialog(frame, "Starting game for: " + name1 + " vs " + name2);
//        });
//
//        panel.getBackButton().addActionListener(e -> {
//            JOptionPane.showMessageDialog(frame, "Going back to main menu...");
//        });
//
//        frame.setVisible(true);
//    }
}
