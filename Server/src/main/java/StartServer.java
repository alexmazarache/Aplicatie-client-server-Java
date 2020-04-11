import Network.Utils.AbstractServer;
import Network.Utils.RPCConcurentServer;
import Repository.Repo.*;
import Server.ServerImplementation;
import Service.*;
import Services.IServices;
import Services.ServerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultport=55555;



    public static void main(String [] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("BeanXML.xml");
        AngajatService angajatService= new AngajatService(context.getBean(AngajatRepository.class));
        CursaService cursaService = new CursaService(context.getBean(CursaRepository.class));
        EchipaService echipaService = new EchipaService(context.getBean(EchipaRepository.class));
        InscriereService inscriereService= new InscriereService(context.getBean(InscriereRepository.class));
        ParticipantService participantService= new ParticipantService(context.getBean(ParticipantRepository.class));







        Properties serverProps = new Properties();
        try{
            serverProps.load(StartServer.class.getResourceAsStream("Server.properties"));
            System.out.println("Server properties set");
            serverProps.list(System.out);

        } catch (IOException e) {
            System.out.println("Could not find Server "+e.getMessage());
            return;
        }
        IServices ServerImplementation = new ServerImplementation(angajatService,cursaService,echipaService,inscriereService,participantService);
        int ServerPort=defaultport;
        try {
            ServerPort=Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException e){
            System.err.println("Wrong  Port Number"+e.getMessage());
            System.err.println("Using default port "+defaultport);
        }
        System.out.println("Starting the server on port "+ ServerPort);
        AbstractServer server= new RPCConcurentServer(ServerPort,ServerImplementation);
        try{
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
        finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }

        }
    }
}
