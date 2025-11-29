package com.example.m_hike.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Helper.Helper;
import com.example.m_hike.Hike.Hike;
import com.example.m_hike.Hike.HikeAdapter;
import com.example.m_hike.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView.Adapter hikeAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;
    private Helper hp;

    private EditText searchInput;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.rvSearch);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        hp = new Helper();

        searchInput = findViewById(R.id.search_input);
        keyword = "";

        db = new DatabaseHelper(this);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getKeyword();
                refreshRecyclerView();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void refreshRecyclerView(){
        ArrayList<Hike> hikes = db.searchHike(keyword);
        hikeAdapter = new HikeAdapter(hikes,db);
        recyclerView.setAdapter(hikeAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getKeyword();
        refreshRecyclerView();
    }

    private void getKeyword(){
        keyword = hp.getStringFromEditText(searchInput);
    }
}