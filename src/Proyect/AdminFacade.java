package Proyect;

import java.sql.SQLException;
import java.util.*;
/*This class extends from ClientFacade class and the methods in this class will use the method's in the DAO classes to do
* changes in the companies, customers and the coupons */
public class AdminFacade extends ClientFacade {


    public AdminFacade() {
    companiesDAO = new CompaniesDBDAO();
    customerDAO = new CustomersDBDAO();
    couponsDAO = new CouponsDBDAO();
    }/*constructor of the class*/
    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        }
        return false;
    }/*This methode will check if the user that trying to connect is type admin and if ghe is an admin he will content to the
    system, and he can do evey thing, after that the admin get in the system he has full authority to do changes */


    /*this methods will use with the help of companiesDAO*/
    public void addCompany(Company company) throws SQLException {
        if (companiesDAO.getOneCompany(company.getId()) != null) {
            if (companiesDAO.getOneCompany(company.getId()).getName().matches(company.getName())) {

                companiesDAO.addCompany(company);
            }
        }
        else {


                companiesDAO.addCompany(company);
            }
        }/*in this method will add a new company to the database according to the conditions with the help of the DAO class*/



    public void updateCompany(Company company){
            companiesDAO.updateCompany(company);
    }
    /* will update the details of the company   with the help of the DAO class*/
    public void deleteCompany(int companyID) throws SQLException{

            companiesDAO.deleteCompany(companyID);



    }/*deleting an exist company from the database we will send the id of the company and will search for the company id in the
     database after that the company will be deleted*/
    public ArrayList<Company> getAllCompanies() {
        return companiesDAO.getAllCompanies();
    }/*this method will return all the companies with all the details of each company, also we can print this details */

    public Company getOneCompany(int companyID) {
        return companiesDAO.getOneCompany(companyID);
    }/*this method will return one company with all the details of the company, we will send the id of the company that we want to see her
    details, also we can print this details */


    /*this methods will use with the help of customerDAO*/
    public void addCustomer(Customer customer) throws  SQLException{

            customerDAO.addCustomer(customer);

    }/*this method will add a customer to the database with the help of the customerDAO */

    public void updateCustomer(Customer customer) {


            customerDAO.updateCustomer(customer);

    }/*will update the details of the customer  with the help of the DAO class*/
    public void deleteCustomer(int customerID) {

            customerDAO.deleteCustomer(customerID);

    }/*deleting an exist customer from the database we will send the id of the customer and will search for the customer id in the
     database after that the customer will delete*/

    public ArrayList<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }/*this method will return all the customers with all the details of each customer, also we can print this details */

    public Customer getOneCustomer(int customerID) {
        return customerDAO.getOneCustomer(customerID);
    }/*this method will return one customer with all the details of the customer, we will send the id of the customer that we want to see her
    details, also we can print this details */
}




