package Tables;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity @Table(name="dates")
public class Dates
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true) @Id private int id;
    @Column(name="startdate") private Timestamp startdate;
    @Column(name="enddate") private Timestamp enddate;
    @Column(name="e_id") private int e_id;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Timestamp getStartdate() { return startdate; }
    public void setStartdate(Timestamp startdate) { this.startdate = startdate; }
    public Timestamp getEnddate() { return enddate; }
    public void setEnddate(Timestamp enddate) { this.enddate = enddate; }
    public int getE_id() { return e_id; }
    public void setE_id(int e_id) { this.e_id = e_id; }
}