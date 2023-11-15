package com.example.android.barbera;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class providers extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ListView listView;
    ArrayList<String> companyList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);

        toolbar = findViewById(R.id.toolbar_id);
        bottomNavigationView = findViewById(R.id.bottom_id);
        listView = findViewById(R.id.list);
        companyList = new ArrayList<>();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        extractData();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home_id)
            {
                Intent intent = new Intent(providers.this, homePage.class);
                startActivity(intent);
                finish();

            }
            else if(item.getItemId() == R.id.share)
            {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Download BarberA";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share"));
                return true;
            }

            else if(item.getItemId() == R.id.about_usid){
                Intent intent = new Intent(providers.this, about__us.class);
                startActivity(intent);
            }
            return true;
        });
    }

    private void extractData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.myjson.online/v1/records/3c30f62b-3918-4376-9218-15e7d6b4e2ee";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonParse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error if needed
            }
        });
        requestQueue.add(stringRequest);
    }

    private void jsonParse(String response) {
        try {
            companyList = new ArrayList<>();
            final ArrayList<Address> addresses = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String get_cname = jsonObject1.getString("name");
                companyList.add(get_cname);

                JSONObject addressObject = jsonObject1.getJSONObject("address");

                String city = addressObject.getString("City");
                String lane = addressObject.getString("Lane");
                String road = addressObject.getString("Road");
                String house = addressObject.getString("House");

                Address address = new Address(city, lane, road, house);
                addresses.add(address);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(providers.this, android.R.layout.simple_list_item_1, companyList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Intent intent = new Intent(providers.this, AddressActivity.class);
                    intent.putExtra("companyName", companyList.get(i));
                    intent.putExtra("address", addresses.get(i));
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}