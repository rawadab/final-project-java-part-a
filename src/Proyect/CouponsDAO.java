package Proyect;

import java.util.ArrayList;
/*Creating interface and define methods that we can use them in CouponsDBDAO  */
public interface CouponsDAO {
    public void addCoupon(Coupon coupon);
    public void updateCoupon(Coupon coupon);
    public void deleteCoupon(int couponID);
    public ArrayList<Coupon> getAllCoupons();
    public Coupon getOneCoupon(int couponID);
    public void addCouponPurchase(int customerID, int couponID);
    public void deleteCouponPurchase(int customerID, int couponID);
}
