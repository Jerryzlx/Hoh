package com.example.hoh.favorite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;
import com.example.hoh.model.Recipe;


@SuppressLint("ValidFragment")
public class FavoriteRecipeFragment extends Fragment {
    private TextView title;
    private TextView calories;
    private TextView ingredient;
    private TextView direction;
    private ImageButton back;
    private Recipe choosen_recipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);
        title = (TextView) view.findViewById(R.id.textView_recipe_title);
        calories = (TextView) view.findViewById(R.id.textView_recipe_calories);
        ingredient = (TextView) view.findViewById(R.id.textView_recipe_ingredients);
        direction = (TextView) view.findViewById(R.id.textView_recipe_direction);
        back = (ImageButton) view.findViewById(R.id.imageButton_recipe_back);
        update();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFavorite_status(0);
                ((MainActivity) getActivity()).onCheckedChanged(((MainActivity) getActivity()).getrg_tab_bar(), ((MainActivity) getActivity()).getCheck_id());
            }
        });
        return view;
    }

    private void update() {
        Recipe recipe = ((MainActivity)getActivity()).getFavorite_choosen_recipe();
        if (recipe == null) {
            return;
        } else if (choosen_recipe == null || !recipe.equals(choosen_recipe)){
            choosen_recipe = recipe;
            title.setText(choosen_recipe.getRecipeTitle());
            calories.setText("Calories: "+String.valueOf(choosen_recipe.getCalories()));
            ingredient.setText("Ingredients: \n" + choosen_recipe.getIngredients().replace('|', '\n'));
            direction.setText("Directions: \n" +choosen_recipe.getDirections().replace('|', '\n'));
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            update();
        }
    }


}
