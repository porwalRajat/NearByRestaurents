package com.example.rajat.nearby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.rajat.nearby.AppConstants.ICON;
import static com.example.rajat.nearby.AppConstants.NAME;
import static com.example.rajat.nearby.AppConstants.OK;
import static com.example.rajat.nearby.AppConstants.RATING;
import static com.example.rajat.nearby.AppConstants.STATUS;
import static com.example.rajat.nearby.AppConstants.VICINITY;
import static com.example.rajat.nearby.AppConstants.ZERO_RESULTS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    Button sortByRating;
    ArrayList<com.example.rajat.nearby.Places> placesList= new ArrayList<>();
    private PlaceAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        sortByRating= (Button)findViewById(R.id.btn_ratingSort);
        sortByRating.setOnClickListener(this);

        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String json= getIntent().getStringExtra("json");
        if (json!=null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                parseLocationResult(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseLocationResult(JSONObject result) {
        String rating = null, placeName = null, icon, vicinity = null;

        try {
            JSONArray jsonArray = result.getJSONArray("results");

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);
                    if (!place.isNull(RATING))
                        rating = place.getString(RATING);
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);
                    }
                    if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY);
                    }
                    icon = place.getString(ICON);
                    placesList.add(new com.example.rajat.nearby.Places(placeName,vicinity,rating,icon));
                    Log.e("Res ",placeName + " : " + vicinity+ " : "+rating);
                }
                showData();
                Toast.makeText(getBaseContext(), jsonArray.length() + " Restaurants found!",
                        Toast.LENGTH_LONG).show();
            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No Restaurants found in 10KM radius!!!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e( "parseLocationResult:", e.getMessage());
        }
    }

    public void showData() {
       adapter = new PlaceAdaptor(placesList,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_ratingSort) {
            Collections.sort(placesList,new MyCustomComparator());
            adapter.notifyDataSetChanged();
        }
    }

    private static class MyCustomComparator implements Comparator<Places> {
        @Override
        public int compare(Places lhs,
                           Places rhs) {
           return lhs.getRating().compareTo(rhs.getRating());

        }

    }
}
