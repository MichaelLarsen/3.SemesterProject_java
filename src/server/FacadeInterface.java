package server;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
public interface FacadeInterface {
    public String getPersonsAsJSON();
    public String getPersonAsJSON(long id);
    public Person addPersonFromGSON(String json);
    public RoleSchool addRoleFromGSON(String json, long id);
    public Person delete(long id);
}
