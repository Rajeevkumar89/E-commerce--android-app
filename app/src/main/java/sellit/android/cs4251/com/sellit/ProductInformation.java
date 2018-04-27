package sellit.android.cs4251.com.sellit;

/**
 * Created by USER on 3/5/2018.
 */

public class ProductInformation {
    public String ProductName;
    public String Description;
    public String Price;
    public String Category;
    public String userEmailID;


    public ProductInformation(){

    }
    public ProductInformation(String ProductName, String Description, String Price,String Category,String userEmailID) {
        this.ProductName=ProductName;
        this.Description=Description;
        this.Price=Price;
        this.Category = Category;
        this.userEmailID=userEmailID;
    }
}
