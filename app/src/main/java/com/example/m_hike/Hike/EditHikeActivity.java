package com.example.m_hike.Hike;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditHikeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseHelper db;
    private Hike hike;
    private EditText input_name, input_location, input_date, input_length, input_description;
    private CheckBox cbParking, cbCompletion;
    private Spinner sp_difficulty;
    private Helper helper;
    private int id;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hike);

        db = new DatabaseHelper(this);
        helper = new Helper();
        Intent intent = getIntent();

        id = intent.getIntExtra("HIKE_ID",0);
        hike = db.findHikeById(id);

        input_name = findViewById(R.id.input_edit_name);
        input_location = findViewById(R.id.input_edit_location);
        input_date = findViewById(R.id.input_edit_date);
        input_length= findViewById(R.id.input_edit_length);
        input_description = findViewById(R.id.input_edit_description);
        sp_difficulty = findViewById(R.id.spinner_edit_difficulty);
        cbCompletion = findViewById(R.id.cb_edit_completion);
        cbParking = findViewById(R.id.cb_edit_parking);


        if(hike != null){
            input_name.setText(hike.getName());
            input_location.setText(hike.getLocation());
            input_date.setText(hike.getDate());
            input_length.setText(String.format("%s",hike.getLength()));
            input_description.setText(hike.getDescription());

            cbParking.setChecked(hike.getParking() == 1);
            cbCompletion.setChecked(hike.getCompleted() == 1);
            setSpinnerToValue(sp_difficulty, hike.getDifficulty());

            updateCalendarWithStoredDate();
        }

        input_date.setOnClickListener(v -> showDatePickerDialog());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDatePickerDialog(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String selectedDate = sdf.format(calendar.getTime());
        input_date.setText(selectedDate);
    }

    private void updateCalendarWithStoredDate(){
        String storedDate = hike.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = sdf.parse(storedDate);
            if(date != null){
                calendar.setTime(date);
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void saveHikeEdit(){
        String name = helper.getStringFromEditText(input_name);
        String location = helper.getStringFromEditText(input_location);
        String date = helper.getStringFromEditText(input_date);

        double length = Double.parseDouble(helper.getStringFromEditText(input_length));
        int parking = helper.getCheckBoxValue(cbParking);
        int completed = helper.getCheckBoxValue(cbCompletion);

        String difficulty = helper.getSelectedSpinnerItem(sp_difficulty);
        String description = helper.getStringFromEditText(input_description);

        int favorite = hike.getFavorite();

        Hike updatedHike = new Hike(name,location,date,parking,length,difficulty,description,favorite,completed);
        db.updateHikeNameById(updatedHike,id);

        finish();
    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            if (spinner.getAdapter().getItem(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                return;
            }
        }
    }

    public void clickSaveEditButton(View view){
        if(!isMandatoryFieldsEmpty(view)){
            saveHikeEdit();
            return;
        }
        helper.showMessage(this,R.string.msg_mandatory_fields);
    }

    private boolean isMandatoryFieldsEmpty(View view){

        String name = helper.getStringFromEditText(input_name);
        String location = helper.getStringFromEditText(input_location);
        String date = helper.getStringFromEditText(input_date);
        String length = helper.getStringFromEditText(input_length);
        if (name.isEmpty() || location.isEmpty() || date.isEmpty()|| length.isEmpty()) {
            return true;
        }
        return false;
    }
}