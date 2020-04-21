package Controllers;
import Validators.Show;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;

public class PCHWIDController
{
    @FXML TextField tf;
    Show b=new Show();

    @FXML public void initialize() throws IOException
    {
        tf.setEditable(false);
        tf.setMouseTransparent(true);
        tf.setFocusTraversable(true);
        tf.setText(b.ThisHWID());
    }
}