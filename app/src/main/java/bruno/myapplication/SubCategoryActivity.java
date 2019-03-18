package bruno.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Objects;

import bruno.myapplication.Adapters.SubCategoryCustomAdapter;

public class SubCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Intent intent = getIntent();
        String titleName = intent.getStringExtra("categoryName");
        setTitle(titleName);
        ListView listView = findViewById(R.id.category_listview);

        if(intent.hasExtra("subcategories")){
            SubCategoryCustomAdapter adapter = new SubCategoryCustomAdapter(this, Objects.requireNonNull(Objects.requireNonNull(intent.getExtras()).getStringArrayList("subcategories")));
            listView.setAdapter(adapter);
        }
    }
}
