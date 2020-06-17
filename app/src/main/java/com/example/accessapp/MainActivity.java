package com.example.accessapp;

import android.os.Bundle;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final int MAX_NUM_OF_USER_LIST = 100;
    private static ListView listView;
    private static SimpleAdapter simpleAdapter;
    private static SimpleAdapter.ViewBinder viewbinder;
    private static List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private static AppCompatActivity mContext;

    private final static String TAG = "MyActivity";
    // TODO: not show log message when DEBUG_MODE = false;
    private final static boolean DEBUG_MODE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        // TODO: Use MVVM architecture
        // Because final version is business secret, I do not provide it in github.
        // Not use MVVM architecture if the amount of UI items is below five.
        listView= (ListView) findViewById(R.id.listview);
        getUserData();
    }

    private void initDatas() {
        // for UI Test
        Map map1=new HashMap();

        map1.put("name","小蒙牛");
        Map map2=new HashMap();

        map2.put("name","千里马");
        Map map3=new HashMap();

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
        UserViewModel.getHolidays(mContext);
    }

    private static boolean isTargetView(View v, String viewName){
        return v.getResources().getResourceEntryName(v.getId()).equalsIgnoreCase(viewName);
    }

    public static void refreshUI(List<Map<String, Object>> datas){

        viewbinder = new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data != null){
                    ImageView imageView = (ImageView) view;
                    if(isTargetView(view,UserModel.PHOTO)) {
                        Picasso.get().load(data.toString()).into(imageView);
                    }
                } else if(view instanceof TextView){
                    TextView textView = (TextView) view;
                    if(isTargetView(view,UserModel.NAME)) {
                        textView.setText(data.toString());
                    }
                }
                return true;
            }
        };

        simpleAdapter=new SimpleAdapter(mContext, datas,R.layout.user_layout,new String[]{"photo","name"},new int[]{R.id.photo, R.id.name});
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