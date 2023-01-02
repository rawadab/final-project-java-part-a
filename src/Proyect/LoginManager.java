package Proyect;

public class LoginManager {
/*this class will manage the sing in to the system and decide which client is trying to sing in */
    public static LoginManager instance;


    LoginManager() {


    }

    public static LoginManager getInstance() {

        if (instance == null) {
            instance = new LoginManager();
        }

        return instance;
    }
/*this method we use switch case (Administrator,Company, Customer) we also use login method to decide which client is trying to sing in */
    public ClientFacade login(ClientType clientType, String email, String password) {

        try {


            switch (clientType) {
                case Administrator:
                    AdminFacade adminFacade = new AdminFacade();
                    if (adminFacade.login(email, password)) {
                        return adminFacade;
                    } else {
                        return null;
                    }

                case Company:
                    CompanyFacade companyFacade = new CompanyFacade(email);
                    if (companyFacade.login(email, password)) {
                        return companyFacade;
                    } else {
                        return null;
                    }
                case Customer:
                    CustomerFacade customerFacade = new CustomerFacade(email);
                    if (customerFacade.login(email, password)) {
                        return customerFacade;
                    } else {
                        return null;
                    }
                default:
                    return null;
            }
        } catch (ClientNotExsit e) {
            System.out.println(e.getMessage());
        }return  null;
    }



}
