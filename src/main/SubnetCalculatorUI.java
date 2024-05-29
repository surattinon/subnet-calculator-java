package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubnetCalculatorUI extends JFrame {

  private JTextField ipField;
  private JTextField maskField;
  private JLabel subnetIdLabel;
  private JLabel broadcastAddressLabel;

  public SubnetCalculatorUI() {
    setTitle("Subnet Calculator");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridLayout(6, 2, 10, 10));

    JLabel ipLabel = new JLabel("Enter IP address (e.g., 192.168.1.1):");
    ipField = new JTextField();

    JLabel maskLabel = new JLabel("Enter subnet mask (e.g., 255.255.255.0):");
    maskField = new JTextField();

    JButton calculateButton = new JButton("Calculate");
    calculateButton.addActionListener(new CalculateButtonListener());

    subnetIdLabel = new JLabel("Subnet ID: ");
    broadcastAddressLabel = new JLabel("Broadcast Address: ");

    add(ipLabel);
    add(ipField);
    add(maskLabel);
    add(maskField);
    add(new JLabel()); // Placeholder
    add(calculateButton);
    add(new JLabel()); // Placeholder
    add(new JLabel()); // Placeholder
    add(subnetIdLabel);
    add(broadcastAddressLabel);

    setVisible(true);
  }

  private class CalculateButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String ipAddress = ipField.getText();
      String subnetMask = maskField.getText();

      String binaryIp = ipToBinary(ipAddress);
      String binaryMask = ipToBinary(subnetMask);

      String binarySubnetId = calculateSubnetId(binaryIp, binaryMask);
      String binaryBroadcastAddress = calculateBroadcastAddress(binaryIp, binaryMask);

      String subnetId = binaryToIp(binarySubnetId);
      String broadcastAddress = binaryToIp(binaryBroadcastAddress);

      subnetIdLabel.setText("Subnet ID: " + subnetId);
      broadcastAddressLabel.setText("Broadcast Address: " + broadcastAddress);
    }
  }

  private static String ipToBinary(String ip) {
    String[] octets = ip.split("\\.");
    StringBuilder binaryIp = new StringBuilder();
    for (String octet : octets) {
      int decimal = Integer.parseInt(octet);
      String binaryString = String.format("%8s", Integer.toBinaryString(decimal)).replace(' ', '0');
      binaryIp.append(binaryString);
    }
    return binaryIp.toString();
  }

  private static String binaryToIp(String binary) {
    StringBuilder ip = new StringBuilder();
    for (int i = 0; i < binary.length(); i += 8) {
      String octet = binary.substring(i, i + 8);
      int decimal = Integer.parseInt(octet, 2);
      ip.append(decimal).append(".");
    }
    return ip.substring(0, ip.length() - 1); // Remove trailing dot
  }

  private static String calculateSubnetId(String binaryIp, String binaryMask) {
    StringBuilder binarySubnetId = new StringBuilder();
    for (int i = 0; i < binaryIp.length(); i++) {
      binarySubnetId.append(binaryIp.charAt(i) == '1' && binaryMask.charAt(i) == '1' ? '1' : '0');
    }
    return binarySubnetId.toString();
  }

  private static String calculateBroadcastAddress(String binaryIp, String binaryMask) {
    StringBuilder binaryBroadcastAddress = new StringBuilder();
    for (int i = 0; i < binaryIp.length(); i++) {
      binaryBroadcastAddress.append(binaryMask.charAt(i) == '0' ? '1' : binaryIp.charAt(i));
    }
    return binaryBroadcastAddress.toString();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new SubnetCalculatorUI());
  }
}
