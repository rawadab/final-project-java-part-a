package Proyect;

import Proyect.CouponsDAO;
import Proyect.CouponsDBDAO;
import Proyect.Coupon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
/*this class we use a thread that run once a day when he run he will check if the coupon is legal if true he will keep,
 it else he will delete the coupon with the help of couponsDAO and will delete the coupon in the database in each table that relative  */
public class CouponExpirationDailyJob implements Runnable {
    private final CouponsDAO couponsDAO = new CouponsDBDAO();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean quit = false;

    public CouponExpirationDailyJob() {

    }
    /*this method will run all the time and do all the things to delete illegal coupons*/
    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!quit) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                System.out.println(hour + "   "+minute+"    "+minute);
              if (hour == 11 && minute == 37 && second ==60) // When it will be 12 o'clock at night
                {
                    System.out.println("toto");
                    ArrayList<Coupon> co = couponsDAO.getAllCoupons();

                    for (int i = 0; i < co.size(); i++) {
                        if (co.get(i).getEndDate().before(calendar.getTime())) {
                            int couponId =  co.get(i).getId();
                            couponsDAO.deleteCoupon(couponId);

                        }
                    }
                }
            }
            try {
                sleep(1000);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    public void stop() {
        quit = true;
        scheduler.shutdown();
    }/*this method will stop the thread*/
}





