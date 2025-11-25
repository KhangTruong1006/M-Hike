package com.example.m_hike.Helper;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Helper {
    public String getStringFromEditText(TextView tv){
        return tv.getText().toString().trim();
    }
    public int getCheckBoxValue(CheckBox cb){
        if(cb.isChecked()){
            return 1;
        }
        return 0;
    }

    public String getSelectedSpinnerItem(Spinner sp){
        return sp.getSelectedItem().toString();
    }

    public void showMessage(Context context, int message){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }
}
