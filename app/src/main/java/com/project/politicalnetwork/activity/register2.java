package com.project.politicalnetwork.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.project.politicalnetwork.R;

public class register2 extends Fragment {

   private AutoCompleteTextView state;
   private AutoCompleteTextView district;
   private String choice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register2, container, false);

        state = (AutoCompleteTextView) view.findViewById(R.id.state);
        district = (AutoCompleteTextView) view.findViewById(R.id.district);


        ArrayAdapter<CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.state, android.R.layout.simple_list_item_1);
        state.setAdapter(stringArrayAdapter);

        state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choice = state.getText().toString();
                Toast.makeText(getContext(), "" + choice, Toast.LENGTH_SHORT).show();

                switch (choice) {
                    case "Tamil Nadu":
                        ArrayAdapter<CharSequence> tamilNadu = ArrayAdapter.createFromResource(getActivity(), R.array.tamilNadu, android.R.layout.simple_list_item_1);
                        district.setAdapter(tamilNadu);
                        break;
                    case "Maharashtra":
                        ArrayAdapter<CharSequence> maharashtra = ArrayAdapter.createFromResource(getActivity(), R.array.maharashtra, android.R.layout.simple_list_item_1);
                        district.setAdapter(maharashtra);
                        break;
                }
            }
        });
        return view;
    }
}