package com.example.contactlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView ContactList;
    private DatabaseConnection connection;
    private AdapterContact adapterContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection= new DatabaseConnection();
        ContactList = findViewById(R.id.contact_List);
        ContactList.setHasFixedSize(true);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,AddEditContact.class);
                startActivity(intent);
            }
        });
        loadData();

    }

    private void loadData()  {
        try {
            adapterContact = new AdapterContact(this,connection.getAllData());
            ContactList.setAdapter(adapterContact);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    protected void onResume(){
        super.onResume();
        loadData();
    }
}