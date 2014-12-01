package server;

import entities.Profile;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
public interface FacadeInterface {
    public String getProfilesAsJSON();
    public String getProfileAsJSON(long id);
    public Profile addProfileFromGSON(String json);
}
