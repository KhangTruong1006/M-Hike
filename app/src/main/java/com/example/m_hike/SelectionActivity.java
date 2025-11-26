package com.example.m_hike;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Hike.Hike;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {
    RecyclerView.Adapter selectionAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_hike_for_observations);

        recyclerView = findViewById(R.id.rvHikeSelection);
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
        selectionAdapter = new SelectionAdapter(hikes,db);
        recyclerView.setAdapter(selectionAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshRecyclerView();
    }
}