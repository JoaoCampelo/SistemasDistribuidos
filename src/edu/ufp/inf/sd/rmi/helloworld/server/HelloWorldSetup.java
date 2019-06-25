package edu.ufp.inf.sd.rmi.helloworld.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.activation.*;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2009</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 1.0
 *
 * This program does not create an instance of a remote object, instead it
 * registers a remote stub with rmiregistry so that clients can look it up.
 *
 * The program registers an activation descriptor, for a remote object, with the
 * Java RMI activation system daemon (rmid) and then binds a stub for that
 * remote object in a rmiregistry.
 *
 * Setup program uses a number of system properties to customize the information
 * that is registered with rmid and rmiregistry.
 * 
 * A stub for an activatable remote object contains the remote object's activation ID and
 * information on how to contact the Java RMI activation system daemon (rmid) for the remote object.
 * If the stub cannot connect to the last-known address (i.e., host/port) for the remote object,
 * then it will contact the remote object's activator (rmid) to activate the object.
 * When rmid receives an activation request, it starts the remote object's activation group (or container)
 * VM (only if the group is not already executing), and 
 * then rmid asks the group to make an instance of the remote object.
 * Once the group constructs the remote object, it returns the remote stub to rmid which, in turn,
 * returns it to the initiating stub.
 *
 */
public class HelloWorldSetup {

    //String serviceName = "rmi://localhost:1099/HelloWorldService";
    //public static String serviceName = "rmi://localhost/HelloWorldService";

    public static void main(String[] args) {
        System.out.println("HelloWorldSetup - main(): going to check args...");
        try {
            String serverActivatableClass = "edu.ufp.sd.helloworld.server.HelloWorldActivatable";
            String registryHost = "localhost";
            int registryPort = 1099;
            String serviceName = "rmi://localhost:1099/HelloWorldService";

            if (args.length < 3) {
                System.err.println("usage: java [options] edu.ufp.sd.helloworld.server.HelloWorldSetup <server_activatable_class>");
                System.exit(1);
            } else {
                registryHost = args[0];
                registryPort = Integer.parseInt(args[1]);
                serverActivatableClass = args[2];
                serviceName = "rmi://" + registryHost + ":1099/HelloWorldService";
            }
            
            System.out.println("HelloWorldSetup - main(): registryHost:port = " + registryHost + ":" + registryPort);
            System.out.println("HelloWorldSetup - main(): serverActivatableClass = " + serverActivatableClass);
            System.out.println("HelloWorldSetup - main(): serviceName = " + serviceName);
        
            System.out.println("HelloWorldSetup - main(): going to set security manager...");
            // Create and install a security manager
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            System.out.println("HelloWorldSetup - main(): going to set properties...");
            // The Java 2 security model requires a security policy specified for the ActivationGroup JVM.
            // Properties (inherited from Hashtable) have put method with key-value.
            Properties props = new Properties();
            //Required system props passed to HelloWorldSetup class:
            //  java.security.policy - the actGroupDesc's security policy file
            //  java.class.path - a dummy class path to prevent an activation actGroupDesc from loading implementation classes from the local class path
            //  edu.ufp.sd.helloworld.activation.impl.codebase - URL of implementation classes, which the actGroupDesc's policy file uses to grant permissions to
            //  edu.ufp.sd.helloworld.activation.file - a file containg the activatable object's persistent state

            //Register information about a particular activatable remote object:
            //* First, creates activation actGroupDesc which is a container virtual machine (VM)
            //  for managing the execution of a set of activatable objects.
            //  An activation actGroupDesc contains information that the activation system (rmid)
            //  needs to start an activation actGroupDesc's VM.
            //* Second, registrater an activation actGroupDesc descriptor for obtaining
            //  an activation actGroupDesc ID to use for the activatable object.
            String groupPolicy = System.getProperty("edu.ufp.sd.helloworld.activation.policy", "group.policy");
            String activImplCodebase = System.getProperty("edu.ufp.sd.helloworld.activation.impl.codebase");
            String servantPersistentStateFilename = System.getProperty("edu.ufp.sd.helloworld.activation.file", "HelloWorldPersistent.State");
            String servName = System.getProperty("edu.ufp.sd.helloworld.activation.servicename", "HelloWorldService");

            System.out.println("HelloWorldSetup - main(): groupPolicy = " + groupPolicy);
            System.out.println("HelloWorldSetup - main(): activImplCodebase = " + activImplCodebase);
            System.out.println("HelloWorldSetup - main(): servantPersistentStateFilename = " + servantPersistentStateFilename);
            System.out.println("HelloWorldSetup - main(): servName = " + servName);
            System.out.println("HelloWorldSetup - main(): serviceName = " + serviceName);
            
            //Properties props = new Properties();
            props.put("java.security.policy", groupPolicy);
            props.put("java.class.path", "no_classpath");
            props.put("edu.ufp.sd.helloworld.activation.impl.codebase", activImplCodebase);
            if (servantPersistentStateFilename != null && !servantPersistentStateFilename.equals("")) {
                props.put("edu.ufp.sd.helloworld.activation.file", servantPersistentStateFilename);
            }

            ActivationGroupDesc.CommandEnvironment ace = null;
            ActivationGroupDesc actGroupDesc = new ActivationGroupDesc(props, ace);

            System.out.println("HelloWorldSetup - main(): group properties = " + actGroupDesc.getPropertyOverrides().toString());

            // Create ActivationGroupDesc and register it with the activation system to obtain its ID;
            // getSystem() obtains the stub for the activation system;
            // registerGroup() is called on the remote activation system. 
            ActivationGroupID actGroupID = ActivationGroup.getSystem().registerGroup(actGroupDesc);

            System.out.println("HelloWorldSetup - main(): actGroupID = " + actGroupID.getSystem().toString());

            // Create parameters passed to ActivationDesc constructor
            MarshalledObject marshalledObg = null;
            if (servantPersistentStateFilename != null && !servantPersistentStateFilename.equals("")) {
                marshalledObg = new MarshalledObject(servantPersistentStateFilename);
            }
            System.out.println("HelloWorldSetup - main(): marshalledObg.get() = " + marshalledObg.get());
            
            // The class path is relative to the URL classLocation and
            // should include the package name - edu.ufp.sd.helloworld.server.HelloWorldActivatable
            ActivationDesc desc = new ActivationDesc(actGroupID, serverActivatableClass, activImplCodebase, marshalledObg);

            // Declare/create an instance of your remote interface and
            // register the activation descriptor with rmid deamon
            HelloWorldRI hwRI = (HelloWorldRI) Activatable.register(desc);
            System.out.println("HelloWorldSetup - main(): got the stub for registered activatable");

            // Bind service in Registry
            System.out.println("HelloWorldSetup - main(): going to register service " + serviceName);
            LocateRegistry.getRegistry(registryHost, registryPort).rebind(serviceName, hwRI);
            //Naming.rebind(serviceName, hwRI);
            System.out.println("HelloWorldSetup - main(): after activatable rebind");

            //Quit the setup application
            System.exit(0);

        } catch (ActivationException e) {
            Logger.getLogger(HelloWorldSetup.class.getName()).log(Level.SEVERE, null, e);
        } catch (RemoteException e) {
            Logger.getLogger(HelloWorldSetup.class.getName()).log(Level.SEVERE, null, e);
        } catch (MalformedURLException e) {
            Logger.getLogger(HelloWorldSetup.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(HelloWorldSetup.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HelloWorldSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
