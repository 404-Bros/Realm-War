package views;import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class UnitSelectionDialog extends JDialog {
    private JComboBox<String> unitComboBox;
    private JLabel unitHPLabel;
    private JLabel unitAttackPower;
    private JLabel unitePaymentCostLabel;
    private JLabel unitRationLabel;
    private JButton doneButton;
    private JButton cancelButton;

    public UnitSelectionDialog(JFrame parent) {
        super(parent, "Select Unit", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBackground(new Color(0xEDEAE6));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        JLabel chooseUnitLabel = new JLabel("Choose Unit:");
        chooseUnitLabel.setPreferredSize(new Dimension(150, 25));
        contentPanel.add(chooseUnitLabel, gbc);

        gbc.gridx = 1;
        unitComboBox = new JComboBox<>(new String[]{"None", "Peasant", "Swordman", "Spearman", "Knight"});
        unitComboBox.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(unitComboBox, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        unitHPLabel = new JLabel("HP:");
        unitHPLabel.setPreferredSize(new Dimension(150, 25));
        contentPanel.add(unitHPLabel, gbc);

        gbc.gridx = 1;
        unitAttackPower = new JLabel("Attack Power:");
        unitAttackPower.setPreferredSize(new Dimension(150, 25));
        contentPanel.add(unitAttackPower, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        unitePaymentCostLabel = new JLabel("Payment Cost:");
        unitePaymentCostLabel.setPreferredSize(new Dimension(150, 25));
        contentPanel.add(unitePaymentCostLabel, gbc);

        gbc.gridx = 1;
        unitRationLabel = new JLabel("Ration Cost:");
        unitRationLabel.setPreferredSize(new Dimension(150, 25));
        contentPanel.add(unitRationLabel, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        doneButton = new JButton("Done");
        doneButton.setFocusable(false);
        doneButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        doneButton.setBackground(new Color(0xCA8D37));
        doneButton.setForeground(Color.WHITE);
        doneButton.setPreferredSize(new Dimension(100, 40));
        doneButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        cancelButton.setBackground(new Color(0xCA8D37));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

        buttonPanel.add(doneButton);
        buttonPanel.add(cancelButton);

        contentPanel.add(buttonPanel, gbc);

        getContentPane().setLayout(new GridBagLayout());
        getContentPane().add(contentPanel);

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    public JComboBox<String> getUnitComboBox() {
        return unitComboBox;
    }

    public JLabel getUnitHPLabel() {
        return unitHPLabel;
    }

    public JLabel getUnitAttackPower() {
        return unitAttackPower;
    }

    public JLabel getUnitePaymentCostLabel() {
        return unitePaymentCostLabel;
    }

    public JLabel getUnitRationLabel() {
        return unitRationLabel;
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
