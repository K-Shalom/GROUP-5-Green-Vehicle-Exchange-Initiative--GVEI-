package gvei.ui;

import gvei.Offer;
import gvei.dao.OfferDAO;

import java.awt.*;
import java.awt.event.*;

public class EditOfferDialog extends Dialog {

    private Offer offer;
    private OfferDAO dao;
    private Runnable onUpdate; // callback to refresh dashboard

    TextField tfVehicleId = new TextField(10);
    TextField tfExchangeValue = new TextField(10);
    TextField tfSubsidy = new TextField(10);
    Choice choiceStatus = new Choice();
    Label lblMsg = new Label("");

    public EditOfferDialog(Frame parent, Offer offer, OfferDAO dao, Runnable onUpdate){
        super(parent, "Edit Offer", true);
        this.offer = offer;
        this.dao = dao;
        this.onUpdate = onUpdate;

        setLayout(new GridLayout(6,2,5,5));

        add(new Label("Vehicle ID:"));
        tfVehicleId.setText(String.valueOf(offer.getVehicleId()));
        tfVehicleId.setEditable(false); // cannot edit vehicleId
        add(tfVehicleId);

        add(new Label("Exchange Value:"));
        tfExchangeValue.setText(String.valueOf(offer.getExchangeValue()));
        add(tfExchangeValue);

        add(new Label("Subsidy Percent:"));
        tfSubsidy.setText(String.valueOf(offer.getSubsidyPercent()));
        add(tfSubsidy);

        add(new Label("Status:"));
        choiceStatus.add("Pending");
        choiceStatus.add("Approved");
        choiceStatus.add("Rejected");
        choiceStatus.select(offer.getStatus());
        add(choiceStatus);

        Button btnUpdate = new Button("Update Offer");
        btnUpdate.addActionListener(e -> updateOffer());
        add(btnUpdate);

        add(lblMsg);

        setSize(300,250);
        setResizable(false);
        setLocationRelativeTo(parent);
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); }});

        setVisible(true);
    }

    private void updateOffer(){
        try{
            double exchangeValue = Double.parseDouble(tfExchangeValue.getText().trim());
            double subsidy = Double.parseDouble(tfSubsidy.getText().trim());
            String status = choiceStatus.getSelectedItem();

            boolean ok = dao.updateOffer(offer.getOfferId(), exchangeValue, subsidy, status);
            if(ok){
                lblMsg.setText("Offer updated successfully!");
                if(onUpdate != null) onUpdate.run(); // refresh dashboard
                dispose();
            } else {
                lblMsg.setText("Update failed.");
            }
        } catch(NumberFormatException nfe){
            lblMsg.setText("Exchange value and subsidy must be numbers.");
        } catch(Exception ex){
            ex.printStackTrace();
            lblMsg.setText("Error updating offer.");
        }
    }
}
