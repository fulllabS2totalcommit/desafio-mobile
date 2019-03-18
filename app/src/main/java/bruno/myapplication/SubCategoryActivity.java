package bruno.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Objects;

public class SubCategoryActivity extends AppCompatActivity {

    ArrayAdapter<String> simpleArrayAdapter;
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
            simpleArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Objects.requireNonNull(Objects.requireNonNull(intent.getExtras()).getStringArrayList("subcategories")));
            listView.setAdapter(simpleArrayAdapter);
        }
    }
}
