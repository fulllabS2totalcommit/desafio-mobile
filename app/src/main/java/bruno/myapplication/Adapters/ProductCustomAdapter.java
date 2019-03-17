package bruno.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bruno.myapplication.R;
import bruno.myapplication.model.Product;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ProductCustomAdapter extends RecyclerView.Adapter<ProductCustomAdapter.ViewHolder>{

    private final Context mContext;
    private final Activity activity;
    private ArrayList<Product> mDataSet;


    public ProductCustomAdapter(@NonNull Context context, @NonNull ArrayList<Product> data, Activity activity) {
        this.mDataSet = data;
        this.activity = activity;
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

        holder.discountProduct.setText(discount);
        holder.nameProduct.setText(name);
        holder.discountProduct.setText(discount);
        holder.listPriceProduct.setText(decimalFormat.format(listPrice));
        holder.finalPriceProduct.setText(decimalFormat.format(finalPrice));
        holder.bestInstallmentProduct.setText(count+"x R$"+value);
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

        //Aca recibo un elemento en particular (una row si se quiere), y hago el binding entre los campos de este elemento y los del xml del diagnostico_item. Es medio "Generico" por asi decirlo.
        public ViewHolder(View itemView) {
            super(itemView);
            discountProduct = itemView.findViewById(R.id.tv_discount);
            nameProduct = itemView.findViewById(R.id.tv_product_name);
            listPriceProduct = itemView.findViewById(R.id.tv_list_price);
            finalPriceProduct = itemView.findViewById(R.id.tv_final_price);
            bestInstallmentProduct = itemView.findViewById(R.id.tv_installment_option);
//            thumbnailProduct = itemView.findViewById(R.id.thumbnail);
        }

    }
}
