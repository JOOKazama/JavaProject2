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
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ListHWIDController
{
    @FXML TextField search;
    @FXML TableView<HWID>table_view_list_hwid;
    @FXML private TableColumn<HWID, String>id;
    public static Database.DB db=new Database.DB();
    List<HWID>list_hwid;

    @FXML public void initialize()
    {
        db.Create();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        list_hwid=db.session.createQuery("FROM HWID").list();
        ObservableList<HWID>d=FXCollections.observableArrayList();
        d.addAll(list_hwid);

        table_view_list_hwid.getItems().clear();
        table_view_list_hwid.getItems().addAll(d);

        FilteredList<HWID>filtered_list_hwid=new FilteredList<>(d, e->true);
        search.setOnKeyReleased(e->
        {
            search.textProperty().addListener((observableValue, oldValue, newValue) ->
            filtered_list_hwid.setPredicate((Predicate<?super HWID>)as ->
            {
                if(newValue==null || newValue.isEmpty()) { return true; }
                String lower_case=newValue.toLowerCase();
                return as.getId().toLowerCase().contains(lower_case);
            }));

            SortedList<HWID>sorted_list_hwid=new SortedList<>(filtered_list_hwid);
            sorted_list_hwid.comparatorProperty().bind(table_view_list_hwid.comparatorProperty());
            table_view_list_hwid.getItems().clear();
            table_view_list_hwid.getItems().addAll(sorted_list_hwid);
        });
    }
}