package com.example.hoh.constant;

public class AppConfig {

    public final static String BASE_URL_PATH = "http://10.0.2.2:9099/Hoh";
    public final static String SIGN_IN = BASE_URL_PATH.concat("/user/signin");
    public final static String SIGN_UP = BASE_URL_PATH.concat("/user/signup");
    public final static String GET_FAVORITE = BASE_URL_PATH.concat("/recipe/getFavorite");
    public final static String ADD_FAVORITE = BASE_URL_PATH.concat("/recipe/addFavorite");
    public final static String SEARCH = BASE_URL_PATH.concat("/recipe/search");




}
