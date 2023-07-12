package com.example.asaf.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.widget.Button;

import java.text.SimpleDateFormat;

import java.util.Locale;

public class DateTimeUtils {
    public static void showDatePickerDialog(Activity activity, Calendar calendar, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public static void showTimePickerDialog(Activity activity, Calendar calendar, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                activity,
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    public static void updateSelectedDate(Button button, Calendar calendar) {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String selectedDate = sdf.format(calendar.getTime());
        button.setText(selectedDate);
    }

    public static void updateSelectedTime(Button button, Calendar calendar) {
        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        String selectedTime = sdf.format(calendar.getTime());
        button.setText(selectedTime);
    }
}
