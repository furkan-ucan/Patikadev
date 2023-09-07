package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int patika_id;
    private String patika_name;

    public Patika(int patika_id, String patika_name) {
        this.patika_id = patika_id;
        this.patika_name = patika_name;
    }

    public static boolean delete(int patika_id) {
        String query = "DELETE FROM patika WHERE patika_id = ?";
        try {
            PreparedStatement pst = DBConnector.getConnection().prepareStatement(query);
            pst.setInt(1,patika_id);
            return pst.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getPatika_name() {
        return patika_name;
    }

    public void setPatika_name(String patika_name) {
        this.patika_name = patika_name;
    }

    public  static ArrayList<Patika> getList(){
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;
        try {
            Statement st = DBConnector.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patika");
            while (rs.next()){
                obj = new Patika(rs.getInt("patika_id"),rs.getString("patika_name"));
                patikaList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
            }
    return patikaList;
    }

    public static boolean add(String patika_name){
        String query = "INSERT INTO patika (patika_name) VALUES (?)";
        try {
            PreparedStatement pst = DBConnector.getConnection().prepareStatement(query);
            pst.setString(1,patika_name);
            return pst.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean update(int patika_id, String patika_name){
        String query = "UPDATE patika SET patika_name = ? WHERE patika_id = ?";
        try {
            PreparedStatement pst = DBConnector.getConnection().prepareStatement(query);
            pst.setString(1,patika_name);
            pst.setInt(2,patika_id);
            return pst.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public  static Patika getFetch(int patika_id){
        Patika obj = null;
        String query = "SELECT * FROM patika WHERE patika_id = ?";
        try {
            PreparedStatement pst = DBConnector.getConnection().prepareStatement(query);
            pst.setInt(1,patika_id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                obj = new Patika(rs.getInt("patika_id"),rs.getString("patika_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return obj;
    }


}






