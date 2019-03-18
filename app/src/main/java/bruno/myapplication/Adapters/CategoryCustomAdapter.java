package bruno.myapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import bruno.myapplication.R;
import bruno.myapplication.model.Category;

public class CategoryCustomAdapter extends ArrayAdapter<Category> {

    public CategoryCustomAdapter(Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("adapter","started getview");
        // Get the data item for this position
        Category category = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }
        // Lookup view for data population
        TextView categoryName = convertView.findViewById(R.id.category_name);
        // Populate the data into the template view using the data object
        categoryName.setText(category.getName());

        Log.d("adapter","finishing adapting");

//        // Cache row position inside the button using `setTag`
//        convertView.setTag(position);
//        // Attach the click event handler
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = (Integer) view.getTag();
//                // Access the row position here to get the correct data item
//                Category category = getItem(position);
//                // Do what you want here...
//                ArrayList<String> listOfSubcategory = category.getSubcategory();
//
//
//            }
//        });
        // Return the completed view to render on screen
        return convertView;
    }
}
