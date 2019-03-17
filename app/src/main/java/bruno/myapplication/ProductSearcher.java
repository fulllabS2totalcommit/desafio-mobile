package bruno.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
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

public class ProductSearcher {

    private ArrayList<Product> products;
    private ProductCustomAdapter adapter;
    private String url = MainActivity.getContext().getString(R.string.URL);

    public ProductSearcher(ProductCustomAdapter adapter, ArrayList<Product> products){
        this.products = products;
        this.adapter = adapter;
    }

    private JSONObject formatQuery(String query, int offset, int size){
        JSONObject jsonObject = new JSONObject();

        try{
            if(!query.isEmpty()){
                jsonObject.put("Query", query);
            }
            jsonObject.put("Offset",offset);
            jsonObject.put("Size", size);
            Log.i("jsonString", jsonObject.toString());
        }catch(Exception e){
            Log.d("JSON Error", e.getMessage());
        }
        return jsonObject;
    }

    public ArrayList<Product> getProducts(String query, int offset, int size) {

        JSONObject jObject = formatQuery(query, offset, size);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jObject, new Response.Listener<JSONObject>() {

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
                Toast.makeText(MainActivity.getContext(),"Error obtaining data",Toast.LENGTH_LONG).show();
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
        int socketTimeout = 70000;//70 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        ApiConnection.getInstance(MainActivity.getContext()).addToRequestQueue(jsObjRequest);
        return products;
    }
}
