package Controllers;
import Inqueries.AdminShow;
import Tables.Dates;
import Tables.Employee;
import Validators.Database;
import Validators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.query.Query;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import Validators.Loader.LoadLogin;
import Validators.Loader.LoadCreate;
import Validators.Loader.LoadHWID;
import Validators.Show;

public class AdminController
{
    @FXML Button button, log, create, month, day, seven, hardware;
    @FXML TextField search;
    @FXML DatePicker date, date2;
    @FXML TableView<AdminShow>tableview;
    @FXML private TableColumn<AdminShow, String>last;
    @FXML private TableColumn<AdminShow, String>time;
    @FXML private TableColumn<AdminShow, String>first;
    @FXML private TableColumn<AdminShow, String>middle;
    private static SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
    Calendar cal=Calendar.getInstance();
    Calendar cal1=Calendar.getInstance();
    public static Database.DB db=new Database.DB();
    List<Dates>list;
    List<Employee>l;
    Date d1;
    Show b=new Show();

    @FXML public void initialize() throws ParseException
    {
        db.Create();
        date.setValue(LocalDate.now());
        date2.setValue(LocalDate.now());
        first.setCellValueFactory(new PropertyValueFactory<>("first"));
        middle.setCellValueFactory(new PropertyValueFactory<>("middle"));
        last.setCellValueFactory(new PropertyValueFactory<>("last"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        action4();
    }

    public void action() throws ParseException
    {
        Validators.DV dv=new Validators.DV(date, date2);

        if(!Validators.error)
        {
            cal.setTime(java.sql.Date.valueOf(date2.getValue()));
            cal.add(Calendar.HOUR_OF_DAY, 23);
            cal.add(Calendar.MINUTE, 59);
            cal.add(Calendar.SECOND, 59);

            Query q=db.session.createQuery("FROM Dates where startdate >= :d and enddate <= :d2");
            q.setParameter("d", java.sql.Date.valueOf(date.getValue()));
            q.setParameter("d2", cal.getTime());
            list=q.list();

            b.Table(tableview, l, list, cal, d1, f, search);
            b.Disable(button, log, create, month, day, seven, hardware, search);
        }
    }

    public void action2() throws IOException
    {
        LoadLogin ll=new LoadLogin();
        db.session.close();
        db.factory.close();
    }

    public void action3() throws IOException { LoadCreate lc=new LoadCreate(); }

    public void action4() throws ParseException
    {
        l=db.session.createQuery("FROM Employee").list();

        cal.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        cal.add(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);

        date.setValue(LocalDate.now());
        date2.setValue(LocalDate.now());

        Query q=db.session.createQuery("FROM Dates where startdate >= :d and enddate <= :d2");
        q.setParameter("d", Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        q.setParameter("d2", cal.getTime());
        list=q.list();

        b.Table(tableview, l, list, cal, d1, f, search);
        b.Disable(button, log, create, month, day, seven, hardware, search);
    }

    public void action5() throws ParseException
    {
        l=db.session.createQuery("FROM Employee").list();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal1=Calendar.getInstance();
        cal1.add(Calendar.MONTH, 1);
        cal1.set(Calendar.DATE, 1);
        cal1.add(Calendar.DATE, -1);

        date.setValue(LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate());
        date2.setValue(LocalDateTime.ofInstant(cal1.toInstant(), cal1.getTimeZone().toZoneId()).toLocalDate());

        Query q=db.session.createQuery("FROM Dates where startdate >= :d and enddate <= :d2");
        q.setParameter("d", cal.getTime());
        q.setParameter("d2", cal1.getTime());
        list=q.list();

        b.Table(tableview, l, list, cal, d1, f, search);
        b.Disable(button, log, create, month, day, seven, hardware, search);
    }

    public void action6() throws ParseException
    {
        l=db.session.createQuery("FROM Employee").list();

        cal.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        cal.add(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);

        cal1.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        cal1.add(Calendar.DATE, -7);

        date.setValue(LocalDateTime.ofInstant(cal1.toInstant(), cal1.getTimeZone().toZoneId()).toLocalDate());
        date2.setValue(LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate());

        Query q=db.session.createQuery("FROM Dates where startdate >= :d and enddate <= :d2");
        q.setParameter("d", cal1.getTime());
        q.setParameter("d2", cal.getTime());
        list=q.list();

        b.Table(tableview, l, list, cal, d1, f, search);
        b.Disable(button, log, create, month, day, seven, hardware, search);
    }

    public void action7() throws IOException { LoadHWID hw=new LoadHWID(); }
}