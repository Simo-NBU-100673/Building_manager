import dao.CompanyDAO;
import dao.ContractDAO;
import dao.EmployeeDAO;
import entity.Company;
import entity.Employee;
import gui.UserInterface;
import session.SessionFactoryUtil;

public class Main {
    public static void main(String[] args) {
        UserInterface.getInstance().start();
    }
}
