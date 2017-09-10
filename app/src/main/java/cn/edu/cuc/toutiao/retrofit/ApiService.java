package cn.edu.cuc.toutiao.retrofit;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.cuc.toutiao.bean.LoginInfo;
import cn.edu.cuc.toutiao.bean.NewsDetail;
import cn.edu.cuc.toutiao.bean.RSA;
import cn.edu.cuc.toutiao.bean.Recommendation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zhengj on 2017/7/31.
 */

public interface ApiService {
    @GET("getTypes")
    Call<ArrayList<String>> getTypes(@Query("gid") String gid,@Query("uid") String uid);

    @GET("getRec")
    Call<Recommendation> getRec(@Query("type") String type,@Query("gid") String gid,@Query("uid") String uid,
                                @Query("sum") int sum,@Query("country") String country,@Query("language") String language);

    @GET("getNews")
    Call<NewsDetail> getNews(@Query("newsid") String newsId,@Query("gid")String gid,@Query("uid") String uid);

    @GET("getRecByNews")
    Call<Recommendation> getRecByNews(@Query("newsid") String newsId,@Query("gid") String gid,@Query("uid")String uid,@Query("sum")int sum);

    @GET("rsa")
    Call<RSA> getRsa();

    @GET("register")
    Call<String> doRegister(@QueryMap Map<String,String> map);

}
