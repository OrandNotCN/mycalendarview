package com.lacuz.cherish.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lacuz.cherish.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarUtil;
import com.prolificinteractive.materialcalendarview.CalendarUtil2;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;


public class SimpleCalendarDialogFragment extends AppCompatDialogFragment implements OnDateSelectedListener,DialogInterface.OnClickListener {

    MaterialCalendarView calendarView;
    TextView textView;
    OnFragmentDateSelectedListener listener;
    CalendarDay selectDate;

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        listener.onDateSelected(selectDate.getDate().getTime(),SimpleDateFormat.getDateInstance().format(selectDate.getDate()), CalendarUtil2.solarToLunar(selectDate.getDate()));
    }

    public interface OnFragmentDateSelectedListener {
        void onDateSelected(long timemillis,String solar,String lunar);
    }

    public SimpleCalendarDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public SimpleCalendarDialogFragment(OnFragmentDateSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_basic, null);
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendar);
        textView = (TextView)view.findViewById(R.id.textView);
        calendarView.setOnDateChangedListener(this);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .create();
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        textView.setText(SimpleDateFormat.getDateInstance().format(date.getDate()));
        selectDate = date;
    }
}