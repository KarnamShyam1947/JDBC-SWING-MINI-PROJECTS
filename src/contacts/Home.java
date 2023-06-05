package contacts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Home extends JFrame{

    private JLabel title, nameLabel, numberLabel, label;
    private JTextField nameField, numberField;
    private JButton addButton, showButton, deleteButton;
    private Font titleFont, txtFont;
    private int noOfContacts = 0;
    private JList<String> list;
    private JScrollPane scrollPane;
    private Vector<String> finalStrings;

    public Home() {
        
        titleFont = new Font("Times New Roman", Font.BOLD, 30);
        txtFont = new Font("Times New Roman", Font.BOLD, 20);
        noOfContacts = Database.getCount();
        
        UIManager.put("OptionPane.minimumSize",new Dimension(300,100));
        UIManager.put("OptionPane.messageFont", txtFont);
        UIManager.put("OptionPane.buttonFont", txtFont);

        title = new JLabel("Contact Management");
        title.setFont(titleFont);
        title.setBounds(280, 50, 500, 40);
        add(title);

        nameLabel = new JLabel("Enter person name");
        nameLabel.setFont(txtFont);
        nameLabel.setBounds(100, 120, 200, 30);
        add(nameLabel);
        
        numberLabel = new JLabel("Enter person number");
        numberLabel.setFont(txtFont);
        numberLabel.setBounds(100, 170, 200, 30);
        add(numberLabel);

        nameField = new JTextField();
        nameField.setFont(txtFont);
        nameField.setBounds(300, 120, 300, 40);
        add(nameField);
        
        numberField = new JTextField();
        numberField.setFont(txtFont);
        numberField.setBounds(300, 170, 300, 40);
        add(numberField);

        addButton = new JButton("ADD");
        addButton.setFont(txtFont);
        addButton.setBounds(100, 250, 100, 30);
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String name = nameField.getText().trim();
                String number = numberField.getText().trim();

                if(name.isEmpty() || number.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please, Enter details in all fields", 
                                                "Warning", JOptionPane.WARNING_MESSAGE);
                }

                else {
                    if(!Validator.isValidNumber(number)) {
                        JOptionPane.showMessageDialog(null, "Please, Enter an valid phone number", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        int row = Database.insertContact(name, number);
                        if(row > 0) {
                            JOptionPane.showMessageDialog(null, "Contact added successfully", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);

                            label.setText("Number of contacts in database : " + Database.getCount());

                            nameField.setText("");
                            numberField.setText("");

                            nameField.requestFocus();
                        }

                        else {
                            JOptionPane.showMessageDialog(null, "[ERROR] contact already exists with given name", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        
        showButton = new JButton("SHOW");
        showButton.setFont(txtFont);
        showButton.setBounds(300, 250, 100, 30);
        add(showButton);
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                finalStrings = Database.getDetails();

                list = new JList<>(finalStrings);
                list.setFont(txtFont);
                list.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        String value = list.getSelectedValue();

                        String[] values = value.split(" ");
                        // System.out.println("Username : " + values[1]);
                        // System.out.println("Number : " + values[3]);

                        nameField.setText(values[1]);
                        numberField.setText(values[3]);
                    }
                });

                scrollPane = new JScrollPane(list);

                JOptionPane.showMessageDialog(null, scrollPane, "List of contacts", JOptionPane.PLAIN_MESSAGE);
            }
        });

        deleteButton = new JButton("DELETE");
        deleteButton.setFont(txtFont);
        deleteButton.setBounds(500, 250, 120, 30);
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String name = nameField.getText().trim();

                if(name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please, select a contact to delete", 
                                                    "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    int op = JOptionPane.showConfirmDialog(null, "Do you want to delete " + name + " contact.....!!!", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                    // System.out.println(op);

                    if(op == 0) {
                        int row = Database.deleteContact(name);
                        label.setText("Number of contacts in database : " + Database.getCount());
                        
                        if(row > 0) {
                            JOptionPane.showMessageDialog(null, "Contact deleted successfully", 
                                                        "Success", JOptionPane.INFORMATION_MESSAGE);


                        }
                        
                        else
                        JOptionPane.showMessageDialog(null, "[ERROR] while deleting the contact", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    nameField.setText("");
                    numberField.setText("");

                    nameField.requestFocus();
                }
            }
        });

        label = new JLabel("Number of contacts in database : " + noOfContacts);
        label.setBounds(200, 320, 300, 30);
        label.setFont(txtFont);
        add(label);

        setLayout(null);
        setBounds(400, 200, 800, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Home();
    }
}
