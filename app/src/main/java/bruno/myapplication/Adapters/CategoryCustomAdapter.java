package bruno.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import bruno.myapplication.R;
import bruno.myapplication.model.Category;

public class CategoryCustomAdapter extends ArrayAdapter<Category> {

    public CategoryCustomAdapter(Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }
        TextView categoryName = convertView.findViewById(R.id.text1);
        categoryName.setText(Objects.requireNonNull(category).getName());

        return convertView;
    }
}
