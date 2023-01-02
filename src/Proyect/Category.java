package Proyect;

import java.util.HashMap;
import java.util.Map;
/*In this class we create enum of the categories that we want to add to the database, (category of the coupons),
in the database we will receive the id of the category that we want to be to the coupon */
public enum Category {
    Food(1),
    Electricity(2),
    Restaurant(3),
    Vacation(4);
    private int id;

    Category(){};

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Map<Integer, Category> map = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            map.put(category.getId(), category);
        }
    }

    public static Category valueOf(int id) {
        return map.get(id);
    }
}