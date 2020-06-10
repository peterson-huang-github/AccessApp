package com.example.accessapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepo {

    private final static String TAG = "UserRepo";
    private Activity mActivity = null;

    public class OkHttpHandlerRepo extends AsyncTask {

        // TODO: OKHttp could only has one instance at one time, so we need to optimize it for preventing error。
        private OkHttpClient client = new OkHttpClient();

        protected void onPostExecute(Object s) {
            super.onPostExecute(s);
            setDataRepos(s.toString());
            refreshUIfromRepo();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Request.Builder builder = new Request.Builder();
            builder.url(objects[0].toString());
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    private static List<Map<String, Object>> datasRepo = null;

    private static void setDataRepos(String rawJSON){
        try {

            if(datasRepo == null){
                datasRepo = new ArrayList<Map<String, Object>>();
            }

            JSONArray array = new JSONArray(rawJSON);

            for (int n = 0; n < array.length() && n < MainActivity.MAX_NUM_OF_USER_LIST; n++) {
                JSONObject object = array.getJSONObject(n);
                UserModel userModel = new UserModel();
                userModel.setPhoto(object.getString("avatar_url"));
                userModel.setName(object.getString("login"));

                Map map=new HashMap();
                map.put(UserModel.PHOTO, userModel.getPhoto());
                map.put(UserModel.NAME, userModel.getName());

                datasRepo.add(map);
            }

        } catch (Exception e){

        }
    }

    private static List<Map<String, Object>> getDatasRepo(){
        return datasRepo;
    }

    public void requestHolidays(Activity activity) {
            mActivity = activity;
            if(datasRepo == null) {
                String url = "https://api.github.com/users";
                OkHttpHandlerRepo okHttpHandler = new OkHttpHandlerRepo();
                okHttpHandler.execute(url);
            } else {
                refreshUIfromRepo();
            }
    }

    private void refreshUIfromRepo(){
        ((MainActivity)mActivity).refreshUI(datasRepo);
    }



}

