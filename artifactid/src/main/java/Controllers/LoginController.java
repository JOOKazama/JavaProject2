package Controllers;
import Tables.Admin;
import Tables.Employee;
import Tables.HWID;
import Validators.Database;
import Validators.Show;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import Validators.Loader.LoadLogin;
import Validators.Loader.LoadAdmin;
import Validators.Loader.LoadEmployee;

public class LoginController extends Application
{
    public static Timestamp time;
    public static int id;
    @FXML private TextField username, password;
    @FXML private Label label;
    Show b=new Show();
    Boolean yes=false;

    @Override public void start(Stage p) throws Exception { LoadLogin ll=new LoadLogin(); }

    public void action() throws IOException
    {
        Database.DB db=new Database.DB();
        db.Create();
        List l=db.session.createQuery("from Employee").list();
        List l1=db.session.createQuery("from HWID").list();
        Admin a=db.session.get(Admin.class, 1);
        String hwid=b.ThisHWID();

        for (Object o:l1)
        {
            if ((((HWID)o).getId()).equals(hwid))
            {
                yes=true;
                break;
            }
        }

        for (Object o:l)
        {
            if ((((Employee)o).getUsername()).equals(username.getText())
            && (((Employee)o).getPassword()).equals(password.getText()) && yes)
            {
                time=b.Time();
                id=((Employee)o).getId();
                LoadEmployee le=new LoadEmployee();
            }
            else if (a.getUsername().equals(username.getText()) && a.getPassword().equals(password.getText()))
            { LoadAdmin ll=new LoadAdmin(); }
            else { label.setText("Invalid input or HWID!"); }
        }

        db.session.close();
        db.factory.close();
    }
}