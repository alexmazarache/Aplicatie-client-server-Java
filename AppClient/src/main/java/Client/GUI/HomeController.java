package Client.GUI;

import Models.*;

import Repository.Repo.CursaRepository;
import Repository.Repo.EchipaRepository;
import Repository.Repo.InscriereRepository;
import Repository.Repo.ParticipantRepository;
import Service.CursaService;
import Service.EchipaService;
import Service.InscriereService;
import Service.ParticipantService;
import Services.IObserver;
import Services.IServices;
import Services.ServerException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable, IObserver {
    public TableView cursaTable;
    public TableColumn numeCursa;
    public TableColumn cmcCursa;
    public TableColumn nrCursa;
    public TableView participantTable;
    public TableColumn numeParticipant;
    public TableColumn cmcParticipant;
    public TableColumn echipa;
    public ComboBox comboParticipant;
    public TextField numePart;
    public ComboBox comboCapacitate;
    public ComboBox echipaComboinput;
    public ComboBox numeCursaInput;
    public TableView tableInscriere;
    public TableColumn idI;
    public TableColumn cursaI;
    public TableColumn participantI;
    public TableColumn numeI;

    private DTOAngajat crtAngajat;
    private IServices server;
    ObservableList<DTOAngajat> others= FXCollections.observableArrayList();
    ObservableList<DTOBJCursa> curseOBS= FXCollections.observableArrayList();
    ObservableList<DTOBJParticipant> participantsOBS= FXCollections.observableArrayList();



    private CursaService cursaService;
    private ObservableList cursaObs= FXCollections.observableArrayList();

    private ParticipantService participantService;
    private ObservableList participantObs= FXCollections.observableArrayList();

    private EchipaService echipaService;
    private ObservableList echipaObs= FXCollections.observableArrayList();

    private InscriereService inscriereService;
    private ObservableList inscriereObs= FXCollections.observableArrayList();

    public HomeController(){
        System.out.println("HomeController Constructor!!!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("INIT: am in lista angajati "+ others.size());
        System.out.println("END INIT!!!");
        ApplicationContext context = new ClassPathXmlApplicationContext("BeanXML.xml");
        cursaService= new CursaService(context.getBean(CursaRepository.class));
        participantService=new ParticipantService(context.getBean(ParticipantRepository.class));
        echipaService=new EchipaService(context.getBean(EchipaRepository.class));
        inscriereService=new InscriereService(context.getBean(InscriereRepository.class));



    }
    private void setare_combo(){
        List<String> list = new ArrayList<>();
        Iterable<Echipa> it = echipaService.getAll();
        it.forEach(x->list.add(x.getNume()));
        echipaObs.setAll(list);
        comboParticipant.setItems(echipaObs);
        comboParticipant.setValue(list.get(0));

    }
    private void combosetup() {
        List<Integer> list = new ArrayList<>();
        Iterable<DTOBJCursa> it = cursaService.getAll();
        it.forEach(x->list.add(x.getCapacitate()));
        ObservableList o= FXCollections.observableArrayList();
        o.setAll(list);
        comboCapacitate.setItems(o);
        comboCapacitate.setValue(list.get(0));
        comboCapacitate.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue observable, Integer oldValue, Integer newValue) {
                numeCursaInput.setValue(numeCursaInput.getItems().get(comboCapacitate.getItems().indexOf(newValue)));

            }
        });
    }
    private void combo_echipa(){
        //setare combo echipa input
        List<String> list = new ArrayList<>();
        Iterable<Echipa> it = echipaService.getAll();
        it.forEach(x->list.add(x.getNume()));
        ObservableList o= FXCollections.observableArrayList();
        o.setAll(list);
        echipaComboinput.setItems(o);
        echipaComboinput.setValue(list.get(0));
    }
    private void combo_nume(){
        //setare combo nume echipa
        List<String> list = new ArrayList<>();
        Iterable<DTOBJCursa> it = cursaService.getAll();
        it.forEach(x->list.add(x.getNume()));
        ObservableList o= FXCollections.observableArrayList();
        o.setAll(list);
        numeCursaInput.setItems(o);
        numeCursaInput.setValue(list.get(0));
        numeCursaInput.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {

                comboCapacitate.setValue(comboCapacitate.getItems().get(numeCursaInput.getItems().indexOf(newValue)));

            }
        });
    }
    public void setServer(IServices server1){
        this.server=server1;
    }

    public void setUser(DTOAngajat crtAngajat){
        this.crtAngajat=crtAngajat;
    }

    public void login(String user, String pass) throws ServerException{
        DTOAngajat ang = new DTOAngajat(1,user,pass);
        server.login(ang,this);
        crtAngajat = ang;
        System.out.println("Autentificarea e ok");
    }
    @Override
    public void AngajatLoggedIn(DTOAngajat angajat) throws ServerException {
        Platform.runLater(() -> {
            others.add(angajat);
            //OthersEmp.setItems(others);
            System.out.println("Employee logged in"+angajat);
            System.out.println(others.size());
        });

    }
    public void setLoggedEmployess(){
        try {
            Angajat[] lEmp = server.getLoggedEmployees();
            //UsernameCol.setCellValueFactory(new PropertyValueFactory<String,Integer>("username"));
            for ( Angajat u : lEmp) {
                others.add(u.convert());
            }
            //OthersEmp.setItems(others);

        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void AngajatLoggedOut(DTOAngajat employee) throws ServerException {
        Platform.runLater(() -> {
            others.remove(employee);
            //OthersEmp.setItems(others);
            System.out.println("Employee logged out"+employee);
        });
    }

    public void AddNewDataCurse(DTOBJCursa [] source){
        this.curseOBS.clear();
        for(DTOBJCursa c:source){
            this.curseOBS.add(c);
        }
        this.cursaTable.setItems(this.curseOBS);
    }
    @Override
    public void AngajatSubmitted(DTOBJCursa[] curse) throws ServerException {
        Platform.runLater(()->{
            System.out.println("S-a apelat AngajatSubmitted din MainCtrl");
            AddNewDataCurse(curse);
            //setCurseTabel();
        });
    }



    public void logout() {
        try{
            server.logout(crtAngajat,this);


        } catch (ServerException e) {
            System.out.println("Logout error "+ e.getMessage());
        }
    }
    private boolean dialog_show(){
        boolean sterg= true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Sunteti sigur ca doriti sa va deconectati?");


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            sterg=true;
        } else {
            //trebuie sa sterg nota din service
            sterg=false;
        }
        return sterg;
    }
    public void logOutHandler(ActionEvent actionEvent) {
        if(dialog_show())
        {
            logout();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }



    public void cautareHandler(ActionEvent actionEvent) {

        try{
            this.participantTable.getItems().clear();
            String team = comboParticipant.getSelectionModel().getSelectedItem().toString();
            DTOBJParticipant[] result=this.server.searchByTeam(team);
            for(DTOBJParticipant part : result){
                participantsOBS.add(part);
            }
            this.participantTable.setItems( participantsOBS);
        } catch (ServerException e) {
            System.out.println("Error when searching "+e);
        }
    }


    public void inscriereHandler(ActionEvent actionEvent) throws ServerException {
        System.out.println("se apeleaza Inscriere");
        String nume= numePart.getText();
        String echipa=echipaComboinput.getSelectionModel().getSelectedItem().toString();
        Integer cap = Integer.parseInt(comboCapacitate.getSelectionModel().getSelectedItem().toString());
        int id_cursa= cursaService.getIdCursa(numeCursaInput.getSelectionModel().getSelectedItem().toString());

        System.out.println("Se apeleaza Inscriere");
        try{
            DTOSubmit submit = new DTOSubmit(crtAngajat,cap,nume,echipa,id_cursa,participantService.findOne(nume).getId());
            this.server.submitInscriere(submit);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Inscriere participant","Inscrierea participantului "+nume+'\n'+" la cursa "+ numeCursaInput.getSelectionModel().getSelectedItem().toString()+" s-a realizat cu succes!");

        }catch (ServerException e){
            System.out.println("Error when submitting from MainCtrl"+e);
        }

    }

    public void init_table() {
        numeCursa.setCellValueFactory(new PropertyValueFactory<DTOBJCursa,String>("nume"));
        cmcCursa.setCellValueFactory(new PropertyValueFactory<DTOBJCursa,Integer>("capacitate"));
        nrCursa.setCellValueFactory(new PropertyValueFactory<DTOBJCursa,Integer>("nr_participanti"));

        try{
            this.cursaTable.getItems().clear();
            DTOBJCursa[] curse = this.server.getCurseDisp();
            for(DTOBJCursa c:curse){
                curseOBS.add(c);
            }
            cursaTable.setItems(curseOBS);
        } catch (ServerException e) {
            System.out.println("Error when setting CurseTabel"+e);
        }

        numeParticipant.setCellValueFactory(new PropertyValueFactory<Participant,String>("nume"));
        cmcParticipant.setCellValueFactory(new PropertyValueFactory<Participant,Integer>("capacitate"));
        echipa.setCellValueFactory(new PropertyValueFactory<Participant,String>("echipa"));
        participantTable.getSelectionModel().selectedItemProperty().addListener((obs,oldV,newV)->show_nume((DTOBJParticipant) newV));
        try{
            this. participantTable.getItems().clear();
            DTOBJParticipant[] p = this.server.searchByTeam("Mercedes");
            for(DTOBJParticipant ps : p){
                participantsOBS.add(ps);
            }
            participantTable.setItems(participantsOBS);
        } catch (ServerException e) {
            System.out.println("Error when setting ParticipantTabel"+e);
        }

    }
    private void clear_fields(){
        numePart.setText("");

    }
    private void show_nume(DTOBJParticipant newV) {
        if(newV==null)
            clear_fields();
        else{
            numePart.setText(""+newV.getNume());
            echipaComboinput.setValue(newV.getEchipa());
        }
    }

    public void init_combo() {
        setare_combo();

        combosetup();
        combo_echipa();
        combo_nume();

    }
}