package Controllers;
import Inqueries.AdminShow;
import Tables.Dates;
import Tables.Employee;
import Validators.Database;
import Validators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.query.Query;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import Validators.Show;
import Validators.Loader;

@SuppressWarnings("unchecked")
public class AdminController
{
    @FXML Button button_show, button_log_out, create, button_show_month, button_show_day, button_show_last_seven_days, add_hardware;
    @FXML TextField search;
    @FXML DatePicker date, date2;
    @FXML TableView<AdminShow>table_view_admin_show;
    @FXML private TableColumn<AdminShow, String>last;
    @FXML private TableColumn<AdminShow, String>time;
    @FXML private TableColumn<AdminShow, String>first;
    @FXML private TableColumn<AdminShow, String>middle;
    Calendar calendar=Calendar.getInstance();
    Calendar calendar1=Calendar.getInstance();
    public static Database.DB db=new Database.DB();
    List<Dates>list_dates;
    List<Employee>list_employee;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Show show=new Show();
    Loader loader=new Loader();

    @FXML public void initialize() throws ParseException
    {
        db.Create();
        date.setValue(LocalDate.now());
        date2.setValue(LocalDate.now());
        first.setCellValueFactory(new PropertyValueFactory<>("first"));
        middle.setCellValueFactory(new PropertyValueFactory<>("middle"));
        last.setCellValueFactory(new PropertyValueFactory<>("last"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        show_for_today();
    }

    public void show() throws ParseException
    {
        validator.ValidatorAdminLogin(date, date2);
        alert_error.setHeaderText("Error!");

        if(!validator.getError().equals(""))
        {
            alert_error.setContentText(validator.getError());
            alert_error.showAndWait();
            validator.setError("");
        }
        else
        {
            calendar.setTime(java.sql.Date.valueOf(date2.getValue()));
            calendar.add(Calendar.HOUR_OF_DAY, 23);
            calendar.add(Calendar.MINUTE, 59);
            calendar.add(Calendar.SECOND, 59);

            Query<Dates>query_dates=db.session.createQuery("FROM Dates where startdate>=:first and enddate<=:second");
            query_dates.setParameter("first", java.sql.Date.valueOf(date.getValue()));
            query_dates.setParameter("second", calendar.getTime());
            list_dates=query_dates.list();

            show.Table(table_view_admin_show, list_employee, list_dates, calendar, search);
            show.Disable(button_show, button_log_out, create, button_show_month, button_show_day, button_show_last_seven_days, add_hardware, search);
        }
    }

    public void log_out() throws IOException
    {
        Stage stage=(Stage)button_log_out.getScene().getWindow();
        stage.close();
        loader.LoadLogin();
        db.session.close();
        db.factory.close();
    }

    public void create_new_employee() throws IOException { loader.LoadCreate(); }

    public void show_for_today() throws ParseException
    {
        list_employee=db.session.createQuery("FROM Employee").list();

        calendar.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);

        date.setValue(LocalDate.now());
        date2.setValue(LocalDate.now());

        Query<Dates>query_dates=db.session.createQuery("FROM Dates where startdate>=:first and enddate<=:second");
        query_dates.setParameter("first", Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        query_dates.setParameter("second", calendar.getTime());
        list_dates=query_dates.list();

        show.Table(table_view_admin_show, list_employee, list_dates, calendar, search);
        show.Disable(button_show, button_log_out, create, button_show_month, button_show_day, button_show_last_seven_days, add_hardware, search);
    }

    public void show_for_the_month() throws ParseException
    {
        list_employee=db.session.createQuery("FROM Employee").list();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar1=Calendar.getInstance();
        calendar1.add(Calendar.MONTH, 1);
        calendar1.set(Calendar.DATE, 1);
        calendar1.add(Calendar.DATE, -1);

        date.setValue(LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate());
        date2.setValue(LocalDateTime.ofInstant(calendar1.toInstant(), calendar1.getTimeZone().toZoneId()).toLocalDate());

        Query<Dates>query_dates=db.session.createQuery("FROM Dates where startdate>=:first and enddate<=:second");
        query_dates.setParameter("first", calendar.getTime());
        query_dates.setParameter("second", calendar1.getTime());
        list_dates=query_dates.list();

        show.Table(table_view_admin_show, list_employee, list_dates, calendar, search);
        show.Disable(button_show, button_log_out, create, button_show_month, button_show_day, button_show_last_seven_days, add_hardware, search);
    }

    public void show_for_the_last_seven_days() throws ParseException
    {
        list_employee=db.session.createQuery("FROM Employee").list();

        calendar.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);

        calendar1.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        calendar1.add(Calendar.DATE, -7);

        date.setValue(LocalDateTime.ofInstant(calendar1.toInstant(), calendar1.getTimeZone().toZoneId()).toLocalDate());
        date2.setValue(LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate());

        Query<Dates>query_dates=db.session.createQuery("FROM Dates where startdate>=:first and enddate<=:second");
        query_dates.setParameter("first", calendar1.getTime());
        query_dates.setParameter("second", calendar.getTime());
        list_dates=query_dates.list();

        show.Table(table_view_admin_show, list_employee, list_dates, calendar, search);
        show.Disable(button_show, button_log_out, create, button_show_month, button_show_day, button_show_last_seven_days, add_hardware, search);
    }

    public void action_add_hardware() throws IOException { loader.LoadHWID(); }
}