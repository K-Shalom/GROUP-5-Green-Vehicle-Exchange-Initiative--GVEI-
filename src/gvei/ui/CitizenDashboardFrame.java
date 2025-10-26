package gvei.ui;

import java.awt.*;
import java.awt.event.*;

public class CitizenDashboardFrame extends Frame {

    int userId;

    public CitizenDashboardFrame(int userId){
        super("Citizen Dashboard");
        this.userId = userId;

        setLayout(new GridLayout(3,1,10,10));

        Label lblWelcome = new Label("Welcome Citizen (User ID: " + userId + ")");
        lblWelcome.setAlignment(Label.CENTER);
        add(lblWelcome);

        Button btnRegisterVehicle = new Button("Register Vehicle");
        btnRegisterVehicle.addActionListener(e -> new VehicleRegistrationFrame(userId).setVisible(true));
        add(btnRegisterVehicle);

        Button btnRequestOffer = new Button("Request Exchange Offer");
        btnRequestOffer.addActionListener(e -> new OfferFrame(userId).setVisible(true));
        add(btnRequestOffer);

        setSize(400,250);
        setResizable(false);
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); }});
    }
}
