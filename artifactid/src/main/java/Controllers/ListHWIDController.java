package Controllers;
import Tables.HWID;
import Validators.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.query.Query;
import java.util.List;
import java.util.function.Predicate;

public class ListHWIDController
{
    @FXML TextField search;
    @FXML TableView<HWID>tableview;
    @FXML private TableColumn<HWID, String>id;
    public static Database.DB db=new Database.DB();
    List<HWID>list;

    @FXML public void initialize()
    {
        db.Create();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Query q=db.session.createQuery("FROM HWID");
        list=q.list();
        ObservableList<HWID>d=FXCollections.observableArrayList();
        d.addAll(list);

        tableview.getItems().clear();
        tableview.getItems().addAll(d);

        FilteredList<HWID> fd=new FilteredList<>(d, e->true);
        search.setOnKeyReleased(e ->
        {
            search.textProperty().addListener((observableValue, oldValue, newValue) ->
            fd.setPredicate((Predicate<?super HWID>) as ->
            {
                if(newValue==null || newValue.isEmpty()) { return true; }
                String lower=newValue.toLowerCase();
                return as.getId().toLowerCase().contains(lower);
            }));

            SortedList<HWID>sd=new SortedList<>(fd);
            sd.comparatorProperty().bind(tableview.comparatorProperty());
            tableview.getItems().clear();
            tableview.getItems().addAll(sd);
        });
    }
}