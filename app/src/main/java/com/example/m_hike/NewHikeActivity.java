package com.example.m_hike;

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

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Helper.Helper;
import com.example.m_hike.Hike.Hike;

public class NewHikeActivity extends AppCompatActivity {

    private EditText input_name, input_location, input_date, input_length, input_description;
    private CheckBox cb_parking;
    private Spinner spinner_difficulty;
    private Helper hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_hike);

        hp = new Helper();

        input_name = findViewById(R.id.input_name);
        input_location = findViewById(R.id.input_location);
        input_date = findViewById(R.id.input_date);
        input_length = findViewById(R.id.input_length);
        input_description = findViewById(R.id.input_description);

        cb_parking = findViewById(R.id.cb_parking);
        spinner_difficulty = findViewById(R.id.spinner_difficulty);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void saveHikeDetails(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        String name = hp.getStringFromEditText(input_name);
        String location = hp.getStringFromEditText(input_location);
        String date = hp.getStringFromEditText(input_date);

        double length = Double.parseDouble(hp.getStringFromEditText(input_length));
        int parking = hp.getCheckBoxValue(cb_parking);

        String difficulty = hp.getSelectedSpinnerItem(spinner_difficulty);
        String description = hp.getStringFromEditText(input_description);

        int favorite = 0;
        int completed = 0;

        Hike hike = new Hike(name,location,date,parking,length,difficulty,description,favorite,completed);
        long hikeId = dbHelper.insertHikeDetails(hike);

        finish();
    }

    public void clickSaveButton(View view){
        if(!isMandatoryFieldsEmpty(view)){
            saveHikeDetails();
            return;
        }
        hp.showMessage(this,R.string.msg_mandatory_fields);
    }
    private boolean isMandatoryFieldsEmpty(View view){

        String[] fields = getMandatoryField();

//        0 - Name
//        1 - Location
//        2 - Date
//        3 - Length

        if (fields[0].equalsIgnoreCase("") || fields[1].equalsIgnoreCase("") || fields[3].equalsIgnoreCase("") || fields[4].equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    private String[] getMandatoryField(){
        String name = hp.getStringFromEditText(input_location);
        String location = hp.getStringFromEditText(input_location);
        String date = hp.getStringFromEditText(input_date);
        String length = hp.getStringFromEditText(input_length);

        return new String[] {name,location,date,length};
    }
}