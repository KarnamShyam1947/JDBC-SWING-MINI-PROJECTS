package login;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

public class Login extends JFrame{

    private JLabel title, userLabel, passwordLabel, label;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton, addButton, showButton;
    private Font titleFont, txtFont;
    private JRadioButton showPassword;
    private int noOfRecords = 0;
    private Vector<String> userNames;
    private JScrollPane scrollPane;
    private JList<String> list;

    public Login() {
        setLayout(null);
        noOfRecords = Database.getNoOfRecords();
        
        UIManager.put("OptionPane.minimumSize",new Dimension(200,100));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 20));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 15));

        userNames = new Vector<>();

        titleFont = new Font("Verdana", Font.BOLD, 35);
        txtFont = new Font("Lucida Sans", Font.PLAIN, 20);

        title = new JLabel("Login");
        title.setFont(titleFont);
        title.setBounds(350, 50, 500, 40);
        add(title);

        userLabel = new JLabel("Enter user name");
        userLabel.setFont(txtFont);
        userLabel.setBounds(100, 150, 200, 30);
        add(userLabel);
        
        passwordLabel = new JLabel("Enter password ");
        passwordLabel.setFont(txtFont);
        passwordLabel.setBounds(100, 220, 200, 30);
        add(passwordLabel);

        userField = new JTextField();
        userField.setBounds(300, 150, 300, 40);
        userField.setFont(txtFont);
        add(userField);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(300, 220, 300, 40);
        passwordField.setFont(txtFont);
        passwordField.setEchoChar('*');
        add(passwordField);

        showPassword = new JRadioButton("Show Password");
        showPassword.setFont(txtFont);
        showPassword.setBounds(500, 270, 200, 30);
        add(showPassword);
        showPassword.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if(showPassword.isSelected()) {
                    passwordField.setEchoChar((char)0);
                }
                else{
                    passwordField.setEchoChar('*');
                }
            }
        });

        loginButton = new JButton("LOGIN");
        loginButton.setFont(txtFont);
        loginButton.setBounds(200, 320, 100, 40);
        add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                
                if(username.isEmpty() || password.isEmpty()) 
                JOptionPane.showMessageDialog(null, "Please enter details in all fields", 
                "Error", JOptionPane.ERROR_MESSAGE);    
                
                else {
                    String encodedPassword = Utils.encodePassword(password);

                    if(Database.isValidUser(username, encodedPassword)) {
                        JOptionPane.showMessageDialog(null, "Successfully logged in", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    else {
                        JOptionPane.showMessageDialog(null, "[ERROR] Invalid username or Password", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                userField.setText("");
                passwordField.setText("");

                userField.requestFocus();
            }
        });
        
        addButton = new JButton("ADD");
        addButton.setFont(txtFont);
        addButton.setBounds(400, 320, 100, 40);
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String username = userField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if(username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter details in all fields", 
                                            "Error", JOptionPane.ERROR_MESSAGE);    
                }

                else {
                    String encodedPassword = Utils.encodePassword(password);

                    int row = Database.insertUser(username, encodedPassword);
                    
                    noOfRecords = Database.getNoOfRecords();
                    label.setText(String.format("Number of Users : %d.", noOfRecords));

                    if(row > 0)
                        JOptionPane.showMessageDialog(null, "user added successfully", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);

                    else
                        JOptionPane.showMessageDialog(null, "[Error] User already exists", 
                                            "Error", JOptionPane.WARNING_MESSAGE);


                    userField.setText("");
                    passwordField.setText("");

                    userField.requestFocus();
                }
            }
        });

        label = new JLabel(String.format("Number of Users : %d.", noOfRecords));
        label.setBounds(200, 380, 200, 40);
        label.setFont(txtFont);
        add(label);

        showButton = new JButton("SHOW");
        showButton.setBounds(600, 320, 100, 40);
        showButton.setFont(txtFont);
        add(showButton);
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Database.updateUserNames(userNames);

                list = new JList<>(userNames);
                list.setFont(txtFont);

                scrollPane = new JScrollPane(list);

                JOptionPane.showMessageDialog(null, scrollPane, "List of Users",
                                             JOptionPane.PLAIN_MESSAGE);
            }
        });

        setBounds(400, 200, 800, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Login();
    }
}
