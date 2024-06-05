import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PasswordNotesManager extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private ArrayList<Password> passwords = new ArrayList<>();
    private ArrayList<Note> notes = new ArrayList<>();

    private static final String PASSWORD_FILE = "passwords.txt";
    private static final String NOTES_FILE = "notes.txt";
    private static final Color BUTTON_COLOR = new Color(105, 96, 236); // Color code: #6960EC

    public PasswordNotesManager() {
        loadPasswords();
        loadNotes();

        setTitle("Password and Notes Manager");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Password and Notes Manager", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel);

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = createStyledButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty label for spacing
        loginPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        add(loginPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String enteredUsername = usernameField.getText();
        char[] enteredPasswordChars = passwordField.getPassword();
        String enteredPassword = new String(enteredPasswordChars);

        // For demonstration purposes, hardcoding the username and password
        if ("hii".equals(enteredUsername) && "bye".equals(enteredPassword)) {
            // Successful login, show the main application
            showMainApplication();
        } else {
            // Incorrect credentials, show an error message
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

        // Clear password field for security
        passwordField.setText("");
    }

    private void showMainApplication() {
        getContentPane().removeAll();
        setLayout(new GridLayout(4, 1));

        JButton addPasswordButton = createStyledButton("Add Password");
        addPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPassword();
            }
        });
        add(addPasswordButton);

        JButton viewPasswordsButton = createStyledButton("View Passwords");
        viewPasswordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showViewPasswords();
            }
        });
        add(viewPasswordsButton);

         JButton deletePasswordButton = new JButton("Delete Password");
    deletePasswordButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            deletePassword();
        }
    });
    setButtonSize(deletePasswordButton);
    add(deletePasswordButton, BorderLayout.SOUTH);


        JButton addNoteButton = createStyledButton("Add Note");
        addNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNote();
            }
        });
        add(addNoteButton);

        JButton viewNotesButton = createStyledButton("View Notes");
        viewNotesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showViewNotes();
            }
        });
        add(viewNotesButton);

JButton deleteNoteButton = new JButton("Delete Note");
    deleteNoteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteNote();
        }
    });
    setButtonSize(deleteNoteButton);
    add(deleteNoteButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        return button;
    }

    private void showAddPassword() {
        getContentPane().removeAll();
        setLayout(new GridLayout(4, 2));

        JLabel titleLabel = new JLabel("Add Password", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel);

        JTextField websiteField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        add(new JLabel("Website:"));
        add(websiteField);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton saveButton = createStyledButton("Save Password");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePassword(websiteField.getText(), usernameField.getText(), new String(passwordField.getPassword()));
            }
        });
        setButtonSize(saveButton);
        add(saveButton);

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainApplication();
            }
        });
        setButtonSize(backButton);
        add(backButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void showViewPasswords() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JTextArea passwordTextArea = new JTextArea();
        passwordTextArea.setEditable(false);

        for (Password password : passwords) {
            passwordTextArea.append("Website: " + password.getWebsite() + "\n");
            passwordTextArea.append("Username: " + password.getUsername() + "\n");
            passwordTextArea.append("Password: " + password.getPassword() + "\n");
            passwordTextArea.append("---------------\n");
        }

        JScrollPane scrollPane = new JScrollPane(passwordTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainApplication();
            }
        });
        setButtonSize(backButton);
        add(backButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void showAddNote() {
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 2));

        JLabel titleLabel = new JLabel("Add Note", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel);

        JTextArea noteTextArea = new JTextArea();
        add(new JScrollPane(noteTextArea));

        JButton saveButton = createStyledButton("Save Note");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNote(noteTextArea.getText());
            }
        });
        setButtonSize(saveButton);
        add(saveButton);

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainApplication();
            }
        });
        setButtonSize(backButton);
        add(backButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void showViewNotes() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JTextArea noteTextArea = new JTextArea();
        noteTextArea.setEditable(false);

        for (Note note : notes) {
            noteTextArea.append("Date/Time: " + note.getDateTime() + "\n");
            noteTextArea.append("Note: " + note.getNoteText() + "\n");
            noteTextArea.append("---------------\n");
        }

        JScrollPane scrollPane = new JScrollPane(noteTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainApplication();
            }
        });
        setButtonSize(backButton);
        add(backButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void deletePassword() {
        String websiteToDelete = JOptionPane.showInputDialog(this, "Enter the website to delete:");
        if (websiteToDelete != null) {
            passwords.removeIf(password -> password.getWebsite().equalsIgnoreCase(websiteToDelete));
            saveData(passwords, PASSWORD_FILE);
            showViewPasswords();
        }
    }

    private void deleteNote() {
        String dateTimeToDelete = JOptionPane.showInputDialog(this, "Enter the date/time of the note to delete:");
        if (dateTimeToDelete != null) {
            notes.removeIf(note -> note.getDateTime().equalsIgnoreCase(dateTimeToDelete));
            saveData(notes, NOTES_FILE);
            showViewNotes();
        }
    }

    private void setButtonSize(JButton button) {
        Dimension buttonSize = new Dimension(150, 30);
        button.setPreferredSize(buttonSize);
    }

    private void savePassword(String website, String username, String password) {
        Password newPassword = new Password(website, username, password);
        passwords.add(newPassword);
        saveData(passwords, PASSWORD_FILE);
        showMainApplication();
    }

    private void saveNote(String noteText) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = dateFormat.format(new java.util.Date());

        Note newNote = new Note(dateTime, noteText);
        notes.add(newNote);
        saveData(notes, NOTES_FILE);
        showMainApplication();
    }

    private void saveData(ArrayList<?> data, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPasswords() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PASSWORD_FILE))) {
            passwords = (ArrayList<Password>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            passwords = new ArrayList<>();
        }
    }

    private void loadNotes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOTES_FILE))) {
            notes = (ArrayList<Note>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            notes = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordNotesManager();
            }
        });
    }
}

class Password implements Serializable {
    private String website;
    private String username;
    private String password;

    public Password(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Note implements Serializable {
    private String dateTime;
    private String noteText;

    public Note(String dateTime, String noteText) {
        this.dateTime = dateTime;
        this.noteText = noteText;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getNoteText() {
        return noteText;
    }
}
