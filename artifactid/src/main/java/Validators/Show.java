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
        (TableView<AdminShow> tableview, List<Employee> l, List<Dates> list, Calendar cal, Date d1, SimpleDateFormat f, TextField search) throws ParseException
        {
            ObservableList<AdminShow>d=FXCollections.observableArrayList();
            tableview.getItems().clear();

            for (Employee o:l)
            {
                AdminShow a=new AdminShow();
                long td=0;

                for(Dates date:list)
                {
                    if(date.getE_id()==(o.getId()))
                    {
                        if(cal.getTime().before(f.parse(f.format(date.getEnddate().getTime()))))
                        { d1=cal.getTime(); }
                        else
                        d1=f.parse(f.format(date.getEnddate().getTime()));
                        Date d2=f.parse(f.format(date.getStartdate().getTime()));
                        td+=d1.getTime()-d2.getTime();
                        a.setTime(""+td/(60*60*1000)%24+":"+td/(60*1000)%60+":"+td/1000%60);
                    }
                }

                if(td!=0)
                {
                    a.setFirst(o.getFirst());
                    a.setMiddle(o.getMiddle());
                    a.setLast(o.getLast());
                    d.add(a);
                }
            }
            tableview.getItems().addAll(d);

            FilteredList<AdminShow> fd=new FilteredList<>(d, e->true);
            search.setOnKeyReleased(e ->
            {
                search.textProperty().addListener((observableValue, oldValue, newValue) ->
                fd.setPredicate((Predicate<? super AdminShow>) as ->
                {
                    if(newValue==null || newValue.isEmpty()) { return true; }
                    String lower=newValue.toLowerCase();
                    if(as.getFirst().toLowerCase().contains(lower)) { return true; }
                    else if(as.getMiddle().toLowerCase().contains(lower)) { return true; }
                    else return as.getLast().toLowerCase().contains(lower);
                }));

                SortedList<AdminShow>sd=new SortedList<>(fd);
                sd.comparatorProperty().bind(tableview.comparatorProperty());
                tableview.getItems().clear();
                tableview.getItems().addAll(sd);
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
            final String servername="pool.ntp.org";
            NTPUDPClient client=new NTPUDPClient();
            client.setDefaultTimeout(10_000);

            TimeInfo info=client.getTime(InetAddress.getByName(servername));
            info.computeDetails();
            Long offset=info.getOffset();
            long millis=System.currentTimeMillis();

            return new Timestamp(millis+offset);
        }

        public String ThisHWID() throws IOException
        {
            String id;
            StringBuilder sb=new StringBuilder();
            String str;

            Process pr=Runtime.getRuntime().exec("wmic csproduct get UUID");
            BufferedReader br=new BufferedReader(new InputStreamReader(pr.getInputStream()));

            while ((str=br.readLine())!=null) { sb.append(str).append("\n"); }
            id=sb.toString().substring(sb.indexOf("\n"), sb.length()).trim();
            return id;
        }
}