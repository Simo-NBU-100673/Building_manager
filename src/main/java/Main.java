import gui.UserInterface;

public class Main {
    public static void main(String[] args) {
//        Employee employee = new Employee(1,"Pesho", "Tihomirov");
//
//        EmployeeDAO.saveEmployee(employee);
//        EmployeeDAO.getAllEmployees().forEach(System.out::println);
//
//        //try all function of CompanyDAO
//        Company company = new Company(1,"SAP");
//        CompanyDAO.saveCompany(company);
//        CompanyDAO.deleteCompanyById(1);
//        CompanyDAO.deleteCompanyById(2);
//        CompanyDAO.getAllCompanies().forEach(System.out::println);

        //TODO make a relationship between Company and Employee (1 to many)
        //TODO make a taxes table too
        UserInterface.getInstance().start();
    }
}
