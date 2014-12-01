package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collection;

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

    @Override
    public RoleSchool addRoleFromGSON(String json, long id) {
        EntityManager em = emf.createEntityManager();
        RoleSchool role = gson.fromJson(json, RoleSchool.class);
        if (role.getRoleName().equals("Student")) {
            role = gson.fromJson(json, Student.class);
        }
        if (role.getRoleName().equals("Teacher")) {
            role = gson.fromJson(json, Teacher.class);
        }
        if (role.getRoleName().equals("AssistantTeacher")) {
            role = gson.fromJson(json, AssistantTeacher.class);
        }
        Person person = null;

        em.getTransaction().begin();
        try {
            person = em.find(Person.class, id);
            role.setPerson(person);
            person.addRole(role);
            em.persist(role);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return role;
    }

    @Override
    public Person delete(long id) {
        EntityManager em = emf.createEntityManager();
        Person person = null;

        em.getTransaction().begin();
        try {
            person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            return null;
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
