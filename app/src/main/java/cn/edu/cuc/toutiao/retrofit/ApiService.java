package cn.edu.cuc.toutiao.retrofit;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.bean.NewsDetail;
import cn.edu.cuc.toutiao.bean.Recommendation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
}
