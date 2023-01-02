package Proyect;

import java.text.DateFormat;
import java.util.Date;
/*In this class we create a constructor and getters and setters to get all the information of the coupons that we have in our database */

public class Coupon {
    private int id;
    private int companyID;
    private int category;
    private String title;
    private String description;
    private Date startDate;

    private Date endDate;
    private int amount;
    private double price;
    private String image;
    private int categoryID;

    public Coupon(int id, int companyID, int category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }/*The main constructor of this class*/
    Coupon( int companyID, int category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {

        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    Coupon(int category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {


        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /*Getters and setters of the class*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {

        this.category= category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return  startDate;
    }

    public void setStartDate(Date startDate) {

        this.startDate = startDate;
    }

    public Date getEndDate() {

        return endDate;
    }

    public void setEndDate(Date endDate) {

        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
       String str ="";

     str +=  "Coupon id = " + id +  "\ncompanyID =" + companyID +
                "\ncategory=" + category +
                "\ntitle: " + title  +
                "\ndescription: " + description +
                "\nstartDate= " + startDate +
                "\nendDate= " + endDate +
                "\namount= " + amount +
                "\nprice= " + price +
                "\nimage: '" + image  + "\n" ;
        return str;
    }/*returning as a string all the details of the coupons */
}
