package com.example.hoh.favorite;


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

@SuppressLint("ValidFragment")
public class FavoriteFragment extends Fragment {

    private static final String FRAGMENT_TAG="FavoriteLog";
    private final OkHttpClient client = new OkHttpClient();
    private ListView listView;
    private List<Recipe> recipeData;
    private List<String> adaptList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list_v2,container,false);
        update();
        listView = (ListView) view.findViewById(R.id.listView_recipe);

        //定义一个链表用于存放要显示的数据
//        //存放要显示的数据
//        for (int i = 0; i < 20; i++) {
//            adapterData.add("ListItem" + i);
//        }

        return view;
    }

    private void update(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData = null;
                int userId = ((MainActivity) getActivity()).getUserId();
                if (userId == -1) {
                    listView.setAdapter(null);
                    Looper.prepare();
                    Toast.makeText(getActivity(), "You need to sign in first to see your favorite recipes", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }
//                //build the URL for login
//                HttpUrl.Builder urlBuilder = HttpUrl.parse(SIGN_IN).newBuilder();
//                urlBuilder.addQueryParameter("username", username);
//                urlBuilder.addQueryParameter("password", password);
                String url = GET_FAVORITE;
                HttpUrl httpUrl = HttpUrl.parse(url).newBuilder().addQueryParameter("userId", String.valueOf(userId)).build();
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
                        Log.d(FRAGMENT_TAG, "result: Update favorite page failed :");

                        //show Toast
                        Looper.prepare();
                        Toast.makeText(getActivity(), "Update favorite page failed!", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else{
                        Log.d(FRAGMENT_TAG, "result: Update favorite page successfully"+result);
                        Log.d(FRAGMENT_TAG, "result: " + bean.getData());
                        recipeData = (List<Recipe>) bean.getData();
                        Log.d(FRAGMENT_TAG, "data: " + recipeData);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adaptList = getData();
                                //创建ArrayAdapter对象adapter并设置适配器
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        simple_list_item_1, adaptList);
                                //将LsitView绑定到ArrayAdapter上
                                ListView listView = getView().findViewById(R.id.listView_recipe);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    //parent 代表listView View 代表 被点击的列表项 position 代表第几个 id 代表列表编号
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               Toast.makeText(getActivity(), id+"", Toast.LENGTH_LONG).show();
                                        ((MainActivity) getActivity()).switchToFavoriteRecipe();
                                        ((MainActivity) getActivity()).setFavorite_status(1);
                                        ((MainActivity) getActivity()).setFavorite_choosen_recipe(recipeData.get(position));
                                    }
                                });
                            }
                        });

                    }
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






