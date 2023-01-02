package Proyect;

import java.util.ArrayList;
/*In this class we create a constructor and getters and setters to get all the information of our customers */
public class Customer {
    private int id;
    private String FirstName;
    private String LastName;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    public Customer(int id, String firstName, String lastName, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }/*The main constructor of this class*/

    public Customer( String firstName, String lastName, String email, String password) {

        FirstName = firstName;
        LastName = lastName;
        this.email = email;
        this.password = password;
    }/* constructor of this class but without the id and the coupons*/
    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        this.email = email;
        this.password = password;
    }/* constructor of this class but without the coupons*/
/*Getters and setters of the class*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
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


        str += "Customer id= " + id + "\nFirstName: " + FirstName + "\nLastName: " + LastName + "\nemail: " + email +
                "\npassword:" + password +  "\n" +
                "\nCoustomer all coupons is = " + coupons + "\n";
        return str;
    }/*returning as a string all the details of the customers */
}
