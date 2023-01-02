package Proyect;

import java.util.ArrayList;
/*In this class we create a constructor and getters and setters to get all the information of all the companies in our database */
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;
//Company(){}
    public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }/*The main constructor of this class*/

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }
    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    /*Getters and setters of the class*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
    String str ="";

            str+= "company id =" + id + "\ncompany name='" + name  + "\ncompany email='" + email +
                    "\ncompany password='" + password  + ".\n" +
                    "coupons of the company=" + coupons + "\n";

    return str;
    }/*returning as a string all the details of the companies */
}
