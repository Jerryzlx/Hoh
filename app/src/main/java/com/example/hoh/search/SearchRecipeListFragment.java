package com.example.hoh.search;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.MainActivity;
import com.example.hoh.R;
import com.example.hoh.bean.BaseResponseBean;
import com.example.hoh.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;
import static com.example.hoh.constant.AppConfig.GET_FAVORITE;
import static com.example.hoh.constant.AppConfig.SEARCH;

@SuppressLint("ValidFragment")
public class SearchRecipeListFragment extends Fragment {

    private static final String FRAGMENT_TAG="SearchLog";
    private final OkHttpClient client = new OkHttpClient();
    private ListView listView;
    private Button back;
    private List<Recipe> recipeData;
    private Map<String, Object> constrait;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list,container,false);
        update();
        listView = (ListView) view.findViewById(R.id.listView_recipe);
        back = (Button) view.findViewById(R.id.btn_back_list);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSearch_status(1);
                ((MainActivity) getActivity()).switchToSubSearch();
            }
        });

        return view;
    }

    private void update(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (((MainActivity) getActivity()).getConstrait() == null) {
                    return;
                } else {
                    constrait = ((MainActivity) getActivity()).getConstrait();
                }

                int userId = ((MainActivity) getActivity()).getUserId();
                String url = SEARCH;
                MediaType mediaType = MediaType.parse("application/json");
                //使用JSONObject封装参数
                String json = new Gson().toJson(constrait);
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

                //创建RequestBody对象，将参数按照指定的MediaType封装
                RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
                Request request = new Request.Builder()
                        .post(requestBody)//Post请求的参数传递
                        .url(SEARCH)
                        .build();
                Log.i(FRAGMENT_TAG, request.toString());
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();

                    //get the type of data
                    Type jsonType = new TypeToken<BaseResponseBean<List<Recipe>>>(){}.getType();
                    BaseResponseBean<Recipe> bean = new Gson().fromJson(result, jsonType);
                    Log.d(FRAGMENT_TAG, "result: Update favorite page successfully"+result);
                    Log.d(FRAGMENT_TAG, "result: " + bean.getData());
                    recipeData = (List<Recipe>) bean.getData();
                    Log.d(FRAGMENT_TAG, "data: " + recipeData);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //make arrayAdapter
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    simple_list_item_1, getData());
                            //bind the ListView with the ArrayAdapter
                            ListView listView = getView().findViewById(R.id.listView_recipe);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                //parent 代表listView View 代表 被点击的列表项 position 代表第几个 id 代表列表编号
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               Toast.makeText(getActivity(), id+"", Toast.LENGTH_LONG).show();
                                    ((MainActivity) getActivity()).switchToSearchRecipe();
                                    ((MainActivity) getActivity()).setSearch_status(3);
                                    ((MainActivity) getActivity()).setFavorite_choosen_recipe(recipeData.get(position));
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (Recipe recipe : recipeData) {
            data.add(recipe.getRecipeTitle());
        }
        return data;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            update();
        }
    }

}






