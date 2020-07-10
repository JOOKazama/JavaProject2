package Validators;
import Inqueries.AdminShow;
import Tables.Dates;
import Tables.Employee;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class Show
{
        public void Table
        (TableView<AdminShow>table_view_admin_show, List<Employee>list_employee, List<Dates>list_dates, Calendar calendar, TextField search) throws ParseException
        {
            SimpleDateFormat simple_date_format=new SimpleDateFormat("HH:mm:ss");
            ObservableList<AdminShow>observable_list_admin_show=FXCollections.observableArrayList();
            Date date;
            table_view_admin_show.getItems().clear();

            for (Employee employee:list_employee)
            {
                AdminShow admin_show=new AdminShow();
                long value=0;

                for(Dates date1:list_dates)
                {
                    if(date1.getE_id()==(employee.getId()))
                    {
                        if(calendar.getTime().before(simple_date_format.parse(simple_date_format.format(date1.getEnddate().getTime()))))
                        { date=calendar.getTime(); }
                        else
                        date=simple_date_format.parse(simple_date_format.format(date1.getEnddate().getTime()));
                        Date d2=simple_date_format.parse(simple_date_format.format(date1.getStartdate().getTime()));
                        value+=date.getTime()-d2.getTime();
                        admin_show.setTime(""+value/(60*60*1000)%24+":"+value/(60*1000)%60+":"+value/1000%60);
                    }
                }

                if(value!=0)
                {
                    admin_show.setFirst(employee.getFirst());
                    admin_show.setMiddle(employee.getMiddle());
                    admin_show.setLast(employee.getLast());
                    observable_list_admin_show.add(admin_show);
                }
            }
            table_view_admin_show.getItems().addAll(observable_list_admin_show);

            FilteredList<AdminShow>filtered_list_admin_show=new FilteredList<>(observable_list_admin_show, e->true);
            search.setOnKeyReleased(e->
            {
                search.textProperty().addListener((observableValue, oldValue, newValue) ->
                filtered_list_admin_show.setPredicate((Predicate<?super AdminShow>) admin_show ->
                {
                    if(newValue==null || newValue.isEmpty()) { return true; }
                    String lower_case=newValue.toLowerCase();
                    if(admin_show.getFirst().toLowerCase().contains(lower_case)) { return true; }
                    else if(admin_show.getMiddle().toLowerCase().contains(lower_case)) { return true; }
                    else return admin_show.getLast().toLowerCase().contains(lower_case);
                }));

                SortedList<AdminShow>sorted_list_admin_show=new SortedList<>(filtered_list_admin_show);
                sorted_list_admin_show.comparatorProperty().bind(table_view_admin_show.comparatorProperty());
                table_view_admin_show.getItems().clear();
                table_view_admin_show.getItems().addAll(sorted_list_admin_show);
            });
        }

        public void Disable
        (Button button, Button log, Button create, Button month, Button day, Button seven, Button hardware, TextField search)
        {
            button.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));
            log.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));
            create.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));
            month.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));
            day.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));
            seven.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));
            hardware.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));
        }

        public Timestamp Time() throws IOException
        {
            final String server_name="pool.ntp.org";
            NTPUDPClient client=new NTPUDPClient();
            client.setDefaultTimeout(10_000);

            TimeInfo time_info=client.getTime(InetAddress.getByName(server_name));
            time_info.computeDetails();
            Long off_set=time_info.getOffset();
            long milliseconds=System.currentTimeMillis();

            return new Timestamp(milliseconds+off_set);
        }

        public String ThisHWID() throws IOException
        {
            String id;
            StringBuilder string_builder=new StringBuilder();
            String string_id;

            Process process=Runtime.getRuntime().exec("wmic csproduct get UUID");
            BufferedReader buffered_reader=new BufferedReader(new InputStreamReader(process.getInputStream()));

            while((string_id=buffered_reader.readLine())!=null) { string_builder.append(string_id).append("\n"); }
            id=string_builder.toString().substring(string_builder.indexOf("\n"), string_builder.length()).trim();
            return id;
        }
}