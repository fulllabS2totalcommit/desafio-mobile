package bruno.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

public class Product implements Parcelable {
    private int discount;
    private String thumbnail;
    private String name;
    private double listPrice;
    private double finalPrice;
    private int count;
    private double value;

    public Product() {
    }

    public Product(int discount, String name, double listPrice, double finalPrice, int count, double value, String thumbnail) {
        this.name = name;
        this.discount = discount;
        this.listPrice = listPrice;
        this.finalPrice = finalPrice;
        this.count = count;
        this.value = value;
        this.thumbnail = thumbnail;

        Log.d("Product","creating product");
    }
    private Product(@NonNull Parcel in) {
        name = in.readString();
        discount = in.readInt();
        listPrice = in.readDouble();
        finalPrice = in.readDouble();
        count = in.readInt();
        value = in.readDouble();
        thumbnail = in.readString();
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(@NonNull Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(discount);
        dest.writeDouble(listPrice);
        dest.writeDouble(finalPrice);
        dest.writeInt(count);
        dest.writeDouble(value);
        dest.writeString(thumbnail);
    }
}