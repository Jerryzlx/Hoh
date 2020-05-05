package com.example.hoh.search;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("ValidFragment")
public class SubSearchFragment extends Fragment {

    private static final String FRAGMENT_TAG="SubSearchLog";

    private int[] categoryId = {38, 116, 70, 101, 88, 17};
    private RadioButton[] calories_radioButtonList;
    private CheckBox[] category_checkBoxList;
    EditText inputCalories;
    EditText name;
    Button button_search;
    RadioButton rb_low;
    RadioButton rb_mid;
    RadioButton rb_high;
    RadioGroup rb_group;
    Button back;
    Button search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subsearch,container,false);
        rb_low = view.findViewById(R.id.low);
        rb_mid = view.findViewById(R.id.mid);
        rb_high = view.findViewById(R.id.high);
        back = view.findViewById(R.id.btn_back);
        inputCalories =(EditText) view.findViewById(R.id.calorie);
        inputCalories.postDelayed(new Runnable() {
            @Override
            public void run() {
                inputCalories.requestFocus();
            }
        }, 500);
        name = (EditText) view.findViewById(R.id.name);
        search = view.findViewById(R.id.btn_search);
        rb_group = view.findViewById(R.id.rb_group);
        if (((MainActivity) getActivity()).getSearch_threshold() == 0) {
            rb_low.setText("300");
            rb_mid.setText("500");
            rb_high.setText("700");
        } else {
            rb_low.setText("900");
            rb_mid.setText("1100");
            rb_high.setText("1300");
        }

        CheckBox checkBox1 = view.findViewById(R.id.checkbox_1);
        CheckBox checkBox2 = view.findViewById(R.id.checkbox_2);
        CheckBox checkBox3 = view.findViewById(R.id.checkbox_3);
        CheckBox checkBox4 = view.findViewById(R.id.checkbox_4);
        CheckBox checkBox5 = view.findViewById(R.id.checkbox_5);
        CheckBox checkBox6 = view.findViewById(R.id.checkbox_6);
        category_checkBoxList = new CheckBox[]{checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6};

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSearch_status(0);
                ((MainActivity) getActivity()).switchToSearch();
            }
        });
        button_search = (Button) view.findViewById(R.id.btn_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> constraint = new HashMap<>();
                try{
                    constraint = getConstraint();
                } catch(Exception e) {
                    Toast.makeText(getActivity(),"Please enter a valid integer for calories!!",  Toast.LENGTH_SHORT).show();
                    Log.d(FRAGMENT_TAG, e.getMessage());
                    return;
                }
                ((MainActivity) getActivity()).setConstrait(constraint);
                ((MainActivity) getActivity()).setSearch_status(2);
                ((MainActivity) getActivity()).switchToSearchRecipeList();
            }
        });


        return view;
    }

    private Map<String, Object> getConstraint() throws Exception {
        Map<String, Object> constraint = new HashMap<>();
        List<Integer> category = new ArrayList<>();
        if (inputCalories.getText() == null || inputCalories.getText().toString().equals("")) {
            if (onRadioButtonClicked(getView()) != 0) {
                Log.d(FRAGMENT_TAG, "value of rb: " + onRadioButtonClicked(getView()));
                constraint.put("calories", onRadioButtonClicked(getView()));
            }
        } else{
            constraint.put("calories",Integer.parseInt(inputCalories.getText().toString()));
        }
        for (int i = 0; i < category_checkBoxList.length; i++) {
            if (category_checkBoxList[i].isChecked()) {
                category.add(categoryId[i]);
            }
        }
        Log.d(FRAGMENT_TAG, "category:" + category);
        if (!category.isEmpty()) {
            constraint.put("category", category);
        }
        if (name.getText()== null || !name.getText().toString().equals("")) {
            constraint.put("title", name.getText());
        }
        Log.d(FRAGMENT_TAG, constraint.toString());
        return constraint;


    }

    public int onRadioButtonClicked( View view) {
        // check which radiobutton is clicked
        int checkedId = rb_group.getCheckedRadioButtonId();

        switch(checkedId) {
            case R.id.low:
                return Integer.valueOf(rb_low.getText().toString());
            case R.id.mid:
                return Integer.valueOf(rb_mid.getText().toString());
            case R.id.high:
                return Integer.valueOf(rb_high.getText().toString());
        }
        return 0;
    }
}


