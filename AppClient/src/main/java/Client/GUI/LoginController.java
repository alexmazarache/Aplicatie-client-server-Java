package Client.GUI;

import Models.Angajat;
import Models.DTOAngajat;
import Repository.Repo.AngajatRepository;
import Service.AngajatService;
import Services.IServices;
import Services.ServerException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginController{
    public TextField usernameText;
    public PasswordField passwordText;
    private IServices server;
    private HomeController mainCtrl;
    private DTOAngajat crtAngajat;
    private AngajatService angajatService;
    Parent mainParent;
    public void SetServer(IServices server1){
        this.server=server1;
    }

    public void SetParent(Parent parent1){
        mainParent=parent1;
    }

    public void SetMainCtrl(HomeController ctrl){
        this.mainCtrl=ctrl;
    }

    public void pressCancel(ActionEvent event){
        System.exit(0);
    }

    public void setCrtAngajat(DTOAngajat crtAngajat) {
        this.crtAngajat = crtAngajat;
    }

    @FXML
    public void initialize(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("C:\\Users\\alex_\\IdeaProjects\\lab5_mpp\\AppClient\\src\\main\\resources\\bd.config"));
            angajatService=new AngajatService(new AngajatRepository(props));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void loginHandler(ActionEvent actionEvent) throws ServerException {
        String username = usernameText.getText();
        String password = passwordText.getText();
        crtAngajat = new DTOAngajat(1,username,password);

        Angajat angajat = angajatService.findOne(username,password);
        try{
            server.login(crtAngajat,mainCtrl);
            Stage stage = new Stage();
            stage.setTitle("Logged as "+crtAngajat.getUsername());
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                     mainCtrl.logout();
                    System.exit(0);
                }
            });
            stage.show();
            mainCtrl.setUser(crtAngajat);
            mainCtrl.init_table();
            mainCtrl.init_combo();

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (ServerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }


    }


}