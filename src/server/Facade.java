package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
public class Facade implements FacadeInterface {

    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static Facade instance = new Facade();
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("KA_InheritancePU");

    private Facade() {
    }

    public static Facade getFacade(boolean reset) {
        if (reset) {
            instance = new Facade();
        }
        return instance;
    }

    @Override
    public String getPersonsAsJSON() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Person.getPersonAll");
            Collection<Person> personList = query.getResultList();
            String personListJson = gson.toJson(personList);
            return personListJson;
        } finally {
            em.close();
        }
    }

    @Override
    public String getPersonAsJSON(long id) {
        EntityManager em = emf.createEntityManager();
        Person person = null;
        try {
            person = em.getReference(Person.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return gson.toJson(person);
    }

    @Override
    public Person addPersonFromGSON(String json) {
        EntityManager em = emf.createEntityManager();
        Person person = gson.fromJson(json, Person.class);

        em.getTransaction().begin();
        try {
            em.persist(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return person;
    }

    public int dropTables() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query1 = em.createNamedQuery("RoleSchool.dropAll");
            Query query2 = em.createNamedQuery("Person.dropAll");
            int rowsAffected = query1.executeUpdate();
            rowsAffected += query2.executeUpdate();
            em.getTransaction().commit();
            return rowsAffected;
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return 0;
    }
}
