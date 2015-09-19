package com.kitchen_ehhd;

import android.app.Activity;
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
import com.kitchen_ehhd.Models.Globals;
import com.kitchen_ehhd.Models.MockSearchItems;
import com.kitchen_ehhd.Services.APIService;
import com.kitchen_ehhd.VIewAdapters.MapAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.send);

        searchBar = (EditText)findViewById(R.id.search_bar);

        /**
         * POPULATE LISTVIEW
         */
        itemList = (ListView)findViewById(R.id.search_items);
        itemMap = new MockSearchItems().getItemToDrawerMap();
        mapAdapter = new MapAdapter(itemMap);
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
        MapAdapter adapter = new MapAdapter(itemMap);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("LOL", itemMap.keySet().toArray()[i].toString());
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
