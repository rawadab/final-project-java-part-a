package Proyect;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
/*This class extends from ClientFacade class and the methods in this class will use the method's in the DAO classes to do
 * changes in the  coupons of the customer */
public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade(String email) throws ClientNotExsit {

        companiesDAO = new CompaniesDBDAO();
        customerDAO = new CustomersDBDAO();
        couponsDAO = new CouponsDBDAO();
        ArrayList<Customer> customers = customerDAO.getAllCustomers();
        for (Customer coustomer: customers) {
            if(coustomer.getEmail().equals(email)){
                customerID = coustomer.getId();
            }

        }
        if(customerID == 0){
            throw new ClientNotExsit();
        }
    }/*constructor of the class will reserve email of the customer and will check if the customer in our database if true we will
     get the id of the customer and use it in the methode of the class , else will throw ClientNotExsit (a class extends from exception)*/

    @Override
    public boolean login(String email, String password) {
        if (customerDAO.isCustomerExsist(email, password)) {
            Customer customer = customerDAO.getOneCustomer(email,password);
            this.customerID = customer.getId();
            return true;
        } else {
            return false;
        }
    }/*This methode will check if the user that trying to connect is type customer and if he is a customer he will content to the
    system, and he can do evey thing  just in this class */
      public void purchaseCoupon (Coupon coupon) {
          try {
              // Check if coupon has already been purchased
              ArrayList<Coupon> customerCoupons = couponsDAO.getAllCoupons();
              if (customerCoupons.contains(coupon)) {
                  throw new purchAllready();
              }

              // Check if coupon is out of stock
              if (coupon.getAmount() == 0) {
                  throw new AmountPurchase();
              }

              Date date = new Date();
              // Check if coupon has expired
              if (coupon.getEndDate().before(date)) {
                  throw new DatePurchase();
              }

              // Purchase coupon
              couponsDAO.addCouponPurchase(customerID, coupon.getId());

              // Decrease coupon quantity by 1
              coupon.setAmount(coupon.getAmount() - 1);
          } catch (IllegalArgumentException e) {
              System.out.println(e.getMessage());
          } catch (purchAllready e) {
              System.out.println(e.getMessage());
          } catch (AmountPurchase e) {
              System.out.println(e.getMessage());
          } catch (DatePurchase e) {
              System.out.println(e.getMessage());
          }
      }/*this method will purchase a coupon but before thar it will check if the coupon purchAllready, amount==0, and the end date is true
      if their any this is not correct it will throw exception according to the problem (purchAllready,AmountPurchase,DatePurchase) each exptin
      is a class that we build that extends from exception and will print a massage and if there is no problem it will use
          couponsDAO.addCouponPurchase(customerID, coupon.getId()); and purchase thew coupon*/

    public ArrayList<Coupon> getCustomerCoupons() {

        return customerDAO.getOneCustomer(customerID).getCoupons();

    }/*will return all the coupons of the customer we will send the customer id*/

    public ArrayList<Coupon> getCustomerCoupons(Category category) {
        ArrayList<Coupon> cop = getCustomerCoupons();
        cop.removeIf(e->e.getCategory() != category.getId()  );
    return cop;

    }/*this method will receive all the coupons of the customer in our database after that will be filtered by the category id and each coupon that
     related to the category he will return it  */

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice){
        ArrayList<Coupon> cop = getCustomerCoupons();
        Iterator<Coupon> iterator = cop.iterator();
        while (iterator.hasNext()) {
            Coupon coupon = iterator.next();
            if (coupon.getPrice() >= maxPrice) {
                iterator.remove();
            }
        }
        return cop;


    }/*this method will receive all the coupons of the customer in our database after that will be filtered by the  max price
    and each coupon that related to the max price he will return it  */
        public Customer getCustomerDetails () {
            return customerDAO.getOneCustomer(customerID);
        }

    }/*this method will return the details of the customer also all the coupons that is for this customer we send the id of the
    customer, we can also print hsi details*/
