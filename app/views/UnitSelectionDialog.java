package views;import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class UnitSelectionDialog extends JDialog {
    private JComboBox<String> unitComboBox;
    private JLabel unitHPLabel;
    private JLabel unitAttackPower;
    private JLabel unitePaymentCostLabel;
    private JLabel unitRotationLabel;
    private JButton doneButton;
    private JButton cancelButton;

    public UnitSelectionDialog(JFrame parent) {
        super(parent, "Select Unit", true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // محتوای اصلی با GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Row 0: Label + ComboBox کنار هم
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(new JLabel("Choose Unit:"), gbc);

        gbc.gridx = 1;
        unitComboBox = new JComboBox<>(new String[]{"None", "Peasant", "Swordman", "Spearman", "Knight"});
        unitComboBox.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(unitComboBox, gbc);

        // Row 1: HP & Attack Power
        unitHPLabel = new JLabel("HP: ");
        unitAttackPower = new JLabel("Attack Power: ");

        gbc.gridy = 1;
        gbc.gridx = 0;
        contentPanel.add(unitHPLabel, gbc);

        gbc.gridx = 1;
        contentPanel.add(unitAttackPower, gbc);

        // Row 2: Payment Cost & Rotation
        unitePaymentCostLabel = new JLabel("Payment Cost: ");
        unitRotationLabel = new JLabel("Rotation: ");

        gbc.gridy = 2;
        gbc.gridx = 0;
        contentPanel.add(unitePaymentCostLabel, gbc);

        gbc.gridx = 1;
        contentPanel.add(unitRotationLabel, gbc);

        // Row 3: دکمه‌ها در یک پنل جدا
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

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        contentPanel.add(buttonPanel, gbc);

        // وسط‌چین کردن کل پنل در دیالوگ
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

    public JLabel getUnitRotationLabel() {
        return unitRotationLabel;
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Unit Selection Dialog");
        UnitSelectionDialog dialog = new UnitSelectionDialog(frame);
        dialog.setVisible(true);
    }

}
