public class Product {
    private String name;
    private double price;
    private String description;
    private String date;
    private int stock;

    public Product(String name, double price, String description, String date , int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.date = date;
        this.stock = stock;
    }

    public Product(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString(){
        return (this.getName() + " -- " + this.getPrice() + " kn -> " +
                this.getDescription() + " -- " + this.getDate() + " --- IN Stock : " + this.getStock());
    }
}
