public class ProductDash {
    private String Trans_type;
    private int Quantity;
    private String Name;
    private double Price;
    private String Date;

    public ProductDash(){

    }

    public ProductDash(String trans_type, int quantity, String name, double price, String date) {
        this.Trans_type = trans_type;
        this.Quantity = quantity;
        this.Name = name;
        this.Price = price;
        this.Date = date;
    }

    public String getTrans_type() {
        return Trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.Trans_type = trans_type;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        this.Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    @Override
    public String toString(){
        return (this.getTrans_type() + " -- " + this.getQuantity() + " --> " +
                this.getName() + " --> " + (this.getQuantity()*this.getPrice()) + " kn --> " + this.getDate());
    }

}
