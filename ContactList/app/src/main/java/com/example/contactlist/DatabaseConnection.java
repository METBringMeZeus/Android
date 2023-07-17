package com.example.contactlist;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static Statement st;
    public static int rs;
    public static Connection conn=null;

    public static String connURL=null;

    public static Statement getSt(){
        return st;
    }
    public DatabaseConnection() {
        conn = GetConnection();
    }
    public static Connection GetConnection(){
        String IP,DB,DBUserName,passWord,port;
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        IP="192.168.30.103";
        port="51553";
        DB="DanhBa";
        DBUserName="sa";
        passWord="123";
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connURL="jdbc:jtds:sqlserver://"+IP+":"+port+";databaseName="+DB+";user="+DBUserName+";password="+passWord+"";
            conn= DriverManager.getConnection(connURL);
            st= conn.createStatement();
        }
        catch (ClassNotFoundException se) {
            throw new RuntimeException(se);
        } catch (Exception x){
            throw new RuntimeException(x);
        }
        return conn;
    }
    public static void  AddContact(String Name,String Phone,String Email,String Note){
        try {
            if(conn!=null){
                String sqlInsert="insert into Thongtin values('"+Name+"','"+Phone+"','"+Email+"','"+Note+"')";
                rs = st.executeUpdate(sqlInsert);
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ModelContact> getAllData() throws SQLException{
        List<ModelContact> contactList = new ArrayList<>();

        try {
            if(conn!=null){
                String selectQuery = "Select * from Thongtin";
                ResultSet rs= st.executeQuery(selectQuery);

                while (rs.next()) {
                    String name = rs.getString(1);
                    ModelContact modelContact = new ModelContact(name);
                    contactList.add(modelContact);
                }
                rs.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

}
