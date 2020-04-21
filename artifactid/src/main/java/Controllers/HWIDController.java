package Controllers;
import Tables.HWID;
import Validators.Loader.LoadThisHWID;
import Validators.Loader.LoadListHWID;
import Validators.Validators;
import Validators.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import java.io.IOException;

public class HWIDController
{
    @FXML public TextField text;
    public Button approve, see, list;
    private Alert alert=new Alert(Alert.AlertType.INFORMATION);

    public void action()
    {
        Validators.HW hwid=new Validators.HW(text.getText());

        if(!Validators.error)
        {
            HWID a=new HWID();
            Database.DB db=new Database.DB();
            db.Create();
            Session session=db.session;

            session.beginTransaction();
            a.setId(text.getText());
            session.save(a);
            session.getTransaction().commit();

            db.session.close();
            db.factory.close();

            alert.setHeaderText("Success!");
            alert.setContentText("Successfully added an HWID!");
            alert.showAndWait();
        }
    }

    public void action2() throws IOException { LoadThisHWID ll=new LoadThisHWID(); }

    public void action3() throws IOException { LoadListHWID ll=new LoadListHWID(); }
}