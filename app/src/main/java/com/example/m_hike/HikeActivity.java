package com.example.m_hike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Hike.Hike;
import com.example.m_hike.Hike.HikeAdapter;

import java.util.ArrayList;

public class HikeActivity extends AppCompatActivity {
    RecyclerView.Adapter hikeAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hike);

        recyclerView = findViewById(R.id.rvHike);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        db = new DatabaseHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void refreshRecyclerView(){
        ArrayList<Hike> hikes = db.getHikes();
        hikeAdapter = new HikeAdapter(hikes);
        recyclerView.setAdapter(hikeAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshRecyclerView();
    }

    public void clickAddButton(View view){
        Intent intent = new Intent(this, NewHikeActivity.class);
        startActivity(intent);
    }
}