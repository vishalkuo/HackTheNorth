package com.kitchen_ehhd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.kitchen_ehhd.Models.Drawer;
import com.kitchen_ehhd.Models.DrawerItem;
import com.kitchen_ehhd.Models.Globals;
import com.kitchen_ehhd.Services.APIService;
import com.kitchen_ehhd.VIewAdapters.MapAdapter;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;

import io.particle.android.sdk.cloud.SparkCloud;
import io.particle.android.sdk.cloud.SparkCloudException;
import io.particle.android.sdk.cloud.SparkDevice;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Toaster;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {
    private ListView itemList;
    private CheckBox c1;
    private CheckBox c2;
    private CheckBox c3;
    private Map<String, Integer> itemMap;
    private MapAdapter mapAdapter;
    private EditText searchBar;
    private ArrayList<DrawerItem> drawerItems;
    private Context c = this;
    private Activity a = (Activity)c;
    private Button toggleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerItems = new ArrayList<>();
        mapAdapter = new MapAdapter(drawerItems);

        final SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.item_list_file_key), Context.MODE_PRIVATE);
        Map<String, ?> keys = sharedPreferences.getAll();
        if(keys != null) {
            for(Map.Entry<String,?> entry : keys.entrySet()){
                Log.d("map values",entry.getKey() + ": " +
                        entry.getValue().toString());
                mapAdapter.appendToData(entry.getKey(), Integer.valueOf(entry.getValue().toString()));
            }
        }
        setContentView(R.layout.activity_main);

        try{
            setupSparkCore();
        }catch(Exception e){
            Log.e("ERR", e.toString());
        }


        final Button button = (Button) findViewById(R.id.send);
        final Button addButton = (Button) findViewById(R.id.add_item_button);
        toggleBtn = (Button)findViewById(R.id.toggleBtn);

        searchBar = (EditText)findViewById(R.id.search_bar);

        /**
         * POPULATE LISTVIEW
         */
        itemList = (ListView)findViewById(R.id.search_items);
        populateListView();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                c1 = (CheckBox)findViewById(R.id.drawer_1);
                c2 = (CheckBox)findViewById(R.id.drawer_2);
                c3 = (CheckBox)findViewById(R.id.drawer_3);

                String postString = "";

                postString += c1.isChecked() ? "1" : "0";
                postString += c2.isChecked() ? "1" : "0";
                postString += c3.isChecked() ? "1" : "0";

             }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText addItemText = (EditText)findViewById(R.id.add_item_text);
                EditText drawerNumText = (EditText)findViewById(R.id.drawer_number);
                String itemName = addItemText.getText().toString();
                String drawerNum = drawerNumText.getText().toString();

                if(!itemName.equals("") && !drawerNum.equals("")) {
                    mapAdapter.appendToData(itemName, Integer.valueOf(drawerNum));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(itemName, Integer.valueOf(drawerNum));
                    editor.commit();
                    mapAdapter.notifyDataSetChanged();
                }
            }
        });


        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Async.executeAsync(SparkCloud.get(c), new Async.ApiWork<SparkCloud, Integer>(){
                    @Override
                    public Integer callApi(SparkCloud sparkCloud) throws SparkCloudException, IOException {
                        SparkDevice sparkDevice = sparkCloud.getDevice("3f0025000647343232363230");
                        try{
                            sparkDevice.callFunction("turnOn");
                        }catch(Exception e){
                            Log.e("ERR", e.getMessage());
                        }
                        return 1;
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                    }

                    @Override
                    public void onFailure(SparkCloudException e) {
                        Log.d(" ERR",  e.getBestMessage());
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * LAZY STATIC OBJECTS
     */
    private void populateListView(){
        itemList.setAdapter(mapAdapter);
       itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final int ii = i;
               Async.executeAsync(SparkCloud.get(c), new Async.ApiWork<SparkCloud, Integer>() {
                   @Override
                   public Integer callApi(SparkCloud sparkCloud) throws SparkCloudException, IOException {
                       SparkDevice sparkDevice = sparkCloud.getDevice("3f0025000647343232363230");
                       String methodSuffix = "";
                       switch(ii){
                           case(1):
                               methodSuffix = "One";
                               break;
                           case(2):
                               methodSuffix = "Two";
                               break;
                           default:
                               methodSuffix = "";
                               break;
                       }
                       try {
                           sparkDevice.callFunction("turnOn" + methodSuffix);
                       } catch (Exception e) {
                           Log.e("ERR", e.getMessage());
                       }
                       return 1;
                   }

                   @Override
                   public void onSuccess(Integer integer) {
                   }

                   @Override
                   public void onFailure(SparkCloudException e) {
                       Log.d(" ERR", e.getBestMessage());
                   }
               });
           }
       });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.this.mapAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setupSparkCore() throws Exception{
        Async.executeAsync(SparkCloud.get(c), new Async.ApiWork<SparkCloud, Integer>(){
            @Override
            public Integer callApi(SparkCloud sparkCloud) throws SparkCloudException, IOException {
                sparkCloud.logIn(Globals.USERNAME, Globals.PASSWORD);
                SparkDevice sparkDevice = sparkCloud.getDevice("3f0025000647343232363230");
                try{
                    sparkDevice.callFunction("turnOn");
                }catch(Exception e){
                    Log.e("ERR", e.getMessage());
                }
                return 1;
            }

            @Override
            public void onSuccess(Integer integer) {
                Toaster.l(MainActivity.this, "Logged in");
            }

            @Override
            public void onFailure(SparkCloudException e) {
                Log.d(" ERR",  e.getBestMessage());
            }
        });
    }
}
