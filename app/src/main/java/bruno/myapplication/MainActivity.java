package bruno.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import java.util.Objects;

import bruno.myapplication.Adapters.ProductCustomAdapter;
import bruno.myapplication.api.ApiConnection;
import bruno.myapplication.model.Product;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Product> products;
    private ProductCustomAdapter adapter;

    private String query;
    private int offset=0;
    private final int size=10;

    private boolean loadingContentFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingContentFlag = false;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        query = "";

        products= new ArrayList<>();

        adapter = new ProductCustomAdapter(getApplicationContext(), products);

        final RecyclerView mRecyclerView = findViewById(R.id.recyclerview_id);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    final int visibleThreshold = 2;

                    GridLayoutManager layoutManager = (GridLayoutManager)mRecyclerView.getLayoutManager();
                    int lastItem  = Objects.requireNonNull(layoutManager).findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = layoutManager.getItemCount();

                    if(currentTotalCount <= lastItem + visibleThreshold && !loadingContentFlag){
                        offset+=10;
                        getProducts(query, offset, size);
                    }
                }
            }
        });

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            offset=0;
            products.clear();
            adapter.notifyDataSetChanged();
        }
        getProducts(query, offset, size);
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
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(MainActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            Intent categoryIntent = new Intent(this, CategoryActivity.class);
            startActivity(categoryIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getProducts(String mQuery, int offset, int size) {

        if(!loadingContentFlag){
            loadingContentFlag = true;
            String url = getString(R.string.URL);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            try{
                if(!mQuery.isEmpty()){
                    jsonObject.put("Query",mQuery);
                }
                jsonObject.put("Offset",offset);
                jsonObject.put("Size", size);
                jsonArray.put(jsonObject);
                Log.i("jsonString", jsonObject.toString());

            }catch(Exception e){
                Log.e("error", e.getMessage());
            }

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(@NonNull JSONObject response) {
                    try {
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

                            int discount = (int) ((listPrice - finalPrice)/listPrice*100);

                            Product product = new Product(discount,
                                    name,
                                    listPrice,
                                    finalPrice,
                                    count,
                                    value,
                                    thumbnail);

                            products.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadingContentFlag = false;
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(@NonNull VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error obtaining data",Toast.LENGTH_LONG).show();
                    loadingContentFlag = false;
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            int socketTimeout = 70000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            ApiConnection.getInstance(this).addToRequestQueue(jsObjRequest);
        }
    }
}
