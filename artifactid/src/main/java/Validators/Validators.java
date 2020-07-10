package Validators;
import Tables.Employee;
import Tables.HWID;
import javafx.scene.control.DatePicker;
import java.util.List;

@SuppressWarnings("unchecked")
public class Validators
{
    private String error="";

    public void ValidatorCreateController(String first, String middle, String last, String username)
    {
        if(first.equals("") || middle.equals("") || last.equals("") || username.equals(""))
        { error="The field(s) must not be empty!"; }
        else if(first.length()>30 || middle.length()>30 || last.length()>30 || username.length()>30)
        { error="The field(s) must not contain more that 30 characters!"; }
        else
        {
            Database.DB db=new Database.DB();
            db.Create();
            List<Employee>list_employee=db.session.createQuery("from Employee").list();

            for(Object object:list_employee)
            {
                if((((Employee)object).getUsername()).equals(username))
                {
                    error="There is already an employee with that username!";
                    break;
                }
            }
            db.session.close();
            db.factory.close();
        }
    }

    public void ValidatorAdminLogin(DatePicker date_picker, DatePicker date_picker1)
    {
        if(date_picker.getValue()==null || date_picker1.getValue()==null || date_picker1.getValue().isBefore(date_picker.getValue()))
        { error="The field must not be empty or have a invalid value!"; }
    }

    public void ValidatorHWIDController(String approve_hwid)
    {
        if(approve_hwid.equals(""))
        { error="The field must not be empty!"; }
        else if(approve_hwid.length()>50)
        { error="The field must not contain more that 50 characters!"; }
        else
        {
            Database.DB db=new Database.DB();
            db.Create();
            List<HWID>list_hwid=db.session.createQuery("from HWID").list();

            for(Object object:list_hwid)
            {
                if((((HWID)object).getId()).equals(approve_hwid))
                {
                    error="That HWID is added already!";
                    break;
                }
            }
            db.session.close();
            db.factory.close();
        }
    }

    public String getError() { return error; }
    public void setError(String error) { this.error=error; }
}