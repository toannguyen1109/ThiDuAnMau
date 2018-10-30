package com.thi.poly.thi;

import android.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private final int year;
    private final int month;
    private final int day;
    public DatePickerFragment() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }
}