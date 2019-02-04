package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.model.Admin;
import com.example.myapplication.model.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper = DatabaseHelper.getHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        sayHello("onCreate", tv);
        setContentView(tv);
    }

    private void sayHello(String action, TextView tv) {
        try {
            Dao<Admin, Integer> adminDao = dbHelper.getDao(Admin.class);

            List<Admin> list = adminDao.queryForAll();
            StringBuilder sb = new StringBuilder();

            // if we already have items in the database
            int maxId = 0;
            for (Admin ad : list) {
                sb.append("Hello, ").append(ad).append("\n");
                sb.append("------------------------------------------\n");
                if (ad.id > maxId) {
                    maxId = ad.id;
                }
            }

            //Admin another = new Admin(maxId + 1, "Yin");
            //Log.i("dddd", another.toString());

            //adminDao.create(another);

            tv.setText(sb.toString());
            return;
        } catch (SQLException e) {
            Log.e("ddd", "Database exception", e);
            tv.setText("Database exeption: " + e.getMessage());
            return;
        }
    }
}
