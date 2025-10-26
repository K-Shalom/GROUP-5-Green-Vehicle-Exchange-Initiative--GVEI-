package gvei.ui;

import gvei.Vehicle;
import gvei.dao.VehicleDAO;
import gvei.dao.OfferDAO;
import gvei.Utils;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class OfferFrame extends Frame {

    private int userId;
    Choice choiceVehicle = new Choice();
    Label lblMsg = new Label("");

    public OfferFrame(int userId){
        super("Request Exchange Offer");
        this.userId = userId;

        setLayout(new FlowLayout());
        add(new Label("Select Vehicle:"));
        add(choiceVehicle);

        Button btnSubmit = new Button("Submit Offer");
        btnSubmit.addActionListener(e -> submitOffer());
        add(btnSubmit);
        add(lblMsg);

        setSize(450,150);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); } });

        loadVehicles();
    }

    private void loadVehicles(){
        try{
            VehicleDAO dao = new VehicleDAO();
            List<Vehicle> vehicles = dao.getVehiclesByUser(userId);
            choiceVehicle.removeAll();
            for(Vehicle v : vehicles){ choiceVehicle.add(v.getVehicleId()+" - "+v.getPlateNo()); }
        } catch(Exception e){ e.printStackTrace(); lblMsg.setText("Error loading vehicles."); }
    }

    private void submitOffer(){
        String selected = choiceVehicle.getSelectedItem();
        if(selected==null || selected.isEmpty()){ lblMsg.setText("Select a vehicle first."); return; }

        int vehicleId = Integer.parseInt(selected.split(" - ")[0]);
        try{
            VehicleDAO vDao = new VehicleDAO();
            Vehicle vehicle = vDao.getVehicleById(vehicleId);
            if(vehicle==null){ lblMsg.setText("Vehicle not found."); return; }

            double exchangeValue = Utils.estimateExchangeValue(vehicle.getFuelType(), vehicle.getManufactureYear(), vehicle.getMileage());
            double subsidy = Utils.calculateSubsidy(exchangeValue);

            OfferDAO oDao = new OfferDAO();
            boolean ok = oDao.insertOffer(vehicleId, exchangeValue, subsidy);
            lblMsg.setText(ok ? "Offer requested!" : "Failed to request offer.");
        } catch(Exception e){ e.printStackTrace(); lblMsg.setText("Error submitting offer."); }
    }
}
