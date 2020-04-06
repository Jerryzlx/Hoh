package com.example.hoh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hoh.favorite.FavoriteFragment;
import com.example.hoh.signin.SignInActivity;
import com.example.hoh.timer.TimerFragment;
import com.example.hoh.home.HomeFragment;
import com.example.hoh.search.SearchFragment;
import com.example.hoh.shoppinglist.ShoppingListFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_main;
    private String s = "123";

    private Fragment fg_home, fg_shopping_list, fg_timer, fg_search, fg_favorite;
    private FragmentManager fManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fManager = getSupportFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        //Radio的点击事件监听
        rg_tab_bar.setOnCheckedChangeListener(this);

        //获取首页单选按钮，并设置为选中状态
        rb_main = (RadioButton) findViewById(R.id.rb_home);
        rb_main.setChecked(true);

    }


    //Radio 点击事件设置
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);

        switch (checkedId){
            case R.id.rb_search:
                if (fg_search == null){
                    //第一次需要加载Fragment
                    fg_search = new SearchFragment();
                    fTransaction.add(R.id.ly_content,fg_search);
                } else{
                    fTransaction.show(fg_search);
                }
                break;
            case R.id.rb_home:
                if (fg_home == null){
                    fg_home = new HomeFragment();
                    fTransaction.add(R.id.ly_content,fg_home);
                }else{
                    fTransaction.show(fg_home);
                }
                break;
            case R.id.rb_shoppinglist:
                if (fg_shopping_list == null){
                    fg_shopping_list = new ShoppingListFragment();
                    fTransaction.add(R.id.ly_content,fg_shopping_list);
                }else{
                    fTransaction.show(fg_shopping_list);
                }
                break;

            case R.id.rb_favorite:
                if (fg_favorite == null){
                    fg_favorite = new FavoriteFragment();
                    fTransaction.add(R.id.ly_content,fg_favorite);
                }else{
                    fTransaction.show(fg_favorite);
                }
                break;

            case R.id.rb_timer:
                if (fg_timer == null){
                    fg_timer = new TimerFragment();
                    fTransaction.add(R.id.ly_content,fg_timer);
                }else{
                    fTransaction.show(fg_timer);
                }
                break;

        }
        fTransaction.commit();
    }

    //隐藏所有的Fragment
    private void hideAllFragment(FragmentTransaction fTransaction) {
        if(fg_home != null)fTransaction.hide(fg_home);
        if(fg_shopping_list != null)fTransaction.hide(fg_shopping_list);
        if(fg_search != null)fTransaction.hide(fg_search);
        if(fg_timer != null)fTransaction.hide(fg_timer);
        if(fg_favorite != null)fTransaction.hide(fg_favorite);
    }

    private void goToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }


    //top_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                //todo 跳转
                return true;

            case R.id.signin:
                //todo 跳转
                goToSignInActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
