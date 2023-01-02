package Proyect;

import java.sql.*;
import java.util.ArrayList;
/*This class implements his methods from the interface CouponsDAO */

public class CouponsDBDAO implements CouponsDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
/*in every method we establish a new connection (with the help of ConnectionPool class)  so we can get to the database
 and after the method finishes it goals  we restore the connection one again so its usable again  */

    @Override
    public void addCoupon(Coupon coupon) {
        Connection conn =null;
        try {
            conn = connectionPool.getConnection();
            Statement rs = conn.createStatement();
            ResultSet ras = rs.executeQuery("select * from coupons ;");
            while (ras.next())
            {
                String titleCheck = ras.getString("title");
                int companyCheck = ras.getInt("company_id");
                if(titleCheck.equals(coupon.getTitle()) && companyCheck == coupon.getCompanyID())
                    throw new couponTitleisAlreadyExist();
            }
            String insertSql = "INSERT INTO company1.coupons(id,company_id,category_id, title, description, start_date, end_date,amount, price,image)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement PS = conn.prepareStatement(insertSql);
            PS.setInt(1, coupon.getId());
          PS.setInt(2, coupon.getCompanyID());
            PS.setInt(3, coupon.getCategory());
            PS.setString(4, coupon.getTitle());
            PS.setString(5, coupon.getDescription());
            PS.setDate(6, new java.sql.Date(coupon.getStartDate().getTime()));
            PS.setDate(7, new java.sql.Date(coupon.getEndDate().getTime()));
            PS.setInt(8, coupon.getAmount());
            PS.setDouble(9, coupon.getPrice());
            PS.setString(10, coupon.getImage());
            PS.executeUpdate();
            System.out.println("Adding Coupon is Successful");
            PS.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (couponTitleisAlreadyExist e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(conn);
        }

    }/*This method check if the coupon is exists in our database if the coupon allready exists will throw  couponTitleisAlreadyExist
     (a class that we create extends from exception), else will do the add and will print Adding Coupon is Successful*/


    @Override
    public void updateCoupon(Coupon coupon) {
        Connection conn =null;
        try  {
            conn = connectionPool.getConnection();
            String UbdateSql = "UPDATE coupons SET category_id= ? , title= ? , description = ?, start_date =?, end_date=?, amount=?," +
                    " price=? , image=?  WHERE id =" + "'" + coupon.getId() + "' AND company_id = '" + coupon.getCompanyID() + "';";
           PreparedStatement PS = conn.prepareStatement(UbdateSql);
            PS.setInt(1, coupon.getCategory() );
            PS.setString(2, coupon.getTitle() );
            PS.setString(3,  coupon.getDescription()  );
            PS.setDate(4, new java.sql.Date(coupon.getStartDate().getTime()) );
            PS.setDate(5, new java.sql.Date(coupon.getEndDate().getTime()) );
            PS.setInt(6, coupon.getAmount() );
            PS.setDouble(7,coupon.getPrice() );
            PS.setString(8, coupon.getImage() );

            PS.executeUpdate();
            System.out.println("Update coupon is successful ");
            PS.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }
    }/*This method we will do update to an exist coupon the update we cant change the coupon id and the company id every thing else we can
    to update*/

    @Override
    public void deleteCoupon(int couponID) {
        Connection conn =null;
        String sql_coustomer_vs_coupon_delete = "delete from customers_vs_coupons where coupon_id = '" + couponID + " ';";
        String Sql_Coupon_Delete = "DELETE FROM company1.coupons WHERE id =" + couponID + ";";
        try  {
            conn = connectionPool.getConnection();
            PreparedStatement RS = conn.prepareStatement(sql_coustomer_vs_coupon_delete);
            RS.execute();
            PreparedStatement PS = conn.prepareStatement(Sql_Coupon_Delete);
            PS.execute();
            System.out.println("Delete Coupon is Successfully, history purchase also deleted");
            PS.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }

    }/*This method will delete a coupon from the database we will reserve the coupon id and in the sql search for the coupon id
    that we put after that the coupon will delete also will delete also if there a customer that have this coupon also will delete */

    @Override
    public ArrayList<Coupon> getAllCoupons() {

        int Id , CompanyId, Category, Amount;
        double Price;
        String  Title,Description, Image;
        Date StartDate, EndDate;
        ArrayList<Coupon> All_coupons = new ArrayList<>();
        Coupon coup;
        Connection conn =null;
        try  {
            conn = connectionPool.getConnection();
            String sqlQuery = "SELECT * FROM coupons";

            PreparedStatement PS = conn.prepareStatement(sqlQuery);

            ResultSet RS = PS.executeQuery(sqlQuery);

            while (RS.next()) {
                Id = RS.getInt("id");
//                CompanyId = RS.getInt("company_id");
//                Category = RS.getInt("category_id");
//                Title = RS.getString("title");
//                Description = RS.getString("description");
//                StartDate = RS.getDate("start_date");
//                EndDate =RS.getDate("end_date");
//                Amount = RS.getInt("amount");
//                Price =RS.getDouble("price");
//                Image =RS.getString("image");

                String sqlCoupon = "SELECT * FROM coupons WHERE id = " + Id;
                PreparedStatement ps2 = connectionPool.getConnection().prepareStatement(sqlCoupon);
                ResultSet rs2 = ps2.executeQuery(sqlCoupon);
                while (rs2.next()) {
                    coup = new Coupon(rs2.getInt("id"), rs2.getInt("company_id"),
                            rs2.getInt("category_id"), rs2.getString("title"), rs2.getString("description"),
                            rs2.getDate("start_date"), rs2.getDate("end_date"), rs2.getInt("amount"),
                            rs2.getDouble("price"), rs2.getString("image"));
                    All_coupons.add(coup);
                }
                ps2.close();
                rs2.close();

             /*   coup = new Coupon(Id, CompanyId,Category, Title, Description, StartDate,EndDate, Amount,Price,Image);
                All_coupons.add(coup);*/
            }
            RS.close();
            PS.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }

        return All_coupons;

    }/*this method well return all the coupons in array in each coupon will return with all the details of the coupon
     , also we can print the company details */

    @Override
    public Coupon getOneCoupon(int couponID) {
        int Id , CompanyId, Category,Amount;
        double Price;
        String  Title,Description, Image;
        Date StartDate, EndDate;
        ArrayList<Coupon> coupons = new ArrayList<>();
        Coupon One_coupon = null;
        Coupon coup;
        Connection conn =null;
        try {
            String sqlQuery = "SELECT * FROM coupons \n" + "WHERE id = " + couponID;
            conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Id = rs.getInt("id");
                CompanyId = rs.getInt("company_id");
                Category = rs.getInt("category_id");
                Title = rs.getString("title");
                Description = rs.getString("description");
                StartDate = rs.getDate("start_date");
                EndDate =rs.getDate("end_date");
                Amount = rs.getInt("amount");
                Price =rs.getDouble("price");
                Image =rs.getString("image");

                String sqlCoupon = "SELECT * FROM customers_vs_coupons WHERE customer_id = " + Id;
                PreparedStatement ps2 = connectionPool.getConnection().prepareStatement(sqlCoupon);
                ResultSet rs2 = ps2.executeQuery(sqlCoupon);
                while (rs2.next()) {
                    coup = new Coupon(rs2.getInt("coupon_id"), rs2.getInt("customer_id"), rs2.getInt("category_id"),
                            rs2.getString("coupon_title"), rs2.getString("coupon_description"), rs2.getDate("coupon_start_date"),
                            rs2.getDate("coupon_end_date"), rs2.getInt("coupon_amount"),
                            rs2.getDouble("coupon_price"), rs2.getString("coupon_image"));
                    coupons.add(coup);
                }
                ps2.close();
                rs2.close();

                One_coupon = new Coupon(Id, CompanyId, Category, Title, Description, StartDate,EndDate, Amount,Price,Image);;

            }
            rs.close();
            stmt.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.restoreConnection(conn);
        }

        return One_coupon;

    }/*this method will return one coupon referece to the coupon id and it will return details of the coupon
   ,  also we can print the company details */

    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        Connection conn =null;
        try {
            conn = connectionPool.getConnection();


            String sqlQuery = "insert into company1.customers_vs_coupons(customer_id,coupon_id) values(" + customerID + "," + couponID + ");";
            PreparedStatement PS = conn.prepareStatement(sqlQuery);
            PS.executeUpdate();
            System.out.println("Adding Coupon and customer into customers_vs_coupons is Successful");
            PS.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }finally {
            connectionPool.restoreConnection(conn);
        }
    }/*this method will accept two values (customerID, couponID) after that will add them to customers_vs_coupons table
     that's mean that the customer buy this coupon*/
    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        Connection conn =null;
            try {
                conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement();
            String sqlQuery = "DELETE FROM customers_vs_coupons WHERE customer_id = '" + customerID + "' and coupon_id = '" + couponID + "'";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
                connectionPool.restoreConnection(conn);
            }

    }/*this method will accept two values (customerID, couponID) after that will delete them to customers_vs_coupons table
     that's mean that the customer buy this coupon*/
}