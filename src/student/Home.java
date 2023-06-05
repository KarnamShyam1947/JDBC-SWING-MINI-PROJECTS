package student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame{

    private JLabel title;
    private Font titleFont, txtFont;
    private JLabel nameLabel, phoneLabel, emailLabel, idLabel;
    private JTextField nameField, phoneField, emailField, idField;
    private JButton addButton, deleteButton, updateButton, clearButton;
    private JPanel panel;
    private JTable table;
    private JTableHeader tableHeader;
    private DefaultTableModel tableModel;

    public Home() {
        
        titleFont = new Font("Lucida Bright", Font.BOLD, 30);
        txtFont = new Font("Times New Roman", Font.PLAIN, 25);
        
        UIManager.put("OptionPane.minimumSize", new Dimension(300, 120));
        UIManager.put("OptionPane.messageFont", txtFont);
        UIManager.put("OptionPane.buttonFont", txtFont);

        table = new JTable();
        
        title = new JLabel("Student Record Management");
        title.setBounds(500, 30, 600, 50);
        title.setFont(titleFont);
        add(title);

        idLabel = new JLabel("Enter student id");
        idLabel.setBounds(50, 100, 250, 30);
        idLabel.setFont(txtFont);
        add(idLabel);

        nameLabel = new JLabel("Enter student name");
        nameLabel.setBounds(50, 170, 250, 30);
        nameLabel.setFont(txtFont);
        add(nameLabel);
        
        emailLabel = new JLabel("Enter student email");
        emailLabel.setBounds(50, 240, 250, 30);
        emailLabel.setFont(txtFont);
        add(emailLabel);
        
        phoneLabel = new JLabel("Enter phone number");
        phoneLabel.setBounds(50, 310, 250, 30);
        phoneLabel.setFont(txtFont);
        add(phoneLabel);

        idField = new JTextField();
        idField.setBounds(300, 100, 300, 40);
        idField.setFont(txtFont);
        add(idField);

        nameField = new JTextField();
        nameField.setBounds(300, 170, 300, 40);
        nameField.setFont(txtFont);
        add(nameField);
        
        emailField = new JTextField();
        emailField.setBounds(300, 240, 300, 40);
        emailField.setFont(txtFont);
        add(emailField);
        
        phoneField = new JTextField();
        phoneField.setBounds(300, 310, 300, 40);
        phoneField.setFont(txtFont);
        add(phoneField);

        addButton = new JButton("ADD");
        addButton.setBounds(50, 400, 150, 40);
        addButton.setFont(txtFont);
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                if(id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter details in all fields", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    if(!Validator.isValidPhoneNumber(phone)) {
                        JOptionPane.showMessageDialog(null, "Please enter an valid phone number", 
                                                "Error", JOptionPane.ERROR_MESSAGE);

                    }

                    else if(!Validator.isValidEmail(email)) {
                        JOptionPane.showMessageDialog(null, "Please enter an valid email", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    else {
                        int row = Database.insertRecord(id, name, email, phone);
                        if(row > 0) {
                            JOptionPane.showMessageDialog(null, "Record inserted successfully", 
                                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                        }

                        else {
                            JOptionPane.showMessageDialog(null, "[Error] Already a record exists with same student id", 
                                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        Database.loadTable(tableModel);
                        idField.setText("");
                        nameField.setText("");
                        emailField.setText("");
                        phoneField.setText("");

                        idField.requestFocus();
                        idField.setEditable(true);
                    }
                }
            }
        });

        deleteButton = new JButton("DELETE");
        deleteButton.setBounds(250, 400, 150, 40);
        deleteButton.setFont(txtFont);
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String id = idField.getText().trim();
                
                if(id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a record", 
                                            "Warning", JOptionPane.WARNING_MESSAGE);
                }

                else {
                    int op = JOptionPane.showConfirmDialog(null, "Do you want to delete the record ???", "Confirmation", 
                                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if(op == 0) {
                        int row = Database.deleteRecord(id);
                        Database.loadTable(tableModel);

                        if(row > 0) 
                            JOptionPane.showMessageDialog(null, "Record deleted successfully", 
                                                    "Success", JOptionPane.INFORMATION_MESSAGE);

                        else 
                            JOptionPane.showMessageDialog(null, "Failed to delete the record", 
                                                    "Failed", JOptionPane.ERROR_MESSAGE);

                        idField.setText("");
                        nameField.setText("");
                        emailField.setText("");
                        phoneField.setText("");

                        idField.requestFocus();
                        idField.setEditable(true);
                    }
                }
                
            }
        });
        
        updateButton = new JButton("UPDATE");
        updateButton.setBounds(450, 400, 150, 40);
        updateButton.setFont(txtFont);
        add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                if(id.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a record", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    int row = Database.updateRecord(id, name, email, phone);
                    Database.loadTable(tableModel);

                    if(row > 0) {
                        JOptionPane.showMessageDialog(null, "Record deleted successfully", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    else {
                        JOptionPane.showMessageDialog(null, "Error While deleting record", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                idField.setText("");
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");

                idField.requestFocus();
                idField.setEditable(true);
            }
        });
        
        clearButton = new JButton("CLEAR");
        clearButton.setBounds(250, 470, 150, 40);
        clearButton.setFont(txtFont);
        add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");

                idField.requestFocus();
                idField.setEditable(true);
            }
        }); 

        // table
        panel = new JPanel(new BorderLayout());
        panel.setBounds(650, 100, 700, 420);

        table.setFont(txtFont);

        tableModel = (DefaultTableModel) table.getModel();
        Database.loadTable(tableModel);

        tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 18));

        table.setRowHeight(40);
        
        panel.add(new JScrollPane(table));
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                int selectedIndex = table.getSelectedRow();

                String id = (String) table.getValueAt(selectedIndex, 0);
                String name = (String) table.getValueAt(selectedIndex, 1);
                String email = (String) table.getValueAt(selectedIndex, 2);
                String phone = (String) table.getValueAt(selectedIndex, 3);

                idField.setText(id);
                nameField.setText(name);
                emailField.setText(email);
                phoneField.setText(phone);

                idField.setEditable(false);
            }
        });

        add(panel);

        setTitle("CRUD operations using JDBC");
        setLayout(null);
        setBounds(300, 100, 1400, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setColumnWidth(JTable table, int noOfCols, int size) {
        for (int i = 0; i < noOfCols; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(size);
        }
    }
}
