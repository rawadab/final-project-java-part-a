package Proyect;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
/*in this class we will test all the methods that we have create in all classes*/
public class Test {
    public void testAll() throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {

            CouponExpirationDailyJob thread = new CouponExpirationDailyJob();
            thread.run();//thread star working
            LoginManager loginManager = new LoginManager();
            AdminFacade adminFacade = (AdminFacade) loginManager.login(ClientType.Administrator, "admin@admin.com", "admin");
            testAdminFacade(adminFacade);//calling testAdminFacade methode that will test all the methodes in adminFacade


            CompanyFacade companyFacade = (CompanyFacade) loginManager.login(ClientType.Company, "fifa@fifa", "FIFA");
            testCompanyFacade(companyFacade, adminFacade);//calling testCompanyFacade methode that will test all the methodes in companyFacade


            CustomerFacade customerFacade = (CustomerFacade) loginManager.login(ClientType.Customer, "rawadabdalla@gmail.com", "987654321");
            testCustomerFacade(customerFacade);//calling testCustomerFacade methode that will test all the methodes in customerFacade


            thread.stop();//thread stop working

        } catch (Exception e) {

            System.out.println(e.getMessage());
        } finally {
            ;
            connectionPool.closeConnection();

        }

    }


    private static void testAdminFacade(AdminFacade adminFacade) throws SQLException {
        adminFacade.addCompany(new Company("Nike", "Nike@nike", "Nike"));
        adminFacade.addCompany(new Company("Fifa", "fifa@fifa", "FIFA"));
        adminFacade.addCompany(new Company("WWE", "WWE@wwe", "WWEW"));
        adminFacade.addCompany(new Company("Macdonals", "Macdonals@Macdonals", "Macdonals"));
        adminFacade.addCompany(new Company("Kfc", "Kfc@kfc", "Kfc"));

        ArrayList<Company> companies = adminFacade.getAllCompanies();
        adminFacade.updateCompany(new Company(companies.get(0).getId(), "Nike", "Nike00@nike", "Nike"));
        adminFacade.updateCompany(new Company(companies.get(0).getId(), "Nike", "Nike00@nike", "Nike"));
        adminFacade.updateCompany(new Company(companies.get(3).getId(), "Macdonals", "Macdonals1564@Macdonals", "Macdonalsisgood"));
        adminFacade.deleteCompany(companies.get(4).getId());

        System.out.println(adminFacade.getAllCompanies());
        System.out.println(adminFacade.getOneCompany(companies.get(3).getId()));
        /* ******************************************************************** */


        adminFacade.addCustomer(new Customer("rawad", "abdalla", "rawadabdalla@gmail.com", "987654321"));
        adminFacade.addCustomer(new Customer("saed", "qzl", "saedqzl@gmail.com", "654fff"));
        adminFacade.addCustomer(new Customer("toto", "abo", "mota@gmail.com", "tree988"));
        adminFacade.addCustomer(new Customer("mohsen", "abdalla", "mohsenab@gmail.com", "jdudjd"));
        ArrayList<Customer> customers = adminFacade.getAllCustomers();
        adminFacade.updateCustomer(new Customer(customers.get(2).getId(), "toto", "abo99", "mota@gmail.com", "tree988"));
        adminFacade.deleteCustomer(customers.get(1).getId());
        System.out.println(adminFacade.getAllCustomers());
        System.out.println(adminFacade.getOneCustomer(customers.get(2).getId()));
    }

    private static void testCompanyFacade(CompanyFacade companyFacade, AdminFacade adminFacade) throws SQLException {
        ArrayList<Company> companies = adminFacade.companiesDAO.getAllCompanies();

        companyFacade.addCoupon(new Coupon(companies.get(1).getId(), Category.Restaurant.getId(), "res coupon", "SDG food", Date.valueOf("2021-12-11"), Date.valueOf("2026-10-25"), 250, 655.5, "fdds"));
        companyFacade.addCoupon(new Coupon(companies.get(1).getId(), Category.Food.getId(), "kfc coupon", "SDG food", Date.valueOf("2021-12-11"), Date.valueOf("2022-10-25"), 360, 655.5, "fdds"));
        companyFacade.addCoupon(new Coupon(companies.get(1).getId(), Category.Restaurant.getId(), "res", "goood", Date.valueOf("2021-12-11"), Date.valueOf("2029-10-25"), 282, 655.5, "fdds"));

        ArrayList<Coupon> coupons = companyFacade.couponsDAO.getAllCoupons();
        companyFacade.updateCoupon(new Coupon(coupons.get(1).getId(), companies.get(1).getId(), Category.Restaurant.getId(), "el", "good el", Date.valueOf("2021-12-11"), Date.valueOf("2023-10-26"), 250, 798, "dsg"));
        companyFacade.deleteCoupon(coupons.get(2).getId());
        System.out.println(companyFacade.getCompanyCoupons());
        System.out.println(companyFacade.getCompanyCoupons(Category.Restaurant));
        System.out.println(companyFacade.getCompanyCoupons(10000));
        System.out.println(companyFacade.getCompanyDetails());


    }

    private static void testCustomerFacade(CustomerFacade customerFacade) {
        ArrayList<Coupon> coupons = customerFacade.couponsDAO.getAllCoupons();
        ArrayList<Company> companies = customerFacade.companiesDAO.getAllCompanies();

        for (Coupon coupon : coupons) {
            customerFacade.purchaseCoupon(coupon);
        }
        System.out.println(customerFacade.getCustomerCoupons());
        System.out.println(customerFacade.getCustomerCoupons(500));
        System.out.println(customerFacade.getCustomerCoupons(Category.Restaurant));
        System.out.println(customerFacade.getCustomerDetails());
    }

}
