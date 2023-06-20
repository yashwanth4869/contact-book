import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

class Contact implements Comparable<Contact>, Serializable {
    String number;
    String name, email, address;

    Contact(String number, String name, String email, String address) {
        this.number = number;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return number + "," + name + "," + email + "," + address;
    }

    @Override
    public int compareTo(Contact o) {
        return name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}

public class ContactGUI {
    JFrame f, f1;
    File file = new File("contact_details.txt");
    ObjectOutputStream fileWrite = null;
    ObjectInputStream fileRead = null;
    TreeSet<Contact> tree = new TreeSet<>();

    ContactGUI() {
        f = new JFrame();
        f.setSize(500, 600);

        JLabel title = new JLabel();
        title.setText("ADDRESS BOOK");
        title.setFont(new Font("Comic Sans ms", Font.BOLD, 25));
        title.setBounds(130, 10, 300, 50);
        f.add(title);

        JButton addButton = new JButton("ADD");
        addButton.setBounds(40, 80, 100, 40);
        f.add(addButton);
        addButton.setFocusable(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(170, 80, 100, 40);
        f.add(deleteButton);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        JButton viewButton = new JButton("ALL CONTACTS");
        viewButton.setBounds(300, 80, 140, 40);
        f.add(viewButton);
        viewButton.setFocusable(false);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContacts();
            }
        });

        JLabel searchlabel = new JLabel();
        searchlabel.setText("SEARCH");
        searchlabel.setFont(new Font("Sans-Sherif", Font.BOLD, 18));
        searchlabel.setBounds(85, 190, 100, 50);
        f.add(searchlabel);
        JTextField searchbar = new JTextField();
        searchbar.setBounds(80, 230, 200, 30);
        f.add(searchbar);
        ImageIcon img = new ImageIcon("searchicon1.png");
        JButton search = new JButton("");
        search.setBounds(285, 230, 60, 30);
        search.setIcon(img);
        f.add(search);
        search.setFocusable(false);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = searchbar.getText();
                if (s.length() == 0) {
                    JOptionPane.showMessageDialog(f, "Enter the name or phone number", "DELETE CONTACT",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (file.isFile()) {
                        try {
                            fileRead = new ObjectInputStream(new FileInputStream(file));
                            tree = (TreeSet<Contact>) fileRead.readObject();
                            fileRead.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Iterator<Contact> it = tree.iterator();
                        boolean found = false;
                        String message = "Contact does not exist...";
                        while (it.hasNext()) {
                            Contact c = it.next();
                            if (s.toLowerCase().equals(c.name.toLowerCase(Locale.ROOT)) || s.equals(c.number)) {
                                message = "Name : " + c.name + "\nNumber : " + c.number + "\nEmail : " + c.email
                                        + "\nAddress : " + c.address;
                                found = true;
                                break;
                            }
                        }
                        JOptionPane.showMessageDialog(f, message, "SEARCH", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(new Color(178, 221, 225));
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addContact() {
        f1 = new JFrame();
        f1.setTitle("ADD CONTACT");
        f1.setSize(500, 600);

        JLabel title_add = new JLabel();
        title_add.setText("ADD CONTACT");
        title_add.setFont(new Font("Comic Sans ms", Font.BOLD, 25));
        title_add.setBounds(130, 10, 300, 50);
        f1.add(title_add);

        JLabel nameLable = new JLabel();
        nameLable.setText("NAME       : ");
        nameLable.setFont(new Font("Sans-Sherif", Font.BOLD, 15));
        nameLable.setBounds(100, 78, 100, 50);
        f1.add(nameLable);
        JTextField name = new JTextField();
        name.setBounds(220, 90, 200, 28);
        f1.add(name);

        JLabel numLable = new JLabel();
        numLable.setText("NUMBER     : ");
        numLable.setFont(new Font("Sans-Sherif", Font.BOLD, 15));
        numLable.setBounds(86, 128, 100, 50);
        f1.add(numLable);
        JTextField number = new JTextField();
        number.setBounds(220, 140, 200, 28);
        f1.add(number);

        JLabel emailLable = new JLabel();
        emailLable.setText("EMAIL      : ");
        emailLable.setFont(new Font("Sans-Sherif", Font.BOLD, 15));
        emailLable.setBounds(100, 178, 100, 50);
        f1.add(emailLable);
        JTextField email = new JTextField();
        email.setBounds(220, 190, 200, 28);
        f1.add(email);

        JLabel addressLable = new JLabel();
        addressLable.setText("ADDRESS    : ");
        addressLable.setFont(new Font("Sans-Sherif", Font.BOLD, 15));
        addressLable.setBounds(85, 228, 100, 50);
        f1.add(addressLable);
        JTextField address = new JTextField();
        address.setBounds(220, 240, 200, 28);
        f1.add(address);

        JButton saveButton = new JButton("SAVE");
        saveButton.setBounds(170, 400, 100, 40);
        f1.add(saveButton);
        saveButton.setFocusable(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().length() == 0 || number.getText().length() == 0) {
                    JOptionPane.showMessageDialog(f1, "Enter name and phone number..", "Warning", 2);
                } else if (number.getText().length() != 10) {
                    JOptionPane.showMessageDialog(f1, "Enter 10 Digit Number.", "Warning", 2);
                } else {
                    String varnum = number.getText();
                    String varname = name.getText();
                    String varemail = email.getText();
                    String varaddress = address.getText();
                    tree.add(new Contact(varnum, varname, varemail, varaddress));
                    try {
                        fileWrite = new ObjectOutputStream(new FileOutputStream(file));
                        fileWrite.writeObject(tree);
                        JOptionPane.showMessageDialog(f1, "Successfully added", "ADD CONTACT",
                                JOptionPane.INFORMATION_MESSAGE);
                        fileWrite.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    f1.dispose();
                }
            }
        });

        f1.setLayout(null);
        f1.setVisible(true);
        f1.setLocationRelativeTo(null);
        f1.getContentPane().setBackground(new Color(178, 221, 225));
        f1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f1.dispose();
            }
        });
    }

    private void deleteContact() {
        f1 = new JFrame();
        f1.setTitle("DELETE CONTACT");
        f1.setSize(400, 300);

        JLabel nameLable = new JLabel();
        nameLable.setText("NAME       : ");
        nameLable.setFont(new Font("Sans-Sherif", Font.BOLD, 15));
        nameLable.setBounds(50, 58, 100, 50);
        f1.add(nameLable);
        JTextField name = new JTextField();
        name.setBounds(160, 70, 200, 28);
        f1.add(name);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(150, 140, 100, 40);
        f1.add(deleteButton);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = name.getText();
                if (file.isFile()) {
                    try {
                        fileRead = new ObjectInputStream(new FileInputStream(file));
                        tree = (TreeSet<Contact>) fileRead.readObject();
                        fileRead.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Iterator<Contact> it = tree.iterator();
                    boolean found = false;
                    while (it.hasNext()) {
                        Contact c = it.next();
                        if (s.toLowerCase().equals(c.name.toLowerCase(Locale.ROOT))) {
                            it.remove();
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        JOptionPane.showMessageDialog(f1, "Successfully deleted.", "DELETE CONTACT",
                                JOptionPane.INFORMATION_MESSAGE);
                        try {
                            fileWrite = new ObjectOutputStream(new FileOutputStream(file));
                            fileWrite.writeObject(tree);
                            fileWrite.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(f1, "Number not found.", "DELETE CONTACT",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        f1.setLayout(null);
        f1.setVisible(true);
        f1.setLocationRelativeTo(null);
        f1.getContentPane().setBackground(new Color(178, 221, 225));
        f1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f1.dispose();
            }
        });
    }

    private void showContacts() {
        f1 = new JFrame("ALL CONTACTS");
        f1.setSize(500, 600);

        JLabel title_add = new JLabel();
        title_add.setText("ALL CONTACTS");
        title_add.setFont(new Font("Comic Sans ms", Font.BOLD, 25));
        title_add.setBounds(130, 10, 300, 50);
        f1.add(title_add);

        if (file.isFile()) {
            try {
                fileRead = new ObjectInputStream(new FileInputStream(file));
                tree = (TreeSet<Contact>) fileRead.readObject();
                fileRead.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Iterator<Contact> it = tree.iterator();
            String[] title = { "NAME", "PH NO.", "EMAIL", "ADDRESS" };
            Object[][] allcontacts = new String[tree.size()][4];
            int i = 0;
            while (it.hasNext()) {
                Contact c = it.next();
                allcontacts[i][0] = c.name;
                allcontacts[i][1] = c.number;
                allcontacts[i][2] = c.email;
                allcontacts[i][3] = c.address;
                i++;
            }
            DefaultTableModel jt = new DefaultTableModel(allcontacts, title) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable tb = new JTable(jt);
            // tb.setBounds(30, 100, 300, 400);
            tb.setPreferredScrollableViewportSize(new Dimension(400, 400));
            tb.setFillsViewportHeight(true);
            tb.setCellSelectionEnabled(false);
            JScrollPane sp = new JScrollPane(tb);
            sp.setAlignmentX(50);
            sp.setAlignmentY(100);
            // f1.ad(sp);
            f1.add(sp);
        }

        f1.setLayout(new FlowLayout());
        f1.setVisible(true);
        f1.setLocationRelativeTo(null);
        f1.getContentPane().setBackground(new Color(178, 221, 225));
        f1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f1.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new ContactGUI();
    }
}
