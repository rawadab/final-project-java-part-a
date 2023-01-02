package Proyect;

import java.util.ArrayList;
/*Creating interface and define methods that we can use them in CompaniesDBDAO  */
public interface CompaniesDAO {
    public boolean isCompanyExists(String email, String password);
    public void addCompany(Company company);
    public void updateCompany(Company company);
    public void deleteCompany(int companyID);
    public ArrayList<Company> getAllCompanies();
    public Company getOneCompany(int companyID);

    Company getOneCompany(String email, String password);
}
