package Proyect;

import java.util.HashMap;
import java.util.Map;
/*In this class we create enum of the clients (admin, company, customer), when a client trying to connect to the system he will be filtered by his type  */
public enum ClientType {
    Administrator(1),
    Company(2),
    Customer(3);
    private int id;
    ClientType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Map<Integer, ClientType> map = new HashMap<>();

    static {
        for (ClientType clientType : ClientType.values()) {
            map.put(clientType.getId(), clientType);
        }
    }

    public static ClientType valueOf(int id) {
        return map.get(id);
    }
}
