package com.example.hoh.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;
import com.example.hoh.bean.BaseResponseBean;
import com.example.hoh.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;
import static com.example.hoh.constant.AppConfig.GET_FAVORITE;
import static com.example.hoh.constant.AppConfig.GET_RANDOM;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private static final String FRAGMENT_TAG="HomeLog";
    private static OkHttpClient client = new OkHttpClient();
    private List<Recipe> recipeData;
    private TextView home_1;
    private TextView home_2;
    private TextView home_3;
    private TextView home_4;
    private TextView home_5;
    private TextView home_6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        home_1 = view.findViewById(R.id.home_1);
        home_2 = view.findViewById(R.id.home_2);
        home_3 = view.findViewById(R.id.home_3);
        home_4 = view.findViewById(R.id.home_4);
        home_5 = view.findViewById(R.id.home_5);
        home_6 = view.findViewById(R.id.home_6);
        update();
        return view;
    }

    private void update() {
        if (recipeData!= null) {
            return;
        }
        recipeData = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = GET_RANDOM;
                HttpUrl httpUrl = HttpUrl.parse(url).newBuilder().build();
                Request request = new Request.Builder()
                        .url(httpUrl.toString())
                        .build();
                Log.i(FRAGMENT_TAG, request.toString());
                try {
                    Response response = client.newCall(request).execute();//send request
                    String result = response.body().string();

                    //get the type of data
                    Type jsonType = new TypeToken<BaseResponseBean<List<Recipe>>>(){}.getType();
                    BaseResponseBean<Recipe> bean = new Gson().fromJson(result, jsonType);
                    if (bean.getData() == null) {
                        Log.d(FRAGMENT_TAG, "result: get home recipes failed :");

                        //show Toast
                        Looper.prepare();
                        Toast.makeText(getActivity(), "Internet Error!", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else{
                        Log.d(FRAGMENT_TAG, "result: Update Home page successfully"+result);
                        Log.d(FRAGMENT_TAG, "result: " + bean.getData());
                        recipeData = (List<Recipe>) bean.getData();
                        Log.d(FRAGMENT_TAG, "data: " + recipeData);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                home_1.setText(recipeData.get(0).getRecipeTitle());
                                home_2.setText(recipeData.get(1).getRecipeTitle());
                                home_3.setText(recipeData.get(2).getRecipeTitle());
                                home_4.setText(recipeData.get(3).getRecipeTitle());
                                home_5.setText(recipeData.get(4).getRecipeTitle());
                                home_6.setText(recipeData.get(5).getRecipeTitle());
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(getActivity(), "Internet Error!", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        }).start();
        home_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setHome_status(1);
                ((MainActivity) getActivity()).switchToHomeRecipe();
                ((MainActivity) getActivity()).setHome_choosen_recipe(recipeData.get(0));
            }
        });
        home_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setHome_status(1);
                ((MainActivity) getActivity()).switchToHomeRecipe();
                ((MainActivity) getActivity()).setHome_choosen_recipe(recipeData.get(1));
            }
        });
        home_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setHome_status(1);
                ((MainActivity) getActivity()).switchToHomeRecipe();
                ((MainActivity) getActivity()).setHome_choosen_recipe(recipeData.get(2));
            }
        });
        home_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setHome_status(1);
                ((MainActivity) getActivity()).switchToHomeRecipe();
                ((MainActivity) getActivity()).setHome_choosen_recipe(recipeData.get(3));
            }
        });
        home_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setHome_status(1);
                ((MainActivity) getActivity()).switchToHomeRecipe();
                ((MainActivity) getActivity()).setHome_choosen_recipe(recipeData.get(4));
            }
        });
        home_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setHome_status(1);
                ((MainActivity) getActivity()).switchToHomeRecipe();
                ((MainActivity) getActivity()).setHome_choosen_recipe(recipeData.get(5));
            }
        });
    }

}

