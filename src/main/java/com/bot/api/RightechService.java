package com.bot.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RightechService {

    @GET("student")
    Call<JsonObject> getStudentByNickname(@Query("nickname") String nickname);

    @GET("studentTasks")
    Call<JsonArray> getStudentTasksByStudentId(@Query("id") long id);

    @PUT("student")
    Call<Void> registerStudent(@Query("nickname") String nickname, @Query("password") String password);

    @GET("/check")
    Call<ResponseBody> check(@Query("nickname") String nickname, @Query("discipline") String discipline, @Query("task_number") int task_number, @Query("ip") String ip);

}
