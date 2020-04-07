package com.example.hoh.favorite;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hoh.MainActivity;
import com.example.hoh.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

@SuppressLint("ValidFragment")
public class FavoriteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        ListView listView = (ListView) view.findViewById(R.id.listView_faborite);
        //定义一个链表用于存放要显示的数据
        final List<String> adapterData = new ArrayList<String>();
        //存放要显示的数据
        for (int i = 0; i < 20; i++) {
            adapterData.add("ListItem" + i);
        }
        //创建ArrayAdapter对象adapter并设置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                simple_list_item_1, adapterData);
        //将LsitView绑定到ArrayAdapter上
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //parent 代表listView View 代表 被点击的列表项 position 代表第几个 id 代表列表编号
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               Toast.makeText(getActivity(), id+"", Toast.LENGTH_LONG).show();
                ((MainActivity) getActivity()).setFavorite_status(1);
                ((MainActivity) getActivity()).switchToFavoriteRecipe();
            }
        });
        return view;
    }


}

