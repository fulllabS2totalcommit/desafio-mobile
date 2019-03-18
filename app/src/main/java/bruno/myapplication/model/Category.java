package bruno.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Category implements Parcelable {

    private String name;
    private ArrayList<String> subcategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSubcategory() {
        return subcategory;
    }

    protected Category(Parcel in) {
        name = in.readString();
        subcategory = in.readParcelable(Category.class.getClassLoader());
    }

    public Category(String name){
        this.name = name;
        this.subcategory = new ArrayList<>();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable((Parcelable) subcategory, flags);
    }
}
