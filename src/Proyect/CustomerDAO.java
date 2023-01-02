package Proyect;

import java.util.ArrayList;
/*Creating interface and define methods that we can use them in CustomersDBDAO  */
public interface CustomerDAO {

    public boolean isCustomerExsist(String email, String password);
    public void addCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public void deleteCustomer(int customerID);
    public ArrayList<Customer> getAllCustomers();
    public Customer getOneCustomer(int customerID);

 public Customer getOneCustomer(String email, String password);
}
