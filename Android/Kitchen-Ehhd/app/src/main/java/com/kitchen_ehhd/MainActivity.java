package com.kitchen_ehhd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.kitchen_ehhd.Models.MockSearchItems;
import com.kitchen_ehhd.Services.APIService;
import com.kitchen_ehhd.VIewAdapters.MapAdapter;

import java.util.ArrayList;
import java.util.Map;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        final Button button = (Button) findViewById(R.id.send);
        final Button addButton = (Button) findViewById(R.id.add_item_button);

        searchBar = (EditText)findViewById(R.id.search_bar);

        /**
         * POPULATE LISTVIEW
         */
        itemList = (ListView)findViewById(R.id.search_items);
        //itemMap = new MockSearchItems().getItemToDrawerMap();
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

                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(Globals.CONFIGURL)
                        .build();
                APIService apiService = restAdapter.create(APIService.class);
                apiService.openDrawerTask(new Drawer(postString), new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("JERRY: retrofit error", error.getMessage());
                    }
                });

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
        mapAdapter = new MapAdapter(drawerItems);
        itemList.setAdapter(mapAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("LOL", drawerItems.get(i).getName());
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

    private void populateDrawerItems() {
        drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem("Fork", 1));
        drawerItems.add(new DrawerItem("Fork 2", 2));
        drawerItems.add(new DrawerItem("Knife", 3));
        drawerItems.add(new DrawerItem("Table", 1));
        drawerItems.add(new DrawerItem("Rain", 2));
        drawerItems.add(new DrawerItem("Tester", 2));
    }
    private class RESTCall extends AsyncTask<Void, Void, Void>{
        private String req;

        public RESTCall(String req) {
            this.req = req;
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}
