package gvei.ui;

import gvei.dao.UserDAO;

import java.awt.*;
import java.awt.event.*;

public class RegisterFrame extends Frame {

    TextField tfName = new TextField(20);
    TextField tfEmail = new TextField(20);
    TextField tfPassword = new TextField(20);
    Label lblMsg = new Label("");

    public RegisterFrame(){
        super("GVEI Registration");
        setLayout(new GridLayout(5,2,5,5));

        add(new Label("Name:")); add(tfName);
        add(new Label("Email:")); add(tfEmail);
        add(new Label("Password:"));
        tfPassword.setEchoChar('*');
        add(tfPassword);

        Button btnRegister = new Button("Register");
        btnRegister.addActionListener(e -> register());
        add(btnRegister);

        add(lblMsg);

        setSize(350,250);
        setResizable(false);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){ dispose(); }
        });
    }

    private void register() {
        String name = tfName.getText().trim();
        String email = tfEmail.getText().trim();
        String password = tfPassword.getText().trim();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            lblMsg.setText("All fields are required!");
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            boolean ok = dao.register(name, email, password, "citizen"); // default role = citizen
            if(ok){
                lblMsg.setText("Registration successful! You can now login.");
            } else {
                lblMsg.setText("Registration failed!");
            }
        } catch(Exception e){
            e.printStackTrace();
            lblMsg.setText("Error occurred during registration.");
        }
    }
}
