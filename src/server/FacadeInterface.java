package server;

import entities.Profile;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
public interface FacadeInterface {
    public String getProfilesAsJSON();
    public Profile getProfileAsJSON(String json);
    public Profile addProfileFromGSON(String json);
}
