package com.example.hoh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hoh.favorite.FavoriteFragment;
import com.example.hoh.favorite.FavoriteRecipeFragment;
import com.example.hoh.home.HomeRecipeFragment;
import com.example.hoh.model.Recipe;
import com.example.hoh.search.SearchRecipeFragment;
import com.example.hoh.search.SearchRecipeListFragment;
import com.example.hoh.search.SubSearchFragment;
import com.example.hoh.signin.SignInActivity;
import com.example.hoh.signup.SignUpActivity;
import com.example.hoh.timer.TimerFragment;
import com.example.hoh.home.HomeFragment;
import com.example.hoh.search.SearchFragment;
import com.example.hoh.shoppinglist.ShoppingListFragment;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String ACTIVITY_TAG="MainLog";

    private RadioGroup rg_tab_bar;
    private RadioButton rb_main;

    private Fragment mCurFragment = new Fragment();
    private HomeFragment fg_home = new HomeFragment();
    private Fragment fg_shopping_list = new ShoppingListFragment();
    private Fragment fg_timer = new TimerFragment();
    private Fragment fg_search = new SearchFragment();
    private Fragment fg_favorite = new FavoriteFragment();
    private Fragment fg_home_recipe = new HomeRecipeFragment();
    private Fragment fg_favorite_recipe = new FavoriteRecipeFragment();
    private Fragment fg_search_recipe = new SearchRecipeFragment();
    private Fragment fg_subsearch = new SubSearchFragment();
    private Fragment fg_search_recipe_list = new SearchRecipeListFragment();
    public int home_status = 0;
    public int search_status = 0;
    public int favorite_status = 0;
    public Recipe search_choosen_recipe;
    public Recipe favorite_choosen_recipe;
    public Map<String, Object> constrait;

    //0 for healthism threshold
    //1 for hedonism threshold
    public int search_threshold = 0;
    public boolean is_SignIn = false;
    public int userId = -1;
    public String username = null;
    private FragmentManager fManager;
    private final OkHttpClient client = new OkHttpClient();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().getDecorView().setImportantForAutofill(
                    View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get username
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        userId = sharedPreferences.getInt("id", -1);

        // check whether user login in and hide the item on menu
        if (!username.equals("")) {
            is_SignIn = true;
            Toast.makeText(MainActivity.this, "Welcome " + username, Toast.LENGTH_SHORT).show();
        } else {
            is_SignIn = false;
        }
        invalidateOptionsMenu();

        fManager = getSupportFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        //Radio的点击事件监听
        rg_tab_bar.setOnCheckedChangeListener(this);

        //获取首页单选按钮，并设置为选中状态
        rb_main = (RadioButton) findViewById(R.id.rb_home);
        rb_main.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getInt("id", -1);
    }


    //Radio 点击事件设置
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
//        hideAllFragment(fTransaction);

        switch (checkedId){
            case R.id.rb_search:
                if(getSearch_status() == 0) {
                    switchToSearch();
                } else if (getSearch_status() == 1) {
                    switchToSubSearch();

                } else if (getSearch_status() == 2) {
                    switchToSearchRecipeList();
                } else {
                    switchToSearchRecipe();
                }

//                if (fg_search == null){
//                    //第一次需要加载Fragment
//                    fg_search = new SearchFragment();
//                    fTransaction.add(R.id.ly_content,fg_search);
//                } else{
//                    fTransaction.show(fg_search);
//                }
                break;
            case R.id.rb_home:
                if (getHome_status() == 0) {
                    switchToHome();
                } else {
                    switchToHomeRecipe();
                }
//                if (fg_home == null){
//                    fg_home = new HomeFragment();
//                    fTransaction.add(R.id.ly_content,fg_home);
//                }else{
//                    fTransaction.show(fg_home);
//                }
                break;
            case R.id.rb_shoppinglist:
                switchToShoppingList();
                break;

            case R.id.rb_favorite:
                if (getFavorite_status() == 0) {
                    switchToFavorite();
                } else {
                    switchToFavoriteRecipe();
                }
//                if (fg_favorite == null){
//                    fg_favorite = new FavoriteFragment();
//                    fTransaction.add(R.id.ly_content,fg_favorite);
//                }else{
//                    fTransaction.show(fg_favorite);
//                }
                break;

            case R.id.rb_timer:
                switchToTimer();
                break;

        }
        fTransaction.commit();
    }

    private void switchFragment(Fragment targetFragment){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {//如果要显示的targetFragment没有添加过
            transaction
                    .hide(mCurFragment)//隐藏当前Fragment
                    .add(R.id.ly_content, targetFragment)//添加targetFragment
                    .commit();
        } else {//如果要显示的targetFragment已经添加过
            transaction//隐藏当前Fragment
                    .hide(mCurFragment)
                    .show(targetFragment)//显示targetFragment
                    .commit();
        }
        //更新当前Fragment为targetFragment
        mCurFragment = targetFragment;

    }

    public void switchToHome() {
        switchFragment(fg_home);
    }

    public void switchToFavorite() {
        switchFragment(fg_favorite);
    }
    public void switchToShoppingList() {
        switchFragment(fg_shopping_list);
    }
    public void switchToFavoriteRecipe() {
        switchFragment(fg_favorite_recipe);
    }
    public void switchToHomeRecipe() {
        switchFragment(fg_home_recipe);
    }

    public void switchToSearch() {
        switchFragment(fg_search);
    }
    public void switchToSubSearch() {
        switchFragment(fg_subsearch);
    }
    public void switchToSearchRecipeList() { switchFragment(fg_search_recipe_list);}
    public void switchToSearchRecipe() {
        switchFragment(fg_search_recipe);
    }
    public void switchToTimer() {
        switchFragment(fg_timer);
    }

    //隐藏所有的Fragment
    private void hideAllFragment(FragmentTransaction fTransaction) {
        if(fg_home != null)fTransaction.hide(fg_home);
        if(fg_shopping_list != null)fTransaction.hide(fg_shopping_list);
        if(fg_search != null)fTransaction.hide(fg_search);
        if(fg_timer != null)fTransaction.hide(fg_timer);
        if(fg_favorite != null)fTransaction.hide(fg_favorite);
        if(fg_favorite_recipe != null)fTransaction.hide(fg_favorite_recipe);
        if(fg_home_recipe != null)fTransaction.hide(fg_home_recipe);
        if(fg_search_recipe != null)fTransaction.hide(fg_search_recipe);
    }

    private void goToSignInActivity() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        hideAllFragment(transaction);
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void goToSignUpActivity() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        hideAllFragment(transaction);
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }





    //top_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        MenuItem signIn = menu.findItem(R.id.signin);
        MenuItem logOut = menu.findItem(R.id.logout);
        if (is_SignIn) {
            signIn.setVisible(false);
            logOut.setVisible(true);
        } else {
            signIn.setVisible(true);
            logOut.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                sharedPreferences.edit().remove("id").apply();
                userId = -1;
                is_SignIn = false;
                invalidateOptionsMenu();
                return true;

            case R.id.signin:
                goToSignInActivity();
                return true;

            case R.id.signup:
                goToSignUpActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // setter and getter for public variable
    public int getHome_status() {
        return home_status;
    }
    public int getSearch_status() {
        return search_status;
    }

    public int getFavorite_status() {
        return favorite_status;
    }

    public void setHome_status(int value) {
        home_status = value;
    }
    public void setSearch_status(int value) {
        search_status = value;
    }
    public void setFavorite_status(int value) {
        favorite_status = value;
    }

    public RadioGroup getrg_tab_bar() {
        return rg_tab_bar;
    }

    public int getCheck_id() {
        return rg_tab_bar.getCheckedRadioButtonId();
    }

    public int getSearch_threshold() {
        return search_threshold;
    }

    public void setSearch_threshold(int search_threshold) {
        this.search_threshold = search_threshold;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Recipe getSearch_choosen_recipe() {
        return search_choosen_recipe;
    }

    public void setSearch_choosen_recipe(Recipe search_choosen_recipe) {
        this.search_choosen_recipe = search_choosen_recipe;
    }

    public Recipe getFavorite_choosen_recipe() {
        return favorite_choosen_recipe;
    }

    public void setFavorite_choosen_recipe(Recipe favorite_choosen_recipe) {
        this.favorite_choosen_recipe = favorite_choosen_recipe;
    }

    public Map<String, Object> getConstrait() {
        return constrait;
    }

    public void setConstrait(Map<String, Object> constrait) {
        this.constrait = constrait;
    }
}
