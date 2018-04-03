package com.thinkline256.themenu.data;

/**
 * Created by cato on 4/3/18.
 */

public class DataUtils {

    public static Repository getRepository(){
        return  Repository.getInstance(new FireStore());
    }
}
