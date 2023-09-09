package com.patikadev.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

public class Users {
    private int user_id;
    private String user_name;
    private String user_username;
    private String user_password;
    private String users_type;

    public Users() {}

    public Users(int user_id, String user_name, String user_username, String user_password, String users_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_password = user_password;
        this.users_type = users_type;
    }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getUser_name() { return user_name; }

    public void setUser_name(String user_name) { this.user_name = user_name; }

    public String getUser_username() { return user_username; }

    public void setUser_username(String user_username) { this.user_username = user_username; }

    public String getUser_password() { return user_password; }

    public void setUser_password(String user_password) { this.user_password = user_password; }

    public String getUsers_type() { return users_type; }

    public void setUsers_type(String users_type) { this.users_type = users_type; }

    public static ArrayList<Users> getList() {
        ArrayList<Users> user_list = new ArrayList<>();
        try {
            Statement st = DBConnector.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            Users obj;
            while(rs.next()) {
                obj = new Users();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_username(rs.getString("user_username"));
                obj.setUser_password(rs.getString("user_password"));
                obj.setUsers_type(rs.getString("users_type"));
                user_list.add(obj);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user_list;
    }

    public static boolean add(String user_name, String user_username, String user_password, String users_type) {
        String query = "INSERT INTO users (user_name, user_username, user_password, users_type) VALUES (?, ?, ?, ?)";
        Users findUser = getFetch(user_username);
        if(findUser != null){
            Helper.showMsg("Bu kullanıcı adı zaten kullanılıyor.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setString(1, user_name);
            pr.setString(2, user_username);
            pr.setString(3, user_password);
            pr.setString(4, users_type);
            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Users getFetch(String user_username){
        Users obj = null;
        String query = "SELECT * FROM users WHERE user_username = ?";
        try {
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setString(1, user_username);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new Users();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_username(rs.getString("user_username"));
                obj.setUser_password(rs.getString("user_password"));
                obj.setUsers_type(rs.getString("users_type"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public static Users getFetch(int user_id){
        Users obj = null;
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setInt(1, user_id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new Users();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_username(rs.getString("user_username"));
                obj.setUser_password(rs.getString("user_password"));
                obj.setUsers_type(rs.getString("users_type"));
            }
        } catch ( SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static boolean delete(int user_id) {
        String query = "DELETE FROM users WHERE user_id = ?";
        ArrayList<Course> courseList = Course.getListByUser(user_id);
        for (Course course : courseList) {
            Course.delete(course.getCourse_id());
        }
        try {
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setInt(1, user_id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean update(int user_id, String user_name, String user_username, String user_password, String users_type) {
        String query = "UPDATE users SET user_name = ?, user_username = ?, user_password = ?, users_type = ? WHERE user_id = ?";
        Users findUser = getFetch(user_username);
        if(findUser != null && findUser.getUser_id() != user_id && findUser.getUser_username().equals(user_username)){
            Helper.showMsg("Bu kullanıcı adı zaten kullanılıyor.");
            return false;
        }
        if(!users_type.equals("educator") && !users_type.equals("student" ) && !users_type.equals("admin")) {
        Helper.showMsg("Geçersiz kullanıcı türü.");
        return false;
    }
        try {
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setString(1, user_name);
            pr.setString(2, user_username);
            pr.setString(3, user_password);
            pr.setString(4, users_type);
            pr.setInt(5, user_id);
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public static  ArrayList<Users> searchUserList(String query){
        ArrayList<Users> user_list = new ArrayList<>();
        Users obj;
        try {
            Statement st = DBConnector.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                obj = new Users();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_username(rs.getString("user_username"));
                obj.setUser_password(rs.getString("user_password"));
                obj.setUsers_type(rs.getString("users_type"));
                user_list.add(obj);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user_list;
    }

    public static String searchQuery(String user_name, String user_username, String users_type){
        String query="SELECT * FROM users WHERE user_name LIKE '%{{user_name}}%' AND user_username LIKE '%{{user_username}}%' ";
        query = query.replace("{{user_name}}", user_name);
        query = query.replace("{{user_username}}", user_username);
        if (!users_type.isEmpty() ){
            query += "AND users_type = '{{users_type}}'";
            query = query.replace("{{users_type}}", users_type);
        }

        return query;
    }



}
