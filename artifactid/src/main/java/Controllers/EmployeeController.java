package Controllers;
import Tables.Dates;
import Validators.Database;
import Validators.Loader.LoadLogin;
import Validators.Show;
import java.io.IOException;

public class EmployeeController
{
    public void action() throws IOException
    {
        Dates d=new Dates();
        Database.DB db=new Database.DB();
        db.Create();
        Show b=new Show();

        db.session.beginTransaction();
        d.setStartdate(LoginController.time);
        d.setEnddate(b.Time());
        d.setE_id(LoginController.id);
        db.session.save(d);
        db.session.getTransaction().commit();
        db.session.close();
        db.factory.close();

        LoadLogin ll=new LoadLogin();
    }
}