package gvei.ui;

import gvei.User;
import gvei.dao.UserDAO;

import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends Frame {

    TextField tfEmail = new TextField(20);
    TextField tfPassword = new TextField(20);
    Label lblMsg = new Label("");

    public LoginFrame(){
        super("GVEI Login");
        setLayout(new GridLayout(5,2,5,5)); // 5 rows to include Register button

        add(new Label("Email:")); add(tfEmail);
        add(new Label("Password:"));
        tfPassword.setEchoChar('*');
        add(tfPassword);

        // Login button
        Button btnLogin = new Button("Login");
        btnLogin.addActionListener(e -> login());
        add(btnLogin);

        // Register button
        Button btnRegister = new Button("Register");
        btnRegister.addActionListener(e -> {
            new RegisterFrame().setVisible(true); // open registration form
        });
        add(btnRegister);

        add(lblMsg);

        setSize(350,250);
        setResizable(false);
        setLocationRelativeTo(null); // center on screen
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){ dispose(); }
        });
    }

    private void login(){
        String email = tfEmail.getText().trim();
        String password = tfPassword.getText().trim();

        if(email.isEmpty() || password.isEmpty()){
            lblMsg.setText("Email and password are required.");
            return;
        }

        try{
            UserDAO dao = new UserDAO();
            User user = dao.getUserByEmailAndPassword(email, password);

            if(user != null){
                if(user.getRole().equalsIgnoreCase("citizen")){
                    new CitizenDashboardFrame(user.getUserId()).setVisible(true);
                } else if(user.getRole().equalsIgnoreCase("admin")){
                    new AdminDashboardFrame().setVisible(true);
                }
                dispose();
            } else {
                lblMsg.setText("Invalid email or password.");
            }
        } catch(Exception e){
            e.printStackTrace();
            lblMsg.setText("Login error.");
        }
    }
}
