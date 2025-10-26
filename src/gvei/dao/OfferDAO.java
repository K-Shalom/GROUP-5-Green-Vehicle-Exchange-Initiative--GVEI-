package gvei.dao;

import gvei.DBHelper;
import gvei.Offer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {

    // 1️⃣ Get all offers
    public List<Offer> getAllOffers() throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "SELECT eo.*, v.plate_no FROM exchange_offers eo JOIN vehicles v ON eo.vehicle_id=v.vehicle_id";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Offer> list = new ArrayList<>();
        while(rs.next()){
            list.add(new Offer(
                    rs.getInt("offer_id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("plate_no"),
                    rs.getDouble("exchange_value"),
                    rs.getDouble("subsidy_percent"),
                    rs.getString("status")
            ));
        }
        rs.close();
        ps.close();
        c.close();
        return list;
    }

    // 2️⃣ Get pending offers
    public List<Offer> getPendingOffers() throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "SELECT eo.*, v.plate_no FROM exchange_offers eo JOIN vehicles v ON eo.vehicle_id=v.vehicle_id WHERE eo.status='Pending'";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Offer> list = new ArrayList<>();
        while(rs.next()){
            list.add(new Offer(
                    rs.getInt("offer_id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("plate_no"),
                    rs.getDouble("exchange_value"),
                    rs.getDouble("subsidy_percent"),
                    rs.getString("status")
            ));
        }
        rs.close();
        ps.close();
        c.close();
        return list;
    }

    // 3️⃣ Get approved offers
    public List<Offer> getApprovedOffers() throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "SELECT eo.*, v.plate_no FROM exchange_offers eo JOIN vehicles v ON eo.vehicle_id=v.vehicle_id WHERE eo.status='Approved'";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Offer> list = new ArrayList<>();
        while(rs.next()){
            list.add(new Offer(
                    rs.getInt("offer_id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("plate_no"),
                    rs.getDouble("exchange_value"),
                    rs.getDouble("subsidy_percent"),
                    rs.getString("status")
            ));
        }
        rs.close();
        ps.close();
        c.close();
        return list;
    }

    // 4️⃣ Get offers by user
    public List<Offer> getOffersByUser(int userId) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "SELECT eo.*, v.plate_no FROM exchange_offers eo JOIN vehicles v ON eo.vehicle_id=v.vehicle_id WHERE v.owner_id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        List<Offer> list = new ArrayList<>();
        while(rs.next()){
            list.add(new Offer(
                    rs.getInt("offer_id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("plate_no"),
                    rs.getDouble("exchange_value"),
                    rs.getDouble("subsidy_percent"),
                    rs.getString("status")
            ));
        }
        rs.close();
        ps.close();
        c.close();
        return list;
    }

    // 5️⃣ Insert a new offer
    public boolean insertOffer(int vehicleId, double exchangeValue, double subsidyPercent) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "INSERT INTO exchange_offers(vehicle_id, exchange_value, subsidy_percent, status) VALUES(?,?,?, 'Pending')";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, vehicleId);
        ps.setDouble(2, exchangeValue);
        ps.setDouble(3, subsidyPercent);
        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows > 0;
    }

    // 6️⃣ Update offer (value, subsidy, status)
    public boolean updateOffer(int offerId, double exchangeValue, double subsidyPercent, String status) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "UPDATE exchange_offers SET exchange_value=?, subsidy_percent=?, status=? WHERE offer_id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDouble(1, exchangeValue);
        ps.setDouble(2, subsidyPercent);
        ps.setString(3, status);
        ps.setInt(4, offerId);
        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows > 0;
    }

    // 7️⃣ Delete offer
    public boolean deleteOffer(int offerId) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "DELETE FROM exchange_offers WHERE offer_id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, offerId);
        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows > 0;
    }

    // 8️⃣ Approve offer
    public boolean approveOffer(int offerId) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "UPDATE exchange_offers SET status='Approved' WHERE offer_id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, offerId);
        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows > 0;
    }

    // 9️⃣ Reject offer
    public boolean rejectOffer(int offerId) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "UPDATE exchange_offers SET status='Rejected' WHERE offer_id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, offerId);
        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows > 0;
    }
}
