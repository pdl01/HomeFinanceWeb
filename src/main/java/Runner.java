
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class Runner {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //Runner.mainOne(args);
        //return;
        
        final int port = Integer.parseInt(System.getProperty("hfw.port", "8080"));
        String x = System.getProperty("user.home")+ File.separator +".hfw_app"+File.separator+"temp";
        final String tempDir = System.getProperty("hfw.temp.dir", x);
        final String bindIP = System.getProperty("hfw.hostAddress","127.0.0.1");
        final String contextPath = System.getProperty("hfw.path","/");
        
        ProtectionDomain protectionDomain = Runner.class.getProtectionDomain();
        String warFile = protectionDomain.getCodeSource().getLocation().toExternalForm();
        String currentDir = new File(protectionDomain.getCodeSource().getLocation().getPath()).getParent();

        
        System.out.println ("Using ip:"+bindIP);
        System.out.println("Using port:"+port);
        System.out.println("Using path:"+contextPath);
        System.out.println("Using warfile:"+warFile);
        
        InetAddress address = InetAddress.getByName(bindIP);
        InetSocketAddress socketAddress = new InetSocketAddress(address,port);
        System.out.println(socketAddress.toString());
        
        Server server = new Server(socketAddress);
        //ProtectionDomain domain = Runner.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        WebAppContext webapp = new WebAppContext(warFile,contextPath);
        
        //webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
	webapp.setParentLoaderPriority(true);
        /*
                    webapp.setConfigurations(new Configuration[] {
				new AnnotationConfiguration(), new WebXmlConfiguration(),
				new WebInfConfiguration(),
				new PlusConfiguration(), new MetaInfConfiguration(),
				new FragmentConfiguration(), new EnvConfiguration() });
        */
/*          
        try {
          
            Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
  */
            /*
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration",
                "org.eclipse.jetty.plus.webapp.EnvConfiguration",
                "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore(
                   "org.eclipse.jetty.webapp.WebInfConfiguration",
                    "org.eclipse.jetty.webapp.WebXmlConfiguration",
                    "org.eclipse.jetty.webapp.MetaInfConfiguration",
                    "org.eclipse.jetty.webapp.FragmentConfiguration",
                    "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                    "org.eclipse.jetty.annotations.AnnotationConfiguration"
  
            );
            */            
            
            /*ClassLoader jspClassLoader = new URLClassLoader(new URL[0], webapp.getClass().getClassLoader());*/
            
            
            /*
            org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
            classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", 
                    "org.eclipse.jetty.plus.webapp.EnvConfiguration", 
                    "org.eclipse.jetty.plus.webapp.PlusConfiguration");
            classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", 
                    "org.eclipse.jetty.annotations.AnnotationConfiguration");
            */
            //classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");
            /*webapp.setClassLoader(jspClassLoader);*/
            //context.setClassLoader(new WebAppClassLoader(getClass().getClassLoader(), context));
                    /*
    } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        /*webapp.setServer(server);*/
        
        if (tempDir.length() != 0) {
            webapp.setTempDirectory(new File(tempDir));
        }
        /*
        HandlerList handlers = new HandlerList();
        handlers.addHandler(webapp);
        server.setHandler(handlers);
        */
        server.setHandler(webapp);
        
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
                
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
            
            org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(srv);
            classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
            classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");
            
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
