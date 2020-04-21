package Controllers;
import Tables.Employee;
import Validators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Validators.Database;
import org.hibernate.Session;

public class CreateController
{
    @FXML TextField first, middle, last, username, password;
    private Alert alert=new Alert(Alert.AlertType.INFORMATION);

    public void action()
    {
        Validators.EV ev= new Validators.EV(first.getText(), middle.getText(), last.getText(), username.getText());

        if(!Validators.error)
        {
            Employee a=new Employee();
            Database.DB db=new Database.DB();
            db.Create();
            Session session=db.session;

            session.beginTransaction();
            a.setFirst(first.getText());
            a.setMiddle(middle.getText());
            a.setLast(last.getText());
            a.setUsername(username.getText());
            a.setPassword(password.getText());
            session.save(a);
            session.getTransaction().commit();

            db.session.close();
            db.factory.close();

            alert.setHeaderText("Success!");
            alert.setContentText("Successfully created an employee!");
            alert.showAndWait();
        }
    }
}