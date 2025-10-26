package gvei.ui;

import gvei.dao.VehicleDAO;

import java.awt.*;
import java.awt.event.*;

public class VehicleRegistrationFrame extends Frame {

    private int userId;
    TextField tfPlateNo = new TextField(10);
    Choice choiceType = new Choice();
    Choice choiceFuel = new Choice();
    TextField tfYear = new TextField(4);
    TextField tfMileage = new TextField(5);
    Label lblMsg = new Label("");

    public VehicleRegistrationFrame(int userId){
        super("Register Vehicle");
        this.userId = userId;

        setLayout(new GridLayout(7,2,5,5));

        add(new Label("Plate Number:")); add(tfPlateNo);

        add(new Label("Vehicle Type:"));
        choiceType.add("Car");
        choiceType.add("Bus");
        choiceType.add("Motorcycle");
        add(choiceType);

        add(new Label("Fuel Type:"));
        choiceFuel.add("Petrol");
        choiceFuel.add("Diesel");
        choiceFuel.add("Other");
        add(choiceFuel);

        add(new Label("Manufacture Year:")); add(tfYear);
        add(new Label("Mileage:")); add(tfMileage);

        Button btnRegister = new Button("Register Vehicle");
        btnRegister.addActionListener(e -> registerVehicle());
        add(btnRegister);

        add(lblMsg);

        setSize(400,300);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){ dispose(); }
        });
    }

    private void registerVehicle(){
        String plateNo = tfPlateNo.getText().trim();
        String type = choiceType.getSelectedItem();
        String fuel = choiceFuel.getSelectedItem();
        String strYear = tfYear.getText().trim();
        String strMileage = tfMileage.getText().trim();

        if(plateNo.isEmpty() || strYear.isEmpty() || strMileage.isEmpty()){
            lblMsg.setText("Please fill all required fields.");
            return;
        }

        try{
            int year = Integer.parseInt(strYear);
            int mileage = Integer.parseInt(strMileage);

            VehicleDAO dao = new VehicleDAO();
            boolean ok = dao.insertVehicle(userId, plateNo, type, fuel, year, mileage);

            lblMsg.setText(ok ? "Vehicle registered successfully!" : "Vehicle registration failed.");

        } catch(NumberFormatException nfe){
            lblMsg.setText("Year and Mileage must be numbers.");
        } catch(Exception ex){
            ex.printStackTrace();
            lblMsg.setText("Error registering vehicle.");
        }
    }
}
