package bruno.myapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bruno.myapplication.R;

public class SubCategoryCustomAdapter extends ArrayAdapter<String> {

    public SubCategoryCustomAdapter(Context context, ArrayList<String> subCategories) {
        super(context, 0, subCategories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("adapter","started getview");
        // Get the data item for this position
        String subCategory = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }
        // Lookup view for data population
        TextView subCategoryName = convertView.findViewById(R.id.text1);
        // Populate the data into the template view using the data object
        subCategoryName.setText(subCategory);

        Log.d("adapter","finishing adapting");

        // Return the completed view to render on screen
        return convertView;
    }
}
