package bruno.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Objects;

import bruno.myapplication.Adapters.SubCategoryCustomAdapter;

public class SubCategoryActivity extends AppCompatActivity {

    SubCategoryCustomAdapter adapter;
    Intent intent;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        intent = getIntent();
        String titleName = intent.getStringExtra("categoryName");
        setTitle(titleName);
        listView = findViewById(R.id.category_listview);

        if(intent.hasExtra("subcategories")){
            Log.d("Intent","it has subcategories");
            adapter =  new SubCategoryCustomAdapter(this,  Objects.requireNonNull(Objects.requireNonNull(intent.getExtras()).getStringArrayList("subcategories")));
            listView.setAdapter(adapter);
        }
    }
}
