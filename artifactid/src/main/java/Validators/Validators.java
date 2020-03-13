package Validators;
import Tables.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import java.util.List;

public class Validators
{
    private static Alert e = new Alert(Alert.AlertType.ERROR);
    public static boolean error=false;

    public static class EV
    {
        public EV(String f, String m, String l, String u)
        {
            if (f.equals("") || m.equals("") || l.equals("") || u.equals(""))
            {
                e.setHeaderText("Error!");
                e.setContentText("The field(s) must not be empty!");
                e.showAndWait();
                error=true;
            }
            else if (f.length() > 30 || m.length() > 30 || l.length() > 30 || u.length() > 30)
            {
                e.setHeaderText("Error!");
                e.setContentText("The field(s) must not contain more that 30 characters!");
                e.showAndWait();
                error=true;
            }
            else
            {
                Database.DB db= new Database.DB();
                List emp = db.session.createQuery("from Employee").list();

                for (Object o : emp)
                {
                    if ((((Employee) o).getUsername()).equals(u))
                    {
                        e.setHeaderText("Error!");
                        e.setContentText("There is already an employee with that username!");
                        e.showAndWait();
                        error = true;
                        break;
                    }
                }
                db.session.close();
                db.factory.close();
            }
        }
    }

    public static class DV
    {
        public DV(DatePicker d, DatePicker d2)
        {
            if(d.getValue()==null || d2.getValue()==null || d2.getValue().isBefore(d.getValue()))
            {
                e.setHeaderText("Error!");
                e.setContentText("The field must not be empty or have a invalid value!");
                e.showAndWait();
                error=true;
            }
        }
    }
}