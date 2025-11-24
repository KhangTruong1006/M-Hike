package com.example.m_hike;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Hike.Hike;

public class EditHikeActivity extends AppCompatActivity {
    private EditText input_name, input_location, input_date, input_length, input_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hike);

        DatabaseHelper db = new DatabaseHelper(this);
        Intent intent = getIntent();

        int id = intent.getIntExtra("HIKE_ID",0);
        Hike hike = db.findHikeById(id);
        input_name = findViewById(R.id.input_edit_name);
        input_location = findViewById(R.id.input_edit_location);
        input_date = findViewById(R.id.input_edit_date);
        input_length=findViewById(R.id.input_edit_length);
        input_description = findViewById(R.id.input_edit_description);


        input_name.setText(hike.getName());
        input_location.setText(hike.getLocation());
        input_date.setText(hike.getDate());
        input_length.setText(String.format("%s",hike.getLength()));
        input_description.setText(hike.getDescription());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}