package com.patikadev.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.patikadev.Helper.DBConnector;

public class Course {
    private int course_id;
    private int patika_id;
    private  int users_id;
    private String course_name;

    private  String language;
    private  Patika patika;
    private  Users educator;

    public Course(int course_id, int patika_id, int users_id, String course_name, String language) {
        this.course_id = course_id;
        this.patika_id = patika_id;
        this.users_id = users_id;
        this.course_name = course_name;
        this.language = language;
        this.patika = Patika.getFetch(patika_id);
        this.educator = Users.getFetch(users_id);
    }

    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        try {
            Statement st = DBConnector.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course");
            while (rs.next()){
                obj = new Course(rs.getInt("course_id"),rs.getInt("patika_id"),rs.getInt("users_id"),rs.getString("course_name"),rs.getString("language"));
                courseList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        try {
            Statement st = DBConnector.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course where users_id = "+user_id);
            while (rs.next()){
                obj = new Course(rs.getInt("course_id"),rs.getInt("patika_id"),rs.getInt("users_id"),rs.getString("course_name"),rs.getString("language"));
                courseList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }
    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public Users getEducator() {
        return educator;
    }

    public void setEducator(Users educator) {
        this.educator = educator;
    }

    public static  boolean add(int users_id, int patika_id, String course_name, String language){
        String query = "INSERT INTO course (users_id,patika_id,course_name,language) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setInt(1,users_id);
            pr.setInt(2,patika_id);
            pr.setString(3,course_name);
            pr.setString(4,language);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int user_id) {
        String query = "DELETE FROM course WHERE user_id = ?";

        try {
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setInt(1, user_id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
