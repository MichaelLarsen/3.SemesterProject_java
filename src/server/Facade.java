package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Profile;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
public class Facade implements FacadeInterface {

    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static Facade instance = new Facade();
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("3.SemesterProject_javaPU");

    private Facade() {
    }

    public static Facade getFacade(boolean reset) {
        if (reset) {
            instance = new Facade();
        }
        return instance;
    }

    @Override
    public String getProfilesAsJSON() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Profile.getProfileAll");
            Collection<Profile> profileList = query.getResultList();
            String profileListJson = gson.toJson(profileList);
            return profileListJson;
        } finally {
            em.close();
        }
    }

    @Override
    public String authenticator(String json) {
        Profile profile = gson.fromJson(json, Profile.class);
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Profile.authenticate").setParameter("username", profile.getUsername()).setParameter("password", profile.getPw());
            Collection<Profile> profileList = query.getResultList();
            String profiles = gson.toJson(profileList.iterator().next()); 
            return profiles;
        } finally {
            em.close();
        }        
    }

    @Override
    public Profile addProfileFromGSON(String json) {
        EntityManager em = emf.createEntityManager();
        Profile profile = gson.fromJson(json, Profile.class);

        em.getTransaction().begin();
        try {
            em.persist(profile);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return profile;
    }

    public String changePassword(String json) {
        System.out.println("json: "+ json);
        Profile profile = gson.fromJson(json, Profile.class);
        EntityManager em = emf.createEntityManager();
        System.out.println("changePassword- profile: " + profile);
        
        Profile prfl = em.find(Profile.class, profile.getId());
        System.out.println("prfl: " + prfl);
        prfl.setPw(profile.getPw());
        em.getTransaction().begin();
        try {
            em.persist(prfl);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return gson.toJson(prfl);  
    }
}
