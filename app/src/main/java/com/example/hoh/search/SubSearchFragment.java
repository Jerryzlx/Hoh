package com.example.hoh.search;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;

@SuppressLint("ValidFragment")
public class SubSearchFragment extends Fragment {

    Button button_search;
    RadioButton rb_low;
    RadioButton rb_mid;
    RadioButton rb_high;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subsearch,container,false);
        rb_low = view.findViewById(R.id.low);
        rb_mid = view.findViewById(R.id.mid);
        rb_high = view.findViewById(R.id.high);
        if (((MainActivity) getActivity()).getSearch_threshold() == 0) {
            rb_low.setText("300");
            rb_mid.setText("500");
            rb_high.setText("700");
        } else {
            rb_low.setText("900");
            rb_mid.setText("1100");
            rb_high.setText("1300");
        }
        button_search = (Button) view.findViewById(R.id.btn_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSearch_status(2);
                ((MainActivity) getActivity()).switchToSearchRecipe();
            }
        });
        return view;
    }
}


