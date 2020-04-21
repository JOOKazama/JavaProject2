package Tables;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="hwid")
public class HWID
{
    @Column(name="id", unique=true) @Id private String id;

    public String getId() { return id; }
    public void setId(String id) { this.id=id; }
}