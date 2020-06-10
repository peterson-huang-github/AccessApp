package com.example.accessapp;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final int MAX_NUM_OF_USER_LIST = 100;
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    private final static String TAG = "MyActivity";
    // TODO: not show log message when DEBUG_MODE = false;
    private final static boolean DEBUG_MODE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Use MVVM architecture
        listView=(ListView)findViewById(R.id.listview);

        getUserData();
    }

    private void initDatas() {
        Map map1=new HashMap();
        //map1.put("img",R.drawable.fish);
        map1.put("name","小蒙牛");
        Map map2=new HashMap();
        //map2.put("img",R.drawable.horse);
        map2.put("name","千里马");
        Map map3=new HashMap();
        //map3.put("img",R.drawable.mouse);
        map3.put("name","米老鼠");
        datas.add(map1);
        datas.add(map2);
        datas.add(map3);

        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
        datas.add(map1);
        datas.add(map2);
        datas.add(map3);
    }

    private void getUserData(){
        String url = "https://api.github.com/users";
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute(url);
    }

    public class OkHttpHandler extends AsyncTask {

        // TODO: OKHttp could only has one instance at one time, so we need to optimize it for preventing error。
        private OkHttpClient client = new OkHttpClient();

        protected void onPostExecute(Object s) {
            super.onPostExecute(s);

            refreshUI(s.toString());
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

    private void refreshUI(String rawJSON){
        try {
            JSONArray array = new JSONArray(rawJSON);

            for (int n = 0; n < array.length() && n < MAX_NUM_OF_USER_LIST; n++) {
                JSONObject object = array.getJSONObject(n);
                String photoUrl = object.getString("avatar_url");
                String loginName = object.getString("login");

                Map map=new HashMap();
                map.put("photo", photoUrl);
                map.put("name",loginName);
                datas.add(map);
            }

        } catch (Exception e){

        }

        SimpleAdapter.ViewBinder viewbinder = new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data != null){
                    ImageView imageView = (ImageView) view;
                    Picasso.get().load(data.toString()).into(imageView);
                } else if(view instanceof TextView){
                    TextView textView = (TextView) view;
                    textView.setText(data.toString());
                }
                return true;
            }
        };

        simpleAdapter=new SimpleAdapter(this,datas,R.layout.user_layout,new String[]{"photo","name"},new int[]{R.id.photo, R.id.name});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.setViewBinder(viewbinder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}