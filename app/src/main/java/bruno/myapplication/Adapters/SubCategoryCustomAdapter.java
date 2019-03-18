package bruno.myapplication.Adapters;

import android.content.Context;
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
        String subCategory = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }
        TextView subCategoryName = convertView.findViewById(R.id.text1);
        subCategoryName.setText(subCategory);

        return convertView;
    }
}
