package Proyect;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.*;
import java.util.stream.Stream;
/*This class implements his methods from the interface CompaniesDAO */
public class CompaniesDBDAO implements CompaniesDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

/*in every method we establish a new connection (with the help of ConnectionPool class)  so we can get to the database
 and after the method finishes it goals  we restore the connection one again so its usable again  */
    @Override
    public boolean isCompanyExists(String Email, String Password) {
        Connection conn =null;
        try {

            conn = connectionPool.getConnection();
            String Search = "SELECT * FROM companies WHERE (email =" + "'" + Email + "'" + " AND password =" + "'" + Password + "'" + ");";

            PreparedStatement preparedStatement = conn.prepareStatement(Search);

            ResultSet resultSet = preparedStatement.executeQuery(Search);
            boolean companyExists = resultSet.next();

            preparedStatement.close();
            resultSet.close();


            return companyExists;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            connectionPool.restoreConnection(conn);
        }

    }/*This method check if the company is exists in our database if the company allready exists will return true else will return false,
     in the method we will do connection to the database after that will do the search(line 16)*/

    @Override
    public void addCompany(Company company) {
        String checkName = company.getName();
        Stream<String> ch = Stream.of(checkName);
        Connection conn = null;
        if (isCompanyExists(company.getEmail(), company.getPassword())) {
            System.out.println("Company Already Exist");
        } else {
            try  {
                conn = connectionPool.getConnection();
                String insertSql = "INSERT INTO company1.companies(id,name,email,password)" + "VALUES(?, ?, ?, ?) ";
                PreparedStatement PS = conn.prepareStatement(insertSql);
                PS.setInt(1, company.getId());
                PS.setString(2, company.getName());
                PS.setString(3, company.getEmail());
                PS.setString(4, company.getPassword());
                PS.executeUpdate();

                System.out.println("Adding Company is Successful");
                PS.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }finally {
                connectionPool.restoreConnection(conn);
            }

        }
    }/*this method will add company to the database,  first will check if the company allready exist in our database,
    else we will add the new company adding(id, name, email,password), after adding the company will print Adding Company is Successful*/

    @Override
    public void updateCompany(Company company) {
        Connection conn = null;
        try  {
            conn = connectionPool.getConnection();
            String query = "UPDATE companies SET email= ? , password= ? WHERE id =" + "'" + company.getId() + "' AND name = '" + company.getName() + "';";
            PreparedStatement PS = conn.prepareStatement(query);
            PS.setString(1, company.getEmail());
            PS.setString(2, company.getPassword());

            int check = PS.executeUpdate();
            if (check != 0) {
                System.out.println("Update is successful ");
            }
            PS.close();

        }

     catch(SQLException e){
        System.out.println(e.getMessage());
    }finally {
            connectionPool.restoreConnection(conn);
        }

}/*This method we will do update to an exist company the update is just on the email and password of the company and the id
and the name will never change*/



    @Override
    public void deleteCompany(int companyID) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM companies INNER JOIN coupons ON companies.id = coupons.company_id INNER JOIN customers_vs_coupons ON coupons.id = customers_vs_coupons.coupon_id where companies.id = '" + companyID + "'");
            while (rs.next()) {
                int iD = rs.getInt("coupon_id");
                stmt.executeUpdate("delete from customers_vs_coupons where coupon_id = '" + iD + "'");
            }
            stmt.executeUpdate("delete from coupons where company_id = '" + companyID + "'");
            int check = stmt.executeUpdate("delete from companies where id = '" + companyID + "'");

            if(check !=0){
                System.out.println("delet company is successful" );
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.restoreConnection(conn);
        }
    }/*This method will delete a company from the database we will reserve the company id and in the sql search for the company id
    that we put after that the company will delete also will delete the coupons of the company */



    @Override
    public ArrayList<Company> getAllCompanies() {

        Connection conn = null;
        ArrayList<Company> All_comp = new ArrayList<Company>();
        try {

            conn = connectionPool.getConnection();
            String sqlQuery = "SELECT * FROM companies";

            PreparedStatement PS = conn.prepareStatement(sqlQuery);

            ResultSet RS = PS.executeQuery();

            while (RS.next()) {
                All_comp.add(new Company(RS.getInt("id"), RS.getString("name"),
                        RS.getString("email"), RS.getString("password"),new ArrayList<Coupon>()));

            }

            String sqlCoupon = "SELECT * FROM coupons ";
            RS =PS.executeQuery(sqlCoupon);

            while (RS.next()) {
                int ID =RS.getInt("company_id");
                Coupon coup = new Coupon(RS.getInt("id"), RS.getInt("company_id"),
                        RS.getInt("category_id"), RS.getString("title"),
                        RS.getString("description"), RS.getDate("start_date"),
                        RS.getDate("end_date"), RS.getInt("amount"),
                        RS.getDouble("price"), RS.getString("image"));
                for (Company c : All_comp) {
                    if (c.getId() == ID) {
                        c.getCoupons().add(coup);
                    }
                }

            }
            RS.close();
            PS.close();

        }


        catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(conn);

        }

        return All_comp;
    }/*this method well return all the companies in array in each company will return with all the details of the company
    (id, name ,email, password, all the coupons of each company), also we can print the company details */



    @Override
    public Company getOneCompany(int companyID) {
        int ID;
        String Name, Email, Password;
        ArrayList<Coupon> coupons = new ArrayList<>();
        Company One_company = null;
        Coupon coup;
        Connection conn = null;
        Connection conn1 = null;
        try {
            conn = connectionPool.getConnection();

            String sqlQuery = "SELECT * FROM companies WHERE id = " + companyID + ";";
            Statement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                ID = rs.getInt("id");
                Name = rs.getString("name");
                Email = rs.getString("email");
                Password = rs.getString("password");


                conn1 = connectionPool.getConnection();
                String sqlCoupon = "SELECT * FROM coupons WHERE company_id = " + companyID + ";";

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

                One_company = new Company(ID, Name, Email, Password, coupons);

            }
            rs.close();
            stmt.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
            connectionPool.restoreConnection(conn1);
        }

        return One_company;
    }/*this method will return one company referece to the company id and it will return details of the company
    (id, name ,email, password, all the coupons of each company),  also we can print the company details */

    public Company getOneCompany(String email, String password) {
        int ID;
        String Name, Email, Password;
        ArrayList<Coupon> coupons = new ArrayList<>();
        Company One_company = null;
        Coupon coup;
        Connection conn = null;
        Connection conn1 = null;
        try {
            conn = connectionPool.getConnection();
           String sqlQuery = "SELECT * FROM companies WHERE email = '" + email + "' AND password = '" + password + "' ;";
            Statement stmt =conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                ID = rs.getInt("id");
                Name = rs.getString("name");
                Email = rs.getString("email");
                Password = rs.getString("password");
                conn1 = connectionPool.getConnection();
                String sqlCoupon = "SELECT * FROM coupons WHERE company_id = " + ID + ";";
                PreparedStatement ps2 = conn.prepareStatement(sqlCoupon);
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

                One_company = new Company(ID, Name, Email, Password, coupons);

            }
            rs.close();
            stmt.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
            connectionPool.restoreConnection(conn1);
        }

        return One_company;
    }/*this method is a help method to have access from the email and password of the company to find the id of the company
    and get all the details of this company*/


}