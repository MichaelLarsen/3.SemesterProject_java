package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
public class WebServer {
    
    private static int port = 8080;
    private static String ip = "127.0.0.1";
    private static final Gson gson = new Gson();
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        if (args.length == 2)
        {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        
        Facade facade = Facade.getFacade(true);
        
        Person michael = new Person("Michael", "Larsen", "12345678", "studerende@cphbusiness.dk");
        facade.addPersonFromGSON(gson.toJson(michael));
        
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/", new FileHandler());
        server.createContext("/Person", new PersonHandler());
        server.createContext("/Role", new RoleHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Started the server, listening on:");
        System.out.println("port: " + port);
        System.out.println("ip: " + ip);
    }
}
