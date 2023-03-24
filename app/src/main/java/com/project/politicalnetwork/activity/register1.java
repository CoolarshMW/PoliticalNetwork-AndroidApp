package com.project.politicalnetwork.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.politicalnetwork.R;

import java.util.Calendar;

public class register1 extends Fragment {

    private ImageButton arrowRight;
    private EditText name, phone;
    private TextView dob;
    private AutoCompleteTextView gender;

    int year, month, day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register1, container, false);

        arrowRight = (ImageButton) view.findViewById(R.id.nextButton);
        name = (EditText) view.findViewById(R.id.name);
        phone = (EditText) view.findViewById(R.id.phone);
        dob = (TextView) view.findViewById(R.id.dob);
        gender = (AutoCompleteTextView) view.findViewById(R.id.gender);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_list_item_1);
        gender.setAdapter(arrayAdapter);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dob.setTextColor(getResources().getColor(R.color.black));
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        dob.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String pName = name.getText().toString();
//                String pPhone = phone.getText().toString();
//                String pDob = dob.getText().toString();
                    register2 reg = new register2();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment, reg)
                            .addToBackStack(null)
                            .commit();

            }
        });
        return view;
    }
}