/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;

/** 
 *
 * @author Michael
 */
public class PasswordHandler {
    private final Facade facade;
    private final Gson gson;

    public PasswordHandler() {
        facade = Facade.getFacade(true);
        gson = new Gson();
    }
    
}
