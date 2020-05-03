package com.example.hoh.shoppinglist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.R;

public class ShoppingListFragment extends Fragment {

    private static final String FRAGMENT_TAG="ShoppingListLog";
    private EditText shoppingList;
    private Button save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        shoppingList = view.findViewById(R.id.shoppingList);
        save = view.findViewById(R.id.save);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
        String text = sharedPreferences.getString("text", "");
        Log.d(FRAGMENT_TAG, text);
        shoppingList.setText(text);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Log.d(FRAGMENT_TAG, shoppingList.getText().toString());
                editor.putString("text", shoppingList.getText().toString());
                editor.commit();
            }
        });

        return view;
    }
}

