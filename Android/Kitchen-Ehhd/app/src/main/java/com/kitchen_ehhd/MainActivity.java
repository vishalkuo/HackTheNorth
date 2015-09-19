package com.kitchen_ehhd;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.kitchen_ehhd.Models.Globals;
import com.kitchen_ehhd.Models.MockSearchItems;
import com.kitchen_ehhd.Services.APIService;

import java.util.Map;

import retrofit.RestAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.send);
        populateListView();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox c1 = (CheckBox)findViewById(R.id.drawer_1);
                CheckBox c2 = (CheckBox)findViewById(R.id.drawer_2);
                CheckBox c3 = (CheckBox)findViewById(R.id.drawer_3);

                String postString = "";

                postString += c1.isChecked() ? "1" : "0";
                postString += c2.isChecked() ? "1" : "0";
                postString += c3.isChecked() ? "1" : "0";

                new RESTCall(postString).execute();
//                apiService.openDrawerTask2(new Callback<String>() {
//                    @Override
//                    public void success(String s, Response response) {
//
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.d("JERRY: retrofit error", error.getMessage());
//                    }
//                });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateListView(){
        Map<String, Integer> itemMap = new MockSearchItems().getItemToDrawerMap();

    }

    private class RESTCall extends AsyncTask<Void, Void, Void> {
        private String req;

        public RESTCall(String req) {
            this.req = req;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(Globals.CONFIGURL)
                    .build();

            APIService apiService = restAdapter.create(APIService.class);

            apiService.resultList();
            return null;
        }
    }
}
