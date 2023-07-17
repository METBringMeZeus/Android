package com.example.contactlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactDetailsss extends AppCompatActivity {
    private TextView NameStartDetail, NameDetail, PhoneDetail, EmailDetail,NoteDetail;
    private String name;
    private DatabaseConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detailsss);
        connection= new DatabaseConnection();

        Intent intent= getIntent();
        name=intent.getStringExtra("ContactName");
        NameStartDetail=findViewById(R.id.Name_Start_Detail);
        NameDetail=findViewById(R.id.Name_detail);
        PhoneDetail=findViewById(R.id.Phone_detail);
        EmailDetail=findViewById(R.id.Email_detail);
        NoteDetail=findViewById(R.id.Address_detail);

        try {
            LoadDataByName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadDataByName() throws SQLException {
        String query = "SELECT * FROM Thongtin WHERE Name = '" + name + "'";
        Statement st= DatabaseConnection.getSt();

        ResultSet rs;
        rs = st.executeQuery(query);

        if (rs.next()) {
            String nameDt = rs.getString(1);
            String phoneDt = rs.getString(2);
            String emailDt = rs.getString(3);
            String noteDt = rs.getString(4);

            NameStartDetail.setText(nameDt);
            NameDetail.setText(nameDt);
            PhoneDetail.setText(phoneDt);
            EmailDetail.setText(emailDt);
            NoteDetail.setText(noteDt);
        }

    }
}