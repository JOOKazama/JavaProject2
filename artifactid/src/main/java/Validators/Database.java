package Validators;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database
{
    public static class DB
    {
        public Session session;
        public SessionFactory factory;

        public void Create()
        {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            factory = cfg.buildSessionFactory();
            session = factory.openSession();
        }
    }
}