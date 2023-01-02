package Proyect;

import java.sql.*;
import java.util.ArrayList;
/*This class implements his methods from the interface CustomerDAO */
public class CustomersDBDAO implements CustomerDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private int flag = 0;
    /*in every method we establish a new connection (with the help of ConnectionPool class)  so we can get to the database
     and after the method finishes it goals  we restore the connection one again so its usable again  */
    @Override
    public boolean isCustomerExsist(String email, String password) {
        Connection conn =null;
        try  {
            conn = connectionPool.getConnection();
            String Search = "SELECT * FROM customers WHERE (email =" + "'" + email + "'" + " AND password =" + "'" + password + "'" + ");";
            Statement st = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(Search);

            ResultSet resultSet = st.executeQuery(Search);
            boolean companyExists = resultSet.next();
//            if (resultSet.next()) {
//                flag = 1;
//            }
            st.close();
            resultSet.close();
            System.out.println("customer is exist");


            return companyExists;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            connectionPool.restoreConnection(conn);
        }

       // return flag ==0;
    }/*This method check if the customer is exists in our database if the customer allready exists will return ("customer is exist")
     , in the method we will do connection to the database*/

    public boolean customerEmailCantBeDuplicated(String email) {
        Connection conn =null;
        try  {
            conn = connectionPool.getConnection();
            String Search = "SELECT * FROM customers WHERE email ='" + email + "';";
            Statement st = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(Search);

            ResultSet resultSet = st.executeQuery(Search);
            if (resultSet.next()) {
                flag = 1;

            }
            st.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }

        return flag == 1;
    }/*a help method that we use it when we want to add customer we cant add a customer with the same email that exist */

    @Override
    public void addCustomer(Customer customer) {
        Connection conn =null;
        if (customerEmailCantBeDuplicated(customer.getEmail())) {
            System.out.println("customer Email Already Exist");

        } else {
            try  {
                conn = connectionPool.getConnection();
                String insertSql = "INSERT INTO customers(first_name, last_name, email, password) VALUES( ?, ?, ?, ?) ; ";

                PreparedStatement PS = conn.prepareStatement(insertSql);

                PS.setString(1, customer.getFirstName());
                PS.setString(2, customer.getLastName());
                PS.setString(3, customer.getEmail());
                PS.setString(4, customer.getPassword());

                PS.executeUpdate();
                System.out.println("Adding customer is Successful");
                PS.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }finally {
                connectionPool.restoreConnection(conn);
            }

        }
    }/*this methos will use the customerEmailCantBeDuplicated method to check if the email that the customer are trying to enter is exist
    if it is not he will add the customer*/

    @Override
    public void updateCustomer(Customer customer) {
        Connection conn =null;
        try  {
            conn = connectionPool.getConnection();
            String UbdateSql = "update customers set first_name='" + customer.getFirstName() +
                    "', last_name = '" + customer.getLastName() + "',email ='" + customer.getEmail() + "',password = '" + customer.getPassword() +
                    "'where id ='" + customer.getId() + "'";
            PreparedStatement PS = conn.prepareStatement(UbdateSql);
            PS.executeUpdate();
            System.out.println("Update customer is successful ");
            PS.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }
    }/*this method will update the details of an exist customer*/

    @Override

    public void deleteCustomer(int customerID) {
        Connection conn =null;
        String Sql_Customer_Delete = "DELETE FROM Company1.customers WHERE id ='" + customerID + "';";
        try {
            conn = connectionPool.getConnection();
            Statement sa = conn.createStatement();
            sa.executeUpdate("delete from customers_vs_coupons where customer_id=" + customerID + "; ");
            sa.executeUpdate("delete from customers where id=" + customerID + "; ");

            System.out.println("Delete Customer is Successfully");


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }finally {
            connectionPool.restoreConnection(conn);
        }
    }/*this method will delete for an exist customer we will send the id of the customer and in the database will search for it and delete from the
    (customers_vs_coupons, customers) after it is finish it will print Delete Customer is Successfully */


    @Override
    public ArrayList<Customer> getAllCustomers() {
        int ID;
        String FirstName, LastName, Email, Password;
        ArrayList<Coupon> coupons = new ArrayList<>();
        ArrayList<Customer> All_cus = new ArrayList<>();
        Customer cus;
        Coupon coup;
        Connection conn =null;
        Connection conn1 =null;
        try  {
            conn = connectionPool.getConnection();
            String sqlQuery = "SELECT * FROM customers";

            PreparedStatement PS = conn.prepareStatement(sqlQuery);

            ResultSet RS = PS.executeQuery(sqlQuery);

            while (RS.next()) {
                ID = RS.getInt("id");
                FirstName = RS.getString("first_name");
                LastName = RS.getString("last_name");
                Email = RS.getString("email");
                Password = RS.getString("password");

                conn1  =connectionPool.getConnection();
                String sqlCoupon = "SELECT * FROM  customers_vs_coupons WHERE customer_id = " + ID;
                PreparedStatement ps2 = conn1.prepareStatement(sqlCoupon);
                ResultSet rs2 = ps2.executeQuery(sqlCoupon);
                while (rs2.next()) {
                    coup = new Coupon(rs2.getInt("id"), rs2.getInt("company_id"),
                            rs2.getInt("category_id"), rs2.getString("title"),
                            rs2.getString("description"), rs2.getDate("start_date"),
                            rs2.getDate("end_date"), rs2.getInt("amount"),
                            rs2.getDouble("price"), rs2.getString("image"));
                    coupons.add(coup);
                }
                ps2.close();
                rs2.close();

                cus = new Customer(ID, FirstName, LastName, Email, Password, coupons);
                All_cus.add(cus);
            }
            RS.close();
            PS.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
            connectionPool.restoreConnection(conn1);
        }

        return All_cus;
    }/*this method well return all the customers in array in each customer will return with all the details of the customer
     , also we can print the customer details */

    @Override
    public Customer getOneCustomer(int customerID) {
        int ID;
        String FirstName, LastName, Email, Password;
        Customer One_customer = null;
        Coupon coup;
        Connection conn =null;

        try {
            conn = connectionPool.getConnection();
            String sqlQuery = "SELECT * FROM customers\n" + "WHERE id = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                ID = rs.getInt("id");
                FirstName = rs.getString("first_name");
                LastName = rs.getString("Last_name");
                Email = rs.getString("email");
                Password = rs.getString("password");
                One_customer = new Customer(ID, FirstName, LastName, Email, Password, new ArrayList<Coupon>());

                conn  =connectionPool.getConnection();
                String sqlCoupon = "SELECT * FROM customers_vs_coupons WHERE customer_id = " + customerID;
                PreparedStatement ps2 =conn.prepareStatement(sqlCoupon);
                ResultSet rs2 = ps2.executeQuery(sqlCoupon);
                while (rs2.next()) {
                    int id = rs2.getInt("coupon_id");
                    One_customer.getCoupons().add(getCouponsCustomerByID(id));
                }
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }

        return One_customer;
    }/*this method will return one customer referece to the customer id and it will return details of the customer
    ,  also we can print the customer details */
    public Customer getOneCustomer(String email, String password) {
        int ID;
        String FirstName, LastName, Email, Password;
        Customer One_customer = null;
        Coupon coup;
        Connection conn =null;

        try {
            conn  =connectionPool.getConnection();
            String sqlQuery = "SELECT * FROM customers\n" + "WHERE email = '" + email + "' AND password = '" + password + "' ;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                ID = rs.getInt("id");
                FirstName = rs.getString("first_name");
                LastName = rs.getString("Last_name");
                Email = rs.getString("email");
                Password = rs.getString("password");
                One_customer = new Customer(ID, FirstName, LastName, Email, Password, new ArrayList<Coupon>());

                conn  =connectionPool.getConnection();
                String sqlCoupon = "SELECT * FROM customers_vs_coupons WHERE customer_id = " + ID;
                PreparedStatement ps2 = conn.prepareStatement(sqlCoupon);
                ResultSet rs2 = ps2.executeQuery(sqlCoupon);
                while (rs2.next()) {
                    int id = rs2.getInt("coupon_id");
                    One_customer.getCoupons().add(getCouponsCustomerByID(id));
                }
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }

        return One_customer;
    }/*this method will accept two values (email, password) and return one customer referece to the email, password and it will return details of the customer
    ,  also we can print the customer details*/

    public Coupon getCouponsCustomerByID(int id) {
        Coupon coupons = null;
        Connection conn =null;
        try {
            conn  = connectionPool.getConnection();
            Statement stmt = conn.createStatement();
            String Sql = "SELECT * FROM coupons WHERE id = '" + id + "'";
            ResultSet rs = stmt.executeQuery(Sql);
            while (rs.next()) {
                coupons = new Coupon(id, rs.getInt("company_id"),
                        rs.getInt("category_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDate("start_date"),
                        rs.getDate("end_date"), rs.getInt("amount"),
                        rs.getDouble("price"), rs.getString("image"));
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.restoreConnection(conn);
        }
        return coupons;
    }/*this method will return all the coupons of the customer that we entered */

}
