package Controllers;
import Validators.Show;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;

public class PCHWIDController
{
    @FXML TextField hwid;
    Show show=new Show();

    @FXML public void initialize() throws IOException
    {
        hwid.setEditable(false);
        hwid.setMouseTransparent(true);
        hwid.setFocusTraversable(true);
        hwid.setText(show.ThisHWID());
    }
}