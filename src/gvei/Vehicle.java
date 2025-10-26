package gvei;

public class Vehicle {
    private int vehicleId;
    private int ownerId;
    private String plateNo;
    private String vehicleType;
    private String fuelType;
    private int manufactureYear;
    private int mileage;

    public Vehicle(int vehicleId, int ownerId, String plateNo, String vehicleType,
                   String fuelType, int manufactureYear, int mileage){
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.plateNo = plateNo;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.manufactureYear = manufactureYear;
        this.mileage = mileage;
    }

    public int getVehicleId() { return vehicleId; }
    public int getOwnerId() { return ownerId; }
    public String getPlateNo() { return plateNo; }
    public String getVehicleType() { return vehicleType; }
    public String getFuelType() { return fuelType; }
    public int getManufactureYear() { return manufactureYear; }
    public int getMileage() { return mileage; }
}
