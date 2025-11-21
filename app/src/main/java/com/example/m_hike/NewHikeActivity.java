package com.example.m_hike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewHikeActivity extends AppCompatActivity {

    private EditText input_name, input_location, input_date, input_length, input_description;
    private CheckBox cb_parking;
    private Spinner spinner_difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_hike);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void saveHikeDetails(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        input_name = findViewById(R.id.input_name);
        input_location = findViewById(R.id.input_location);
        input_date = findViewById(R.id.input_date);
        input_length = findViewById(R.id.input_length);
        input_description = findViewById(R.id.input_description);


        spinner_difficulty = findViewById(R.id.spinner_difficulty);

        String name = input_name.getText().toString().trim();
        String location = input_location.getText().toString().trim();
        String date = input_date.getText().toString().trim();
        double length = Double.parseDouble(input_length.getText().toString().trim());
        int parking = getCheckBoxValue();
        String difficulty = spinner_difficulty.getSelectedItem().toString();
        String description = input_description.getText().toString().trim();
        int favorite = 0;
        int completed = 0;

        Hike hike = new Hike(name,location,date,parking,length,difficulty,description,favorite,completed);
        long hikeId = dbHelper.insertHikeDetails(hike);

        Intent intent = new Intent(this,HikeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    private int getCheckBoxValue(){
        cb_parking = findViewById(R.id.cb_parking);
        if(cb_parking.isChecked()){
            return 1;
        } else {
            return 0;
        }
    }

    public void clickSaveButton(View view){
        saveHikeDetails();
    }
    private boolean isMandatoryFieldsEmpty(View view){

        return false;
    }
}