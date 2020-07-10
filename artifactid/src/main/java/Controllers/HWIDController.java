package Controllers;
import Tables.HWID;
import Validators.Validators;
import Validators.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import java.io.IOException;
import Validators.Loader;

public class HWIDController
{
    @FXML public TextField approve_hwid;
    public Button button_approve, this_pc_hwid, list_of_hwid;
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Alert information_alert=new Alert(Alert.AlertType.INFORMATION);
    Validators validator=new Validators();
    Loader loader=new Loader();

    public void approve()
    {
        validator.ValidatorHWIDController(approve_hwid.getText());

        if(!validator.getError().equals(""))
        {
            alert_error.setContentText(validator.getError());
            alert_error.showAndWait();
            validator.setError("");
        }
        else
        {
            HWID hwid=new HWID();
            Database.DB db=new Database.DB();
            db.Create();
            Session session=db.session;

            session.beginTransaction();
            hwid.setId(approve_hwid.getText());
            session.save(hwid);
            session.getTransaction().commit();

            db.session.close();
            db.factory.close();

            information_alert.setHeaderText("Success!");
            information_alert.setContentText("Successfully added an HWID!");
            information_alert.showAndWait();
        }
    }

    public void action_this_pc_hwid() throws IOException { loader.LoadThisHWID(); }
    public void action_list_of_hwid() throws IOException { loader.LoadListHWID(); }
}