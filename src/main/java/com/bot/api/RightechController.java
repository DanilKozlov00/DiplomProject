package com.bot.api;

import com.bot.model.Task;
import com.bot.model.Student;
import com.google.gson.*;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RightechController implements Callback<List<JsonObject>> {

    private static final String BASE_URL = "http://localhost:8080/";

    private final RightechService rightechService;

    public RightechController() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().readTimeout(15, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        rightechService = retrofit.create(RightechService.class);
    }


    public Student getStudentByNickname(String nickname) {
        Student currentStudent = null;
        try {
            Call<JsonObject> call = rightechService.getStudentByNickname(nickname);
            Response<JsonObject> response = call.execute();
            if (response.body() != null) {
                currentStudent = new Gson().fromJson(response.body(), Student.class);
            }
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
        return currentStudent;
    }

    public List<Task> getStudentTasksByStudentId(long id) {
        List<Task> tasks = new ArrayList<>();
        try {
            Call<JsonArray> call = rightechService.getStudentTasksByStudentId(id);
            Response<JsonArray> response = call.execute();
            if (response.body() != null) {
                for (int i = 0; i < response.body().size(); i++) {
                    tasks.add(new Gson().fromJson(response.body().get(i), Task.class));
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return tasks;
    }

    public String registerStudent(String nickname, String password) {
        try {
            Call<Void> call = rightechService.registerStudent(nickname, password);
            Response<Void> response = call.execute();
            if (response.code() == 200) {
                return "User registered";
            }
            if (response.code() == 409) {
                return "This user is already exists";
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return "Error create user";
    }

    public String check(String nickname, String discipline, int taskNumber, String ip) {
        try {
            Call<ResponseBody> call = rightechService.check(nickname, discipline, taskNumber, ip);
            Response<ResponseBody> response = call.execute();
            if (response.code() == 200 && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return "Error check";
    }

    @Override
    public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {

    }

    @Override
    public void onFailure(Call<List<JsonObject>> call, Throwable throwable) {

    }
}
