import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
  private JTextField display;
  private double num1 = 0, num2 = 0, result = 0;
  private String operator = "";
  private boolean isOperatorClicked = false;

  public Calculator() {
    setTitle("Calculator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 500);
    setLocationRelativeTo(null);
    setResizable(false);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(10, 10));
    mainPanel.setBackground(new Color(240, 240, 240));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Display Panel
    display = new JTextField();
    display.setFont(new Font("Arial", Font.BOLD, 28));
    display.setHorizontalAlignment(JTextField.RIGHT);
    display.setText("0");
    display.setEditable(false);
    display.setBackground(new Color(50, 50, 50));
    display.setForeground(Color.WHITE);
    display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    mainPanel.add(display, BorderLayout.NORTH);

    // Buttons Panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
    buttonPanel.setBackground(new Color(240, 240, 240));

    String[] buttons = {
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "−",
        "0", ".", "=", "+",
        "C", "←", "(", ")"
    };

    for (String btn : buttons) {
      JButton button = createButton(btn);
      buttonPanel.add(button);
    }

    mainPanel.add(buttonPanel, BorderLayout.CENTER);
    add(mainPanel);
    setVisible(true);
  }

  private JButton createButton(String label) {
    JButton button = new JButton(label);
    button.setFont(new Font("Arial", Font.BOLD, 20));
    button.setFocusPainted(false);
    button.addActionListener(this);

    if (label.matches("[÷×−+]")) {
      button.setBackground(new Color(255, 140, 0));
      button.setForeground(Color.WHITE);
    } else if (label.equals("=")) {
      button.setBackground(new Color(34, 177, 76));
      button.setForeground(Color.WHITE);
    } else if (label.equals("C")) {
      button.setBackground(new Color(220, 53, 69));
      button.setForeground(Color.WHITE);
    } else if (label.equals("←")) {
      button.setBackground(new Color(220, 53, 69));
      button.setForeground(Color.WHITE);
    } else {
      button.setBackground(new Color(70, 70, 70));
      button.setForeground(Color.WHITE);
    }

    button.setOpaque(true);
    button.setBorder(BorderFactory.createRaisedBevelBorder());

    return button;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (command.equals("C")) {
      display.setText("0");
      num1 = num2 = result = 0;
      operator = "";
      isOperatorClicked = false;
    } else if (command.equals("←")) {
      String text = display.getText();
      if (text.length() > 1) {
        display.setText(text.substring(0, text.length() - 1));
      } else {
        display.setText("0");
      }
    } else if (command.matches("[0-9]")) {
      if (display.getText().equals("0")) {
        display.setText(command);
      } else {
        display.setText(display.getText() + command);
      }
      isOperatorClicked = false;
    } else if (command.equals(".")) {
      if (!display.getText().contains(".")) {
        display.setText(display.getText() + ".");
      }
    } else if (command.matches("[÷×−+]")) {
      if (!isOperatorClicked) {
        num1 = Double.parseDouble(display.getText());
      }
      operator = command;
      display.setText("0");
      isOperatorClicked = true;
    } else if (command.equals("=")) {
      num2 = Double.parseDouble(display.getText());
      result = performOperation(num1, num2, operator);
      display.setText(formatResult(result));
      operator = "";
      isOperatorClicked = true;
    }
  }

  private double performOperation(double a, double b, String op) {
    switch (op) {
      case "+" -> {
        return a + b;
      }
      case "−" -> {
        return a - b;
      }
      case "×" -> {
        return a * b;
      }
      case "÷" -> {
        return b != 0 ? a / b : 0;
      }
      default -> {
        return b;
      }
    }
  }

  private String formatResult(double value) {
    if (value == (long) value) {
      return String.format("%d", (long) value);
    } else {
      return String.format("%.6f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(Calculator::new);
  }
}
