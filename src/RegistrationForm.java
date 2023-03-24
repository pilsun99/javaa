import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JDialog{
    private JTextField tfName;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(500,420));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                registerUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = tfName.getText();
        String email = tfEmail.getText();
        String phone = tfPhone.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());

        if (name.isEmpty()    || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please enter all fields", "Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this,"Password is not correct", "Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUsertoDatabase(name, email, phone, password);
        if (user != null) {
            dispose();
        }else{
            JOptionPane.showMessageDialog(this,"Registration failed", "Try again",JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String MD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public User user;
    private User addUsertoDatabase(String name, String email, String phone, String password){
        User user = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaa", "root", "Caxauxi@9599");
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users(name, email, phone, password) VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3,phone);
            preparedStatement.setString(4,MD5(MD5(password)));
            int addedRows = preparedStatement.executeUpdate();

            if (addedRows > 0){
                user = new User();
                user.name = name;
                user.email = email;
                user.phone = phone;
                user.password = password;
            }
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        RegistrationForm registrationForm = new RegistrationForm(null);
        User user = registrationForm.user;
        if (user != null) {
            System.out.println("Success registration : " + user.name);
        }else{
            System.out.println("Error registration");
        }
    }
}
