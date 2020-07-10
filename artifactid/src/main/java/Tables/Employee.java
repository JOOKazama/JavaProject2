package Tables;
import javax.persistence.*;

@Entity
@Table(name="employee")
public class Employee
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true) @Id private int id;
    @Column(name="first") private String first;
    @Column(name="middle") private String middle;
    @Column(name="last") private String last;
    @Column(name="username") private String username;
    @Column(name="password") private String password;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirst() { return first; }
    public void setFirst(String first) { this.first=first; }
    public String getMiddle() { return middle; }
    public void setMiddle(String middle) { this.middle=middle; }
    public String getLast() { return last; }
    public void setLast(String last) { this.last=last; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username=username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password=password; }
}