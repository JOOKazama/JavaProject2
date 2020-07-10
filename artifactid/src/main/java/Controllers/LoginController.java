package Controllers;
import Tables.Admin;
import Tables.Employee;
import Tables.HWID;
import Validators.Database;
import Validators.Show;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Timestamp;
import java.util.List;
import Validators.Loader;

@SuppressWarnings("unchecked")
public class LoginController extends Application
{
    public static Timestamp timestamp;
    public static int id;
    public Button button_login;
    @FXML private TextField username, password;
    @FXML private Label label;
    Show show=new Show();
    Boolean if_hwid_exists=false;
    Loader loader=new Loader();

    @Override public void start(Stage stage) throws Exception { loader.LoadLogin(); }

    public void login() throws Exception
    {
        Database.DB db=new Database.DB();
        db.Create();
        List<Employee>list_employee=db.session.createQuery("from Employee").list();
        List<HWID>list_hwid=db.session.createQuery("from HWID").list();
        Admin admin=db.session.get(Admin.class, 1);
        String hwid=show.ThisHWID();

        for(Object object:list_hwid)
        {
            if((((HWID)object).getId()).equals(hwid))
            {
                if_hwid_exists=true;
                break;
            }
        }

        for(Object object:list_employee)
        {
            if((((Employee)object).getUsername()).equals(username.getText())
            && (((Employee)object).getPassword()).equals(password.getText()) && if_hwid_exists)
            {
                Stage stage=(Stage)button_login.getScene().getWindow();
                stage.close();
                timestamp=show.Time();
                id=((Employee)object).getId();
                loader.LoadEmployee();
            }
            else if(admin.getUsername().equals(username.getText()) && admin.getPassword().equals(password.getText()))
            {
                Stage stage=(Stage)button_login.getScene().getWindow();
                stage.close();
                loader.LoadAdmin();
            }
            else { label.setText("Invalid input or HWID!"); }
        }

        db.session.close();
        db.factory.close();
    }
}