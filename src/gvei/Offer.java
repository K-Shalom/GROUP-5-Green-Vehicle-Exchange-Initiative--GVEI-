package gvei;

public class Offer {
    private int offerId;
    private int vehicleId;
    private String plateNo;        // <-- yongewe
    private double exchangeValue;
    private double subsidyPercent;
    private String status;

    public Offer(int offerId, int vehicleId, String plateNo, double exchangeValue, double subsidyPercent, String status) {
        this.offerId = offerId;
        this.vehicleId = vehicleId;
        this.plateNo = plateNo;
        this.exchangeValue = exchangeValue;
        this.subsidyPercent = subsidyPercent;
        this.status = status;
    }

    public int getOfferId() { return offerId; }
    public int getVehicleId() { return vehicleId; }
    public String getPlateNo() { return plateNo; }      // <-- getter
    public double getExchangeValue() { return exchangeValue; }
    public double getSubsidyPercent() { return subsidyPercent; }
    public String getStatus() { return status; }

    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }  // <-- setter
    public void setExchangeValue(double exchangeValue) { this.exchangeValue = exchangeValue; }
    public void setSubsidyPercent(double subsidyPercent) { this.subsidyPercent = subsidyPercent; }
    public void setStatus(String status) { this.status = status; }
}
