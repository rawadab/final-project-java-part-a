package Proyect;

/*This class is abstract class that will help us when a user trying to connect to the system, and it will
 use the login method to define the type of the user that trying to get in the system */
abstract public  class ClientFacade {
    protected CompaniesDAO companiesDAO;
    protected CustomerDAO customerDAO;
    protected CouponsDAO couponsDAO;
    public abstract boolean login(String email, String password);


}
