/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Beans.Country;
import Beans.Location;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author DTI
 */
public class LocationDao extends DaoBase {

    public ArrayList<Location> listar() {

        ArrayList<Location> lista = new ArrayList<>();

        try (Connection conn = this.getConection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM locations left join countries c on locations.country_id = c.country_id")) {

            while (rs.next()) {
                Location location = new Location();
                location.setLocationId(rs.getInt(1));
                location.setStreetAddress(rs.getString(2));
                location.setPostalCode(rs.getString(3));
                location.setCity(rs.getString(4));
                location.setStateProvince(rs.getString(5));

                Country c = new Country();
                c.setCountryId(rs.getString("c.country_id"));
                c.setCountryName(rs.getString("country_name"));
                location.setCountry(c);

                lista.add(location);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void crear(int locationID, String streetAddress, String postalCode, String city, String stateProvince, String countryId) {

        try (Connection conn = this.getConection();) {
            String sql = "INSERT INTO locations (`location_id`, `street_address`, `postal_code`, `city`, `state_province`, `country_id`) "
                    + "VALUES (?,?,?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, locationID);
                pstmt.setString(2, streetAddress);
                pstmt.setString(3, postalCode);
                pstmt.setString(4, city);
                pstmt.setString(5, stateProvince);
                pstmt.setString(6, countryId);
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Location obtener(int locationId) {

        Location location = null;

        String sql = "SELECT * FROM locations left join countries c on locations.country_id = c.country_id" +
                " WHERE location_id = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, locationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    location = new Location();
                    location.setLocationId(rs.getInt(1));
                    location.setStreetAddress(rs.getString(2));
                    location.setPostalCode(rs.getString(3));
                    location.setCity(rs.getString(4));
                    location.setStateProvince(rs.getString(5));

                    Country c = new Country();
                    c.setCountryId(rs.getString("c.country_id"));
                    c.setCountryName(rs.getString("country_name"));
                    location.setCountry(c);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return location;
    }

    public void actualizar(int locationID, String streetAddress, String postalCode, String city, String stateProvince, String countryId) {
        try {
            try (Connection conn = this.getConection();) {
                String sql = "UPDATE locations SET street_address = ?, postal_code = ?, city = ?, state_province = ?, country_id = ? "
                        + "WHERE location_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, streetAddress);
                    pstmt.setString(2, postalCode);
                    pstmt.setString(3, city);
                    pstmt.setString(4, stateProvince);
                    pstmt.setString(5, countryId);
                    pstmt.setInt(6, locationID);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void borrar(int locationID) {

        String sql = "DELETE FROM locations WHERE location_id = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, locationID);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
