package cn.edu.cuc.toutiao.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhengj on 2017/7/31.
 */

public interface ApiService {
    @GET("getTypes")
    Call<ArrayList<String>> getTypes(@Query("gid") String gid,@Query("uid") String uid);
}
