package com.example.hoh.search;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;
import com.example.hoh.bean.BaseResponseBean;
import com.example.hoh.model.Favorite;
import com.example.hoh.model.Recipe;
import com.example.hoh.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static com.example.hoh.constant.AppConfig.DISLIKE;
import static com.example.hoh.constant.AppConfig.IS_FAVORITE;
import static com.example.hoh.constant.AppConfig.LIKE;

public class SearchRecipeFragment extends Fragment {

    private static final String FRAGMENT_TAG="SearchRecipeLog";
    private final OkHttpClient client = new OkHttpClient();
    private TextView title;
    private TextView calories;
    private TextView ingredient;
    private TextView direction;
    private ImageButton back;
    private ImageButton like;
    private Recipe choosen_recipe;
    private boolean is_like;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);
        title = (TextView) view.findViewById(R.id.textView_recipe_title);
        calories = (TextView) view.findViewById(R.id.textView_recipe_calories);
        ingredient = (TextView) view.findViewById(R.id.textView_recipe_ingredients);
        direction = (TextView) view.findViewById(R.id.textView_recipe_direction);
        back = (ImageButton) view.findViewById(R.id.imageButton_recipe_back);
        like = (ImageButton) view.findViewById(R.id.like);

        update();
        checkIsLike();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setConstrait(null);
                ((MainActivity) getActivity()).setSearch_status(2);
                ((MainActivity) getActivity()).switchToSearchRecipeList();
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).getUserId() == -1) {
                    //show Toast

                    Toast.makeText(getActivity(), "You have to sign in first!", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }
                checkIsLike();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int userId = ((MainActivity) getActivity()).getUserId();
                        int recipeId = choosen_recipe.getRecipeId();
                        String url;
                        if (is_like) {
                            url = DISLIKE;
                        } else {
                            url = LIKE;
                        }
                        //设置媒体类型。application/json表示传递的是一个json格式的对象
                        MediaType mediaType = MediaType.parse("application/json");
                        //使用JSONObject封装参数
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("userId",userId);
                            jsonObject.put("recipeId", recipeId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //创建RequestBody对象，将参数按照指定的MediaType封装
                        RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
                        Request request = new Request.Builder()
                                .post(requestBody)//Post请求的参数传递
                                .url(url)
                                .build();
                        Log.i(FRAGMENT_TAG, request.toString());
                        try {
                            Response response = client.newCall(request).execute();//发送请求
                            String result = response.body().string();

                            //get the type of data
                            Type jsonType = new TypeToken<BaseResponseBean<Integer>>(){}.getType();
                            BaseResponseBean<User> bean = new Gson().fromJson(result, jsonType);
                            if (bean.getData() == null) {
                                //show Toast
                                Looper.prepare();
                                Toast.makeText(getActivity(), "Action Failed", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            } else{
                                if (is_like) {
                                    is_like = false;
                                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                                } else {
                                    is_like = true;
                                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
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

    private void checkIsLike() {
        if (((MainActivity) getActivity()).getUserId() == -1) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                }
            });
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int userId = ((MainActivity) getActivity()).getUserId();
                int recipeId = choosen_recipe.getRecipeId();
                String url = IS_FAVORITE;
                //设置媒体类型。application/json表示传递的是一个json格式的对象
                MediaType mediaType = MediaType.parse("application/json");
                //使用JSONObject封装参数
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userId",userId);
                    jsonObject.put("recipeId", recipeId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //创建RequestBody对象，将参数按照指定的MediaType封装
                RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
                Request request = new Request.Builder()
                        .post(requestBody)//Post请求的参数传递
                        .url(url)
                        .build();
                Log.i(FRAGMENT_TAG, request.toString());
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Log.d(FRAGMENT_TAG, result);
                    //get the type of data
                    Type jsonType = new TypeToken<BaseResponseBean<Favorite>>(){}.getType();
                    BaseResponseBean<User> bean = new Gson().fromJson(result, jsonType);
                    if (bean.getData() == null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                like.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                            }
                        });
                        is_like = false;
                        Log.d(FRAGMENT_TAG, "Checking favorite recipe: No");
                    } else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                like.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                            }
                        });
                        is_like = true;
                        Log.d(FRAGMENT_TAG, "Checking favorite recipe: Yes");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            checkIsLike();
            update();
        }
    }


}
