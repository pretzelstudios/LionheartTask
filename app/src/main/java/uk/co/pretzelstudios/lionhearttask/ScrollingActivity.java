package uk.co.pretzelstudios.lionhearttask;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


    public class ScrollingActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {

        // created string below for JSON Array list

        public static final String EXTRA_URL = "imageUrl";
        public static final String EXTRA_CREATOR = "creatorName";
        public static final String EXTRA_LIKES = "likeCount";

        // referencing my views and adapters

        private RecyclerView mRecyclerView;
        private ExampleAdapter mExampleAdapter;
        private ArrayList<ExampleItem> mExampleList;
        private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // defining my views .setfixed size helps with the performance of the recycler view and stops it lagging.

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setting up my array list for jSON

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }
            // added url to JSON path including my API KEY.
        private void parseJSON() {
            String url = "https://pixabay.com/api/?key=9355841-c32b1483cf2003fdf188b1b2d&q=fast+food+hot&image_type=photo&pretty=true";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                //calling hits array from pixabay
                                JSONArray jsonArray = response.getJSONArray("hits");

                                //looping through the array

                                for (int i = 0; i < jsonArray.length(); i ++) {
                                    JSONObject hit = jsonArray.getJSONObject(i);

                                    String creatorName = hit.getString("user");
                                    String imageUrl = hit.getString("webformatURL");
                                    int likeCount = hit.getInt("likes");

                                    mExampleList.add(new ExampleItem(imageUrl, creatorName, likeCount));
                                }

                                // passing data to my recycler view
                                mExampleAdapter = new ExampleAdapter(ScrollingActivity.this, mExampleList);
                                mRecyclerView.setAdapter(mExampleAdapter);
                                mExampleAdapter.setOnItemClickListener(ScrollingActivity.this);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            mRequestQueue.add(request);
        }

        @Override
        public void onItemClick(int position) {
        //added onclick method to pull the data across to my next activity.
            // This will only be displaying the image however I kept the data in as this could be useful later on if I decided to change my design.
            Intent detailIntent = new Intent(this, DetailActivity.class);
            ExampleItem clickedItem = mExampleList.get(position);

            detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
            detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
            detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

            startActivity(detailIntent);
            //added transition
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
    }
