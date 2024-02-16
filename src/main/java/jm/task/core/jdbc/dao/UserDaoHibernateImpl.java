package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    //private final SessionFactory sessionFactory = Util.getSessionFactory();

    private Transaction transaction = null;
    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS users" + "(ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR(40) NOT NULL," + "LASTNAME VARCHAR(40) NOT NULL, " + "AGE TINYINT)";
    private static final String CLEAN_USERS_TABLE = "DELETE FROM users";
    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS users";



    public UserDaoHibernateImpl() {

    }




    @Override
    public void createUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createSQLQuery(CREATE_USER_TABLE).executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createSQLQuery(DROP_USER_TABLE).executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            User user = new User(name, lastName, age);

            session.save(user);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, id);

            if (user != null) {
                session.delete(user);
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        //return null;
        List<User> userList = new ArrayList<>();

        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User").getResultList();

            userList.forEach(System.out::println);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createSQLQuery(CLEAN_USERS_TABLE).addEntity(User.class).executeUpdate(); // TO DO

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
