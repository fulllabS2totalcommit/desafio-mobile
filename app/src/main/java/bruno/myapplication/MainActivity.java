package bruno.myapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bruno.myapplication.Adapters.ProductCustomAdapter;
import bruno.myapplication.api.ApiConnection;
import bruno.myapplication.model.Product;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Product> products;
    ProductCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        products= new ArrayList<>();

        adapter = new ProductCustomAdapter(getApplicationContext(), products);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_id);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(adapter);

        getProducts();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //For Searchview
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getProducts() {

        String url = getString(R.string.URL);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("Offset",0);
            jsonObject.put("Size", 10);
            jsonArray.put(jsonObject);
            Log.i("jsonString", jsonObject.toString());

        }catch(Exception e){

        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(@NonNull JSONObject response) {
                try {
                    // Get current json object
                    JSONArray productsArray = response.getJSONArray("Products");

                    for (int i = 0; i < productsArray.length(); i++) {
                        JSONObject jsonObject = productsArray.getJSONObject(i);
                        JSONArray skus = jsonObject.getJSONArray("Skus");
                        JSONObject skusObject = skus.getJSONObject(0);

                        String name = skusObject.optString("Name");
                        JSONArray sellersArray = skusObject.getJSONArray("Sellers");
                        JSONObject sellerObject= sellersArray.getJSONObject(0);
                        Double finalPrice = sellerObject.optDouble("Price");
                        Double listPrice = sellerObject.optDouble("ListPrice");
                        JSONObject bestInstallmentObject = sellerObject.getJSONObject("BestInstallment");
                        int count = bestInstallmentObject.optInt("Count");
                        Double value = bestInstallmentObject.optDouble("Value");

                        JSONArray imagesArray = skusObject.getJSONArray("Images");
                        JSONObject imagesObject = imagesArray.getJSONObject(0);
                        String thumbnail = imagesObject.optString("ImageUrl");

                        int discount = (int) ((listPrice - finalPrice)/finalPrice);

                        Product product = new Product(discount,
                                name,
                                listPrice,
                                finalPrice,
                                count,
                                value,
                                thumbnail);

//                        TODO: Obtain image thumbnail

                        products.add(product);
                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(@NonNull VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error obtaining data",Toast.LENGTH_LONG).show();
                Log.d("errorJSON",error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                Log.d("headers", headers.toString());
                return headers;
            }
        };
        // Access the RequestQueue through your singleton class.
        int socketTimeout = 70000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        ApiConnection.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}
