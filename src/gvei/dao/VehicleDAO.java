package gvei.dao;

import gvei.DBHelper;
import gvei.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public boolean insertVehicle(int ownerId, String plateNo, String type, String fuel, int manufactureYear, int mileage) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "INSERT INTO vehicles(owner_id, plate_no, vehicle_type, fuel_type, manufacture_year, mileage) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, ownerId);
        ps.setString(2, plateNo);
        ps.setString(3, type);
        ps.setString(4, fuel);
        ps.setInt(5, manufactureYear);
        ps.setInt(6, mileage);
        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows > 0;
    }

    public Vehicle getVehicleById(int vehicleId) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "SELECT * FROM vehicles WHERE vehicle_id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, vehicleId);
        ResultSet rs = ps.executeQuery();
        Vehicle v = null;
        if(rs.next()){
            v = new Vehicle(
                    rs.getInt("vehicle_id"),
                    rs.getInt("owner_id"),
                    rs.getString("plate_no"),
                    rs.getString("vehicle_type"),
                    rs.getString("fuel_type"),
                    rs.getInt("manufacture_year"), // correct column
                    rs.getInt("mileage")
            );
        }
        rs.close();
        ps.close();
        c.close();
        return v;
    }

    public List<Vehicle> getVehiclesByUser(int ownerId) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "SELECT * FROM vehicles WHERE owner_id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, ownerId);
        ResultSet rs = ps.executeQuery();

        List<Vehicle> list = new ArrayList<>();
        while(rs.next()){
            Vehicle v = new Vehicle(
                    rs.getInt("vehicle_id"),
                    rs.getInt("owner_id"),
                    rs.getString("plate_no"),
                    rs.getString("vehicle_type"),
                    rs.getString("fuel_type"),
                    rs.getInt("manufacture_year"),
                    rs.getInt("mileage")
            );
            list.add(v);
        }

        rs.close();
        ps.close();
        c.close();
        return list;
    }
}
