package Controllers;
import Tables.Employee;
import Validators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Validators.Database;
import org.hibernate.Session;

public class CreateController
{
    public Button button_create;
    @FXML TextField first, middle, last, username, password;
    private Alert alert=new Alert(Alert.AlertType.INFORMATION);
    private Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Validators validator=new Validators();

    public void create()
    {
        validator.ValidatorCreateController(first.getText(), middle.getText(), last.getText(), username.getText());
        alert_error.setHeaderText("Error!");

        if(!validator.getError().equals(""))
        {
            alert_error.setContentText(validator.getError());
            alert_error.showAndWait();
            validator.setError("");
        }
        else
        {
            Employee employee=new Employee();
            Database.DB db=new Database.DB();
            db.Create();
            Session session=db.session;

            session.beginTransaction();
            employee.setFirst(first.getText());
            employee.setMiddle(middle.getText());
            employee.setLast(last.getText());
            employee.setUsername(username.getText());
            employee.setPassword(password.getText());
            session.save(employee);
            session.getTransaction().commit();

            db.session.close();
            db.factory.close();

            alert.setHeaderText("Success!");
            alert.setContentText("Successfully created an employee!");
            alert.showAndWait();
        }
    }
}