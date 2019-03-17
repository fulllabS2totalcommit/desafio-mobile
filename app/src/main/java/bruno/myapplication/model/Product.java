package bruno.myapplication.model;

public class Product {
//    @SerializedName("name")
//    @Expose
    private String name;
//    @SerializedName("price")
//    @Expose
    private String price;
//    @SerializedName("thumbnail")
//    @Expose
    private String thumbnail;

    public Product() {
    }

    public Product(String name, String price, String thumbnail) {
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}