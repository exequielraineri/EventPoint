package com.exeraineri.eventpoint.client.ui.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.exeraineri.eventpoint.client.databinding.FragmentAddEventBinding;

import java.util.Calendar;

public class AddEventFragment extends Fragment {

    FragmentAddEventBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initListener() {
        binding.etStartDate.setOnClickListener(v -> {
            showDialogDatePicker(binding.etStartDate);
        });

        binding.etStartTime.setOnClickListener(v -> {
            showDialogTimePicker(binding.etStartTime);
        });

        binding.etEndDate.setOnClickListener(v -> {
            showDialogDatePicker(binding.etEndDate);
        });

        binding.etEndTime.setOnClickListener(v -> {
            showDialogTimePicker(binding.etEndTime);
        });
    }

    private void initUI() {


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialogTimePicker(EditText editText) {
        Calendar currentDate = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                editText.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, currentDate.get(Calendar.HOUR), currentDate.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialogDatePicker(EditText editText) {
        Calendar currentDate = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(String.format("%02d/%02d/%02d", dayOfMonth, month, year));
                    }
                }
                , currentDate.get(Calendar.DATE), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.YEAR));
        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }
}