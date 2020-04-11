package com.example.hoh.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;

public class SearchRecipeFragment extends Fragment {

    private TextView title;
    private TextView detail;
    private ImageButton imageButton_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);
        title = (TextView) view.findViewById(R.id.textView_recipe_title);
        detail = (TextView) view.findViewById(R.id.textView_recipe_detail);
        imageButton_back = (ImageButton) view.findViewById(R.id.imageButton_recipe_back);
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSearch_status(1);
                ((MainActivity) getActivity()).switchToSubSearch();
            }
        });
        return view;
    }
}
