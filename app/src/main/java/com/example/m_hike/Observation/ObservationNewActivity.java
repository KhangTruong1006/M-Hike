package com.example.m_hike.Observation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

public class ObservationNewActivity extends AppCompatActivity {
    private EditText input_observation, input_description;
    private Spinner sp_type;
    private CalendarView calender;
    private String date;
    private Helper hp;
    private int hike_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_observation_new);

        hp = new Helper();
        Intent intent = getIntent();
        hike_id = intent.getIntExtra("HIKE_ID",0);

        input_observation = findViewById(R.id.input_observation);
        input_description = findViewById(R.id.input_observation_description);
        calender = findViewById(R.id.calendarView);
        sp_type = findViewById(R.id.spinner_type);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date = sdf.format(Calendar.getInstance().getTime());

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = String.format(Locale.getDefault(),"%02d/%02d/%d",dayOfMonth,month+1,year);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public  void clickNewObservationButton(View view){
        if(!isMandatoryFieldEmpty(view)){
            saveObservationDetails();
            hp.showMessage(this,R.string.msg_new_observation);
            return;
        }
        hp.showMessage(this,R.string.msg_mandatory_fields);
    }
    private void saveObservationDetails(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        String observation = hp.getStringFromEditText(input_observation);
        String type = hp.getSelectedSpinnerItem(sp_type);
        String description = hp.getStringFromEditText(input_description);

        Observation newObservation = new Observation(observation,date,type,description,hike_id);
        dbHelper.insertObservation(newObservation);

        finish();
    }

    private boolean isMandatoryFieldEmpty(View view){
        String observation = hp.getStringFromEditText(input_observation);
        if(observation.isEmpty()){
            return true;
        }
        return false;
    }
}