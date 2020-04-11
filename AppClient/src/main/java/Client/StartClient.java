package Client;

import Client.GUI.HomeController;
import Client.GUI.LoginController;
import Network.RPCProtocol.ClientServicesProxy;
import Services.IServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.graalvm.compiler.phases.graph.ScheduledNodeIterator;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {

    private Stage primaryStage;
    private static int defaultPort=55555;
    private static String defaultServer = "localhost";

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/Client.properties"));
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find Client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host",defaultServer);
        int serverPort = defaultPort;
        try{
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));

        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices server = new ClientServicesProxy(serverIP,serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login.fxml"));
        Parent root=loader.load();

        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.SetServer(server);

        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("curse.fxml"));
        Parent croot=cloader.load();

        HomeController mainCtrl = cloader.<HomeController>getController();
        mainCtrl.setServer(server);
        ctrl.SetMainCtrl(mainCtrl);
        ctrl.SetParent(croot);


        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

}
