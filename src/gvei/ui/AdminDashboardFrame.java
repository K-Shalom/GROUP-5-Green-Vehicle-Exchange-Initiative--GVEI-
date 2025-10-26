package gvei.ui;

import gvei.Offer;
import gvei.dao.OfferDAO;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.util.List;

public class AdminDashboardFrame extends Frame {

    private List<Offer> offers;
    private OfferDAO dao = new OfferDAO();
    private Canvas canvas = new Canvas();
    private String filter = "All"; // All, Pending, Approved

    public AdminDashboardFrame() {
        super("Admin Dashboard");
        setLayout(new BorderLayout(10,10));

        // Top panel with buttons
        Panel topPanel = new Panel(new FlowLayout());
        Button btnAll = new Button("All Offers");
        Button btnPending = new Button("Pending Offers");
        Button btnApproved = new Button("Approved Offers");
        Button btnExport = new Button("Export Report (.txt)");

        topPanel.add(btnAll);
        topPanel.add(btnPending);
        topPanel.add(btnApproved);
        topPanel.add(btnExport);

        add(topPanel, BorderLayout.NORTH);

        // Canvas for offers
        add(canvas, BorderLayout.CENTER);

        // Button actions
        btnAll.addActionListener(e -> { filter="All"; loadOffers(); });
        btnPending.addActionListener(e -> { filter="Pending"; loadOffers(); });
        btnApproved.addActionListener(e -> { filter="Approved"; loadOffers(); });
        btnExport.addActionListener(e -> exportReport());

        setSize(950,500);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ dispose(); }});

        loadOffers();

        // Canvas click listener for Edit/Delete/Approve/Reject
        canvas.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int y = e.getY();
                int index = (y - 50)/30;
                if(index >=0 && index < offers.size()){
                    Offer o = offers.get(index);
                    int clickX = e.getX();

                    if(clickX > 700 && clickX < 770){ // Edit
                        openEditDialog(o);
                    } else if(clickX > 780 && clickX < 850){ // Delete
                        deleteOffer(o);
                    } else if(filter.equals("Pending")){
                        if(e.getButton() == MouseEvent.BUTTON1){ // Left click → approve
                            try { dao.approveOffer(o.getOfferId()); loadOffers(); } catch(Exception ex){ ex.printStackTrace(); }
                        } else if(e.getButton() == MouseEvent.BUTTON3){ // Right click → reject
                            try { dao.rejectOffer(o.getOfferId()); loadOffers(); } catch(Exception ex){ ex.printStackTrace(); }
                        }
                    }
                }
            }
        });
    }

    private void loadOffers(){
        try{
            switch(filter){
                case "Pending": offers = dao.getPendingOffers(); break;
                case "Approved": offers = dao.getApprovedOffers(); break;
                default: offers = dao.getAllOffers(); break; // all offers
            }
            canvas.repaint();
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void exportReport(){
        try{
            FileWriter fw = new FileWriter("offers_report.txt");
            for(Offer o : offers){
                fw.write(o.getOfferId() + "," + o.getPlateNo() + "," +
                        o.getExchangeValue() + "," + o.getSubsidyPercent() + "," +
                        o.getStatus() + "\n");
            }
            fw.close();
            showMessage("Report exported to offers_report.txt");
        } catch(Exception ex){ ex.printStackTrace(); }
    }

    private void openEditDialog(Offer o){
        Dialog d = new Dialog(this, "Edit Offer", true);
        d.setLayout(new GridLayout(6,2,5,5));

        TextField tfVehicle = new TextField(o.getPlateNo());
        TextField tfValue = new TextField(""+o.getExchangeValue());
        TextField tfSubsidy = new TextField(""+o.getSubsidyPercent());
        Choice choiceStatus = new Choice();
        choiceStatus.add("Pending"); choiceStatus.add("Approved"); choiceStatus.add("Rejected");
        choiceStatus.select(o.getStatus());

        d.add(new Label("Vehicle Plate:")); d.add(tfVehicle);
        d.add(new Label("Exchange Value:")); d.add(tfValue);
        d.add(new Label("Subsidy %:")); d.add(tfSubsidy);
        d.add(new Label("Status:")); d.add(choiceStatus);

        Button btnUpdate = new Button("Update");
        Button btnCancel = new Button("Cancel");

        btnUpdate.addActionListener(ev -> {
            try{
                double val = Double.parseDouble(tfValue.getText());
                double sub = Double.parseDouble(tfSubsidy.getText());
                dao.updateOffer(o.getOfferId(), val, sub, choiceStatus.getSelectedItem());
                d.dispose();
                loadOffers();
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        btnCancel.addActionListener(ev -> d.dispose());

        d.add(btnUpdate);
        d.add(btnCancel);

        d.setSize(400,300);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }

    private void deleteOffer(Offer o){
        try{
            dao.deleteOffer(o.getOfferId());
            loadOffers();
        } catch(Exception ex){ ex.printStackTrace(); }
    }

    private void showMessage(String msg){
        Dialog d = new Dialog(this, "Message", true);
        d.setLayout(new FlowLayout());
        Label l = new Label(msg);
        Button b = new Button("OK");
        b.addActionListener(e -> d.dispose());
        d.add(l); d.add(b);
        d.setSize(300,120);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }

    // Canvas painting
    {
        canvas = new Canvas(){
            public void paint(Graphics g){
                g.setFont(new Font("Arial", Font.PLAIN, 14));
                g.drawString("Admin Offers Dashboard (" + filter + ")", 10, 20);

                if(offers == null || offers.isEmpty()){
                    g.drawString("No offers to display.", 10, 50);
                    return;
                }

                int y = 50;
                for(Offer o : offers){
                    g.drawString(o.getOfferId() + " | " + o.getPlateNo() + " | " +
                            o.getExchangeValue() + " | " + o.getSubsidyPercent() + " | " + o.getStatus(), 10, y);
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(700, y-15, 70, 20); // Edit button
                    g.fillRect(780, y-15, 70, 20); // Delete button
                    g.setColor(Color.BLACK);
                    g.drawRect(700, y-15, 70, 20);
                    g.drawRect(780, y-15, 70, 20);
                    g.drawString("Edit", 715, y);
                    g.drawString("Delete", 795, y);
                    y += 30;
                }
            }
        };
    }
}
