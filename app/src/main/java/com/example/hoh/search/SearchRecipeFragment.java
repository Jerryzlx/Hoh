package com.example.hoh.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.R;

public class SearchRecipeFragment extends Fragment {

    private ImageView imageView;
    private TextView title;
    private TextView detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);
        imageView = (ImageView) view.findViewById(R.id.imageView_recipe);
        title = (TextView) view.findViewById(R.id.textView_recipe_title);
        detail = (TextView) view.findViewById(R.id.textView_recipe_detail);
        return view;
    }
}
