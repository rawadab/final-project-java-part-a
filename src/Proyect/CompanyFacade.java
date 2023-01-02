package Proyect;

import java.util.ArrayList;
import java.util.Iterator;
/*This class extends from ClientFacade class and the methods in this class will use the method's in the DAO classes to do
 * changes in the  coupons of the company */
public class CompanyFacade extends ClientFacade {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
        private int companyId;
    public CompanyFacade(String email) throws ClientNotExsit {

        companiesDAO = new CompaniesDBDAO();
        customerDAO = new CustomersDBDAO();
        couponsDAO = new CouponsDBDAO();
        ArrayList<Company> companies = companiesDAO.getAllCompanies();
        for (Company company: companies) {
            if(company.getEmail().equals(email)){
                companyId = company.getId();
            }

        }
        if(companyId == 0){
            throw new ClientNotExsit();
        }
    }/*constructor of the class will reserve email of the company and will check if the company in our database if true we will
     get the id of the company and use it in the methode of the class , else will throw ClientNotExsit (a class extends from exception)*/



    @Override
    public boolean login(String email, String password) {

        if (companiesDAO.isCompanyExists(email, password)) {
            Company company= companiesDAO.getOneCompany(email, password);

            return true;

        }
        return false;
    }/*This methode will check if the user that trying to connect is type company and if he is a company he will content to the
    system, and he can do evey thing (within)*/

    public void addCoupon(Coupon coupon) {

        couponsDAO.addCoupon(coupon);
    }/*in this method will add a new coupon to the database according to the conditions with the help of the DAO class*/

    public void updateCoupon(Coupon coupon) {
        couponsDAO.updateCoupon(coupon);
    }
    /* will update the details of the coupon with the help of the DAO class*/
    public  void deleteCoupon(int couponId){

        couponsDAO.deleteCoupon(couponId);
    }  /*deleting an exist coupon from the database we will send the id of the coupon and will search for the coupon id in the
     database after that the coupon will be deleted*/
    public ArrayList<Coupon> getCompanyCoupons() {

        ArrayList<Coupon> cop=couponsDAO.getAllCoupons();
        cop.removeIf(e->e.getCompanyID()!=companyId);
        return cop;
    }/*this method will receive all the coupons in our database after that will be filtered by the company id and each coupon that
     related to the company he will return it  */
    public ArrayList<Coupon> getCompanyCoupons(Category category){
        ArrayList<Coupon> cop = getCompanyCoupons();
        cop.removeIf(e->e.getCategory() != category.getId()  );


        return cop;
    }/*this method will receive all the coupons in our database after that will be filtered by the category that we want and each coupon that
     related to the category that we entered he will return it  */
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice){
        ArrayList<Coupon> cop = getCompanyCoupons();
        Iterator<Coupon> iterator = cop.iterator();
        while (iterator.hasNext()) {
            Coupon coupon = iterator.next();
            if (coupon.getPrice() >= maxPrice) {
                iterator.remove();
            }
        }
        return cop;


    }/*this method will receive all the coupons in our database after that will be filtered by the max price that we have entered and each
    coupon that related to the max price he will return it  */
    public Company getCompanyDetails(){
       return companiesDAO.getOneCompany(companyId);
    }
    /*will return  the company details, we will send the company id to companiesDAO.getOneCompany after that we will get all the details of the company  */

}

