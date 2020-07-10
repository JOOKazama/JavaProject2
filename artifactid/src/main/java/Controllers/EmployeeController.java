package Controllers;
import Tables.Dates;
import Validators.Database;
import Validators.Loader;
import Validators.Show;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeController
{
    public Button button_employee;
    Loader loader=new Loader();

    public void employee() throws IOException
    {
        Dates date=new Dates();
        Database.DB db=new Database.DB();
        db.Create();
        Show b=new Show();

        db.session.beginTransaction();
        date.setStartdate(LoginController.timestamp);
        date.setEnddate(b.Time());
        date.setE_id(LoginController.id);
        db.session.save(date);
        db.session.getTransaction().commit();
        db.session.close();
        db.factory.close();

        Stage stage=(Stage)button_employee.getScene().getWindow();
        stage.close();
        loader.LoadLogin();
    }
}