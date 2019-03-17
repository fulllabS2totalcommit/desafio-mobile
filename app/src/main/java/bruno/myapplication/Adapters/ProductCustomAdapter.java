package bruno.myapplication.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bruno.myapplication.R;
import bruno.myapplication.model.Product;

public class ProductCustomAdapter extends RecyclerView.Adapter<ProductCustomAdapter.ViewHolder>{

    private final Context mContext;
    private ArrayList<Product> mDataSet;


    public ProductCustomAdapter(@NonNull Context context, @NonNull ArrayList<Product> data) {
        this.mDataSet = data;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.product_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ImageView thumbnail;
        String name = mDataSet.get(position).getName();
        double listPrice = mDataSet.get(position).getListPrice();
        double finalPrice = mDataSet.get(position).getFinalPrice();
        int count = mDataSet.get(position).getCount();
        double value = mDataSet.get(position).getValue();
        int discount = mDataSet.get(position).getDiscount();

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        holder.discountProduct.setText(String.format("%s%% \nOff", String.valueOf(discount)));
        holder.nameProduct.setText(name);
        holder.nameProduct.setTypeface(null, Typeface.BOLD);
        holder.listPriceProduct.setText(String.format("R$ %s", String.valueOf(decimalFormat.format(listPrice))));
        holder.listPriceProduct.setPaintFlags(holder.listPriceProduct.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.listPriceProduct.setTextColor(Color.GRAY);
        holder.listPriceProduct.setTypeface(null, Typeface.ITALIC);
        holder.finalPriceProduct.setText(String.format("R$ %s", String.valueOf(decimalFormat.format(finalPrice))));
        holder.finalPriceProduct.setTextColor(Color.GREEN);
        holder.bestInstallmentProduct.setText(String.format("%sx R$%s", String.valueOf(count), String.valueOf(decimalFormat.format(value))));
        holder.bestInstallmentProduct.setTextColor(Color.GREEN);
//        holder.thumbnailProduct.setImageBitmap();

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.product_card;
    }

    // View lookup cache
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView discountProduct;
        TextView nameProduct;
        TextView listPriceProduct;
        TextView finalPriceProduct;
        TextView bestInstallmentProduct;
        ImageView thumbnailProduct;
        CardView mCardView;



        //Aca recibo un elemento en particular (una row si se quiere), y hago el binding entre los campos de este elemento y los del xml del diagnostico_item. Es medio "Generico" por asi decirlo.
        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("viewholder","viewHolder constructor");
            discountProduct = itemView.findViewById(R.id.tv_discount);
            nameProduct = itemView.findViewById(R.id.tv_product_name);
            listPriceProduct = itemView.findViewById(R.id.tv_list_price);
            finalPriceProduct = itemView.findViewById(R.id.tv_final_price);
            bestInstallmentProduct = itemView.findViewById(R.id.tv_installment_option);
//            thumbnailProduct = itemView.findViewById(R.id.thumbnail);
            mCardView = itemView.findViewById(R.id.cv_item);
        }

    }
}
