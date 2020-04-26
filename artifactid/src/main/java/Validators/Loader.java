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
    public static Stage pr=new Stage();
    public static Stage st=new Stage();
    public static Parent root;

    public static class LoadLogin
    {
        public LoadLogin() throws IOException
        {
            st.close();
            root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            pr.setResizable(false);
            pr.setTitle("Login");
            pr.setScene(new Scene(root));
            pr.show();
        }
    }

    public static class LoadEmployee
    {
        public LoadEmployee() throws IOException
        {
            pr.close();

            Loader.st.setOnCloseRequest(evt ->
            {
                evt.consume();
                Dates d=new Dates();
                Database.DB db=new Database.DB();
                db.Create();
                Show b=new Show();

                db.session.beginTransaction();
                d.setStartdate(LoginController.time);
                try { d.setEnddate(b.Time()); } catch (IOException e) { e.printStackTrace(); }
                d.setE_id(LoginController.id);
                db.session.save(d);
                db.session.getTransaction().commit();
                db.session.close();
                db.factory.close();
                st.close();
            });

            root=FXMLLoader.load(getClass().getResource("/fxml/Employee.fxml"));
            st.setTitle("Main window for employee");
            st.setScene(new Scene(root));
            st.show();
        }
    }

    public static class LoadAdmin
    {
        public LoadAdmin() throws IOException
        {
            Loader.pr.setOnCloseRequest(evt ->
            {
                evt.consume();
                AdminController.db.session.close();
                AdminController.db.factory.close();
                pr.close();
            });

            root = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
            pr.setTitle("Main window for admin");
            pr.setScene(new Scene(root));
            pr.show();
        }
    }

    public static class LoadCreate
    {
        public LoadCreate() throws IOException
        {
            Stage cr=new Stage();
            Parent r = FXMLLoader.load(getClass().getResource("/fxml/Create.fxml"));
            cr.setTitle("Window for creating employee");
            cr.setScene(new Scene(r));
            cr.show();
        }
    }

    public static class LoadHWID
    {
        public LoadHWID() throws IOException
        {
            Stage cr=new Stage();
            Parent r=FXMLLoader.load(getClass().getResource("/fxml/Hardware.fxml"));
            cr.setTitle("Window for Adding HWID");
            cr.setScene(new Scene(r));
            cr.show();
        }
    }

    public static class LoadThisHWID
    {
        public LoadThisHWID() throws IOException
        {
            Stage cr=new Stage();
            Parent r=FXMLLoader.load(getClass().getResource("/fxml/PCHWID.fxml"));
            cr.setTitle("This PC's ID");
            cr.setScene(new Scene(r));
            cr.show();
        }
    }

    public static class LoadListHWID
    {
        public LoadListHWID() throws IOException
        {
            Stage cr=new Stage();
            Parent r=FXMLLoader.load(getClass().getResource("/fxml/ListHWID.fxml"));
            cr.setTitle("List of HWID's");
            cr.setScene(new Scene(r));
            cr.show();
        }
    }
}