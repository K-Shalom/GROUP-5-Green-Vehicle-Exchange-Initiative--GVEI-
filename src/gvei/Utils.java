package gvei;

public class Utils {

    // Example: estimate exchange value based on fuel type, year, mileage
    public static double estimateExchangeValue(String fuelType, int manufactureYear, int mileage){
        double baseValue = 10000; // example base value
        int age = 2025 - manufactureYear; // adjust as needed
        double depreciation = age * 500 + mileage * 0.1;
        double value = baseValue - depreciation;
        if(fuelType.equalsIgnoreCase("Diesel")) value *= 0.9;
        return Math.max(value, 1000); // minimum value
    }

    // ✅ Add this method to calculate subsidy percentage
    public static double calculateSubsidy(double exchangeValue){
        // Example: 20% subsidy of exchange value, max 2000
        double subsidy = exchangeValue * 0.2;
        return Math.min(subsidy, 2000);
    }
}
