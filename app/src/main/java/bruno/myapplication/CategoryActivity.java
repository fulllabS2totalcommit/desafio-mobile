package bruno.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import bruno.myapplication.Adapters.CategoryCustomAdapter;
import bruno.myapplication.api.ApiConnection;
import bruno.myapplication.model.Category;

public class CategoryActivity extends AppCompatActivity {

    private String baseURL;
    private ArrayList<Category> arrayOfCategory;
    private CategoryCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ListView listView = findViewById(R.id.category_listview);

        baseURL = getString(R.string.categoryURL);
        arrayOfCategory  = new ArrayList<>();
        adapter = new CategoryCustomAdapter(this, arrayOfCategory);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> subCategoryArray = arrayOfCategory.get(position).getSubcategory();
                Intent subCategoryIntent =  new Intent(getApplicationContext(), SubCategoryActivity.class);
                subCategoryIntent.putExtra("subcategories", subCategoryArray);
                subCategoryIntent.putExtra("categoryName", arrayOfCategory.get(position).getName());
                startActivity(subCategoryIntent);
            }
        });
        getCategoriesAndSubcategories();
    }

    private void getCategoriesAndSubcategories() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        readCategoryJSON(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error obtaining data",Toast.LENGTH_LONG).show();
                    }
                });
        int socketTimeout = 70000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        ApiConnection.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void readCategoryJSON(JSONObject response) {
        try {
            JSONArray categoriesArray = response.getJSONArray("Categories");
            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject jsonObject = categoriesArray.getJSONObject(i);
                String categoryName = jsonObject.optString("Name");
                JSONArray subCategoriesArray = jsonObject.getJSONArray("SubCategories");

                Category category = new Category(categoryName);

                for (int j = 0; j < subCategoriesArray.length(); j++) {
                    JSONObject jsonSubcategoryObject = subCategoriesArray.getJSONObject(j);
                    JSONObject redirectObject = jsonSubcategoryObject.getJSONObject("Redirect");
                    String subCategoryName = redirectObject.optString("Title");

                    category.getSubcategory().add(subCategoryName);
                }
                arrayOfCategory.add(category);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
