package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entities.Profile;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
public class WebServer {
    
    private static int port = 8080;
    private static String ip = "127.0.0.1";
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException
    {
        if (args.length == 2)
        {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        
        Facade facade = Facade.getFacade(true);
        
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        Profile profile1 = new Profile("MichaelLarsen", "larsen_max@hotmail.com", "1234", sqlDate, "student");
        facade.addProfileFromGSON(gson.toJson(profile1));
        Profile profile2 = new Profile("EmilAndreas", "larsen_max@hotmail.com", "1234", sqlDate, "student");
        facade.addProfileFromGSON(gson.toJson(profile2));
        System.out.println("Profile1: " + profile1);
        System.out.println("Profile2: " + profile2);
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/Profiles", new ProfileHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Started the server, listening on:");
        System.out.println("port: " + port);
        System.out.println("ip: " + ip);
    }
}
