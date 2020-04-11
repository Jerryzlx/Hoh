package com.example.hoh.home;

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

public class HomeRecipeFragment extends Fragment {
    private TextView title;
    private TextView detail;
    private ImageButton back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);
        title = (TextView) view.findViewById(R.id.textView_recipe_title);
        detail = (TextView) view.findViewById(R.id.textView_recipe_detail);
        back = (ImageButton) view.findViewById(R.id.imageButton_recipe_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setHome_status(0);
//                ((MainActivity) getActivity()).onCheckedChanged(((MainActivity) getActivity()).getrg_tab_bar(), ((MainActivity) getActivity()).getCheck_id());
                ((MainActivity) getActivity()).switchToHome();
            }
        });
        return view;


    }
}
