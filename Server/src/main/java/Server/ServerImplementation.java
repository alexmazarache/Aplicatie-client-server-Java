package Server;

import Models.*;
import Service.*;
import Services.IObserver;
import Services.IServices;
import Services.ServerException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImplementation implements IServices {

    private AngajatService angajatService;
    private CursaService cursaService;
    private EchipaService echipaService;
    private InscriereService inscriereService;
    private ParticipantService participantService;

    private Iterable<Angajat> angajati;
    private Iterable<DTOBJCursa> cursedisp;
    private Map<String,IObserver> loggedEmployees;

    private final int noOfThreads = 5;
    public ServerImplementation(AngajatService a, CursaService c, EchipaService e, InscriereService i, ParticipantService p){
        this.angajatService=a;
        this.cursaService = c;
        this.echipaService = e;
        this.inscriereService = i;
        this.participantService = p;
        angajati = angajatService.getAll();
        cursedisp = cursaService.getAll();
        loggedEmployees = new ConcurrentHashMap<>();


    }

    @Override
    public synchronized void login(DTOAngajat angajat, IObserver client) throws ServerException {
        Angajat isEmployee = this.angajatService.findOne(angajat.getUsername(),angajat.getPassword());

        if(isEmployee!=null){
            if(loggedEmployees.get(angajat.getUsername())!=null){
                throw new ServerException("User already logged in!");
            }
            loggedEmployees.put(angajat.getUsername(),client);
            notifyEmployeeLoggedIn(angajat);



        }
        else {
            System.out.println("Authetification failed!!!");
            throw new ServerException("Wrong username or password");
        }

    }

    private void notifyEmployeeLoggedIn(DTOAngajat angajat)throws ServerException {
        Iterable<DTOAngajat> employees = this.angajatService.findOthers(angajat);

        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
        for(DTOAngajat ang : employees){
            IObserver client = loggedEmployees.get(ang.getUsername());
            if(client!=null){
                executor.execute(()->{
                    try{
                        System.out.println("Notifying employee "+ ang.getUsername()+" employee "+angajat.getUsername()+" logged in");
                        client.AngajatLoggedIn(angajat);
                    } catch (ServerException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executor.shutdown();

    }

    @Override
    public synchronized void logout(DTOAngajat angajat, IObserver client) throws ServerException {

            IObserver localClient = loggedEmployees.remove(angajat.getUsername());
            if(localClient==null){
                throw new ServerException("User "+angajat.getUsername()+" is not logged in");
            }
        notifyEmployeeLoggedOut(angajat);

    }

    private void notifyEmployeeLoggedOut(DTOAngajat angajat)throws ServerException {

        Iterable<DTOAngajat> employees = this.angajatService.findOthers(angajat);

        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
        for(DTOAngajat ang:employees){
            IObserver client = loggedEmployees.get(ang.getUsername());
            if(client!=null){
                executor.execute(()->
                {
                    try{
                        System.out.println("Notifying employee "+ang.getUsername()+" employee "+angajat.getUsername()+" logged out");
                        client.AngajatLoggedOut(angajat);
                    } catch (ServerException e) {
                        e.printStackTrace();
                    }
                });

            }
        }
        executor.shutdown();
    }

    @Override
    public synchronized void submitInscriere(DTOSubmit infoSubmit) throws ServerException {
        System.out.println("Submitting by " + infoSubmit.getUserWho()+"..........");
        try{
            //inscrierea
            this.cursaService.addParticipanti(infoSubmit.getIdCursa());
            this.inscriereService.addInscriere(1,infoSubmit.getIdCursa(),infoSubmit.getIdPart(),infoSubmit.getWho().getUsername());
            //end- inscriere
            System.out.println("New submit saved in database");
            notifyEmployeeSubmitted(infoSubmit.getWho());
        }catch(ServerException e){
            throw new ServerException("Could not submit ..."+e);
        }
    }
   private DTOBJCursa[] convert(Iterable<DTOBJCursa> source){
        ArrayList<DTOBJCursa> result=new ArrayList<>();
        for (DTOBJCursa c : source){
            result.add(c);
        }
        return result.toArray(new DTOBJCursa[result.size()]);
    }

    private void notifyEmployeeSubmitted(DTOAngajat who)throws ServerException {
        System.out.println("S-a apealat notificatia pt submit");
        Iterable<Angajat> employees = this.angajatService.getAll();
        this.cursedisp=this.cursaService.getAll();
        DTOBJCursa[] result = convert(this.cursedisp);
        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
        for(Angajat ang: employees){
            IObserver client = loggedEmployees.get(ang.getNume());
            System.out.println(client);
            if(client!=null){
                executor.execute(()->{
                    try{
                        System.out.println("Notifiying employee "+ang.getNume()+" employee "+who.getUsername()+" submitted");
                        client.AngajatSubmitted(result);
                        System.out.println("Employee "+ang.getNume()+" notified");
                    }catch (ServerException e){
                        System.err.println("Error notifying about submit");
                    }
                });
            }
            else {
                System.out.println("Error gettting logged in employees");
            }
        }
        executor.shutdown();

    }

    @Override
    public synchronized Angajat[] getLoggedEmployees() throws ServerException {
        Iterable<Angajat> allEmployees = this.angajatService.getAll();
        Set<Angajat> result = new TreeSet<>();
        for(Angajat ang:allEmployees){
            if(loggedEmployees.containsKey(ang.getNume())){
                result.add(new Angajat(0,ang.getNume(),""));
                System.out.println("+" +ang.getNume());
            }
        }
        System.out.println("Size is "+result.size());
        return  result.toArray(new Angajat[result.size()]);
    }

    @Override
    public synchronized DTOBJCursa[] getCurseDisp() throws ServerException {
        ArrayList<DTOBJCursa> result = new ArrayList<>();
        for(DTOBJCursa c:this.cursedisp){
            result.add(c);
        }
        return result.toArray(new DTOBJCursa[result.size()]);
    }

    @Override
    public synchronized DTOBJParticipant[] searchByTeam(String team) throws ServerException {
        Iterable<DTOBJParticipant> result = this.participantService.getAllEchipa(team);
        ArrayList< DTOBJParticipant> ret = new ArrayList<>();
        for( DTOBJParticipant part: result){
            ret.add(part);
        }
        return ret.toArray(new  DTOBJParticipant[ret.size()]);
    }


}
