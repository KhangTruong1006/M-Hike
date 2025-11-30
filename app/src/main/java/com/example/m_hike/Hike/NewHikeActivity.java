package com.example.m_hike.Hike;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Helper.Helper;
import com.example.m_hike.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewHikeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

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

        input_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void showDatePickerDialog(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,year,month,day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year , int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String selectedDate = sdf.format(calendar.getTime());

        input_date.setText(selectedDate);
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
            hp.showMessage(this,R.string.msg_new_hike);
            return;
        }
        hp.showMessage(this,R.string.msg_mandatory_fields);
    }
    private boolean isMandatoryFieldsEmpty(View view){

        String name = input_name.getText().toString().trim();
        String location = input_location.getText().toString().trim();
        String date = input_date.getText().toString().trim();
        String length = input_length.getText().toString().trim();
        if (name.isEmpty() || location.isEmpty() || date.isEmpty()|| length.isEmpty()) {
            return true;
        }
        return false;
    }


}