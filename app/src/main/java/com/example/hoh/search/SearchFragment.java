package com.example.hoh.search;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {

    ImageView imageView_hed;
    ImageView imageView_hea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        imageView_hed = (ImageView) view.findViewById(R.id.imageView_hed);
        imageView_hed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSearch_status(1);
                ((MainActivity) getActivity()).switchToSubSearch();
            }
        });
        imageView_hea = (ImageView) view.findViewById(R.id.imageView_hea);
        imageView_hea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSearch_status(1);
                ((MainActivity) getActivity()).switchToSubSearch();
            }
        });
        return view;
    }
}

