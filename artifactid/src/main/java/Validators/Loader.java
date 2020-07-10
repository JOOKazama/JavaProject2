package Validators;
import Controllers.LoginController;
import Tables.Dates;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import Controllers.AdminController;

public class Loader
{
    private Stage primary_stage=new Stage();
    private Stage stage=new Stage();
    private Parent root, parent;

    public void LoadLogin() throws IOException
    {
        root=FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        primary_stage.setResizable(false);
        primary_stage.setTitle("Login");
        primary_stage.setScene(new Scene(root));
        primary_stage.show();
    }

    public void LoadEmployee() throws IOException
    {
        root=FXMLLoader.load(getClass().getResource("/fxml/Employee.fxml"));
        primary_stage.setTitle("Main window for employee");
        primary_stage.setScene(new Scene(root));
        primary_stage.show();

        primary_stage.setOnCloseRequest(evt->
        {
            evt.consume();
            Dates dates=new Dates();
            Database.DB db=new Database.DB();
            db.Create();
            Show show=new Show();

            db.session.beginTransaction();
            dates.setStartdate(LoginController.timestamp);
            try { dates.setEnddate(show.Time()); }
            catch(IOException e) { e.printStackTrace(); }
            dates.setE_id(LoginController.id);
            db.session.save(dates);
            db.session.getTransaction().commit();
            db.session.close();
            db.factory.close();
            primary_stage.close();
        });
    }

    public void LoadAdmin() throws IOException
    {
        root=FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
        primary_stage.setTitle("Main window for admin");
        primary_stage.setScene(new Scene(root));
        primary_stage.show();

        primary_stage.setOnCloseRequest(evt->
        {
            evt.consume();
            AdminController.db.session.close();
            AdminController.db.factory.close();
            primary_stage.close();
        });
    }

    public void LoadCreate() throws IOException
    {
        parent=FXMLLoader.load(getClass().getResource("/fxml/Create.fxml"));
        stage.setTitle("Window for creating employee");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void LoadHWID() throws IOException
    {
        parent=FXMLLoader.load(getClass().getResource("/fxml/Hardware.fxml"));
        stage.setTitle("Window for Adding HWID");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void LoadThisHWID() throws IOException
    {
        parent=FXMLLoader.load(getClass().getResource("/fxml/PCHWID.fxml"));
        stage.setTitle("This PC's ID");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void LoadListHWID() throws IOException
    {
        parent=FXMLLoader.load(getClass().getResource("/fxml/ListHWID.fxml"));
        stage.setTitle("List of HWID's");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public Stage getPrimary_stage() { return primary_stage; }
    public void setPrimary_stage(Stage primary_stage) { this.primary_stage=primary_stage; }
    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage=stage; }
    public Parent getRoot() { return root; }
    public void setRoot(Parent root) { this.root=root; }
    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent=parent; }
    public void closeStage() { primary_stage.close(); }
}