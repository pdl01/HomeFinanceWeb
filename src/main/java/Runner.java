
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

public class Runner {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //Runner.mainOne(args);
        //return;
        
        final int port = Integer.parseInt(System.getProperty("port", "8080"));
        final String home = System.getProperty("home", "/home/pldorrell/jsps");
        Server server = new Server(port);
        ProtectionDomain domain = Runner.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        if (home.length() != 0) {
            webapp.setTempDirectory(new File(home));
        }

        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);
        server.start();
        server.join();
                
    }

    public static void mainOne(String[] args) {
        Server srv = new Server(8080);
        srv.setStopAtShutdown(true);

// Get the war-file
        ProtectionDomain protectionDomain = Runner.class.getProtectionDomain();
        String warFile = protectionDomain.getCodeSource().getLocation().toExternalForm();
        String currentDir = new File(protectionDomain.getCodeSource().getLocation().getPath()).getParent();

// Add the warFile (this jar)
        String contextPath = "/";
        WebAppContext context = new WebAppContext(warFile, contextPath);
        context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        try {
            ClassLoader jspClassLoader = new URLClassLoader(new URL[0], context.getClass().getClassLoader());
            
            //classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");
            context.setClassLoader(jspClassLoader);
            //context.setClassLoader(new WebAppClassLoader(getClass().getClassLoader(), context));
        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
        context.setServer(srv);
        // Add the handlers
        HandlerList handlers = new HandlerList();
        handlers.addHandler(context);
        srv.setHandler(handlers);

        try {
            srv.start();
            srv.join();

        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
