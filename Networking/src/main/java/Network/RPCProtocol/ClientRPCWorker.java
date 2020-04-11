package Network.RPCProtocol;

import Models.*;
import Services.IObserver;
import Services.IServices;
import Services.ServerException;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientRPCWorker implements Runnable, IObserver {

    private IServices server;
    private Socket connection;

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private volatile boolean connected;

    private static Response OK = new Response.Builder().type(ResponseType.OK).build();


    public ClientRPCWorker(IServices server, Socket connection){
        this.server = server;
        this.connection=connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected=true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void SendResponse(Response response) throws IOException{
        System.out.println("Sending response "+ response);
        output.writeObject(response);
        output.flush();
    }


    @Override
    public void AngajatLoggedIn(DTOAngajat angajat) throws ServerException {
        System.out.println("Angajat logged in " + angajat);
        Response response=new Response.Builder().type(ResponseType.EMPLOYEE_LOGGED_IN).data(angajat).build();
        try {
            SendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void AngajatLoggedOut(DTOAngajat angajat) throws ServerException {

        System.out.println("Angajat logged out "+ angajat);
        Response response=new Response.Builder().type(ResponseType.EMPLOYEE_LOGGED_OUT).data(angajat).build();
        try{
            SendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AngajatSubmitted(DTOBJCursa[] curse) throws ServerException {

        System.out.println("New submit made in ClientRPCWorker ");
        Response response=new Response.Builder().type(ResponseType.NEW_SUBMIT).data(curse).build();
        try{
            SendResponse(response);

        } catch (IOException e) {
            throw new ServerException("error"+e);
        }
    }
    private Response handleRequest(Request request){
        Response response = null;
        String handlerName="handle"+(request).getType();
        System.out.println("HandlerName "+handlerName);
        try{
            Method method = this.getClass().getDeclaredMethod(handlerName,Request.class);
            response = (Response) method.invoke(this,request);
            System.out.println("Method " + handlerName +" invoked");


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }
    private Response handleLOGIN(Request request){
        DTOAngajat angajat = (DTOAngajat)request.getData();
        System.out.println("Login request by "+angajat);

        try{
            server.login(angajat,this);
            return OK;
        } catch (ServerException e) {
            connected = false;

            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleLOGOUT(Request request){
        DTOAngajat angajat = (DTOAngajat) request.getData();
        System.out.println("Logout request by " +angajat);
        try{
            server.logout(angajat,this);
            connected=false;
            return OK;
        } catch (ServerException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_LOGGED_EMPLOYEES(Request request){
        System.out.println("Get logged employees request");
        try{
            Angajat[] angajats = server.getLoggedEmployees();
            return new Response.Builder().type(ResponseType.GET_LOGGED_EMPLOYEE).data(angajats).build();
        } catch (ServerException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_CURRENT_CURSE(Request request){
        System.out.println("Get curent curse request");
        try{
            DTOBJCursa[] cursas = server.getCurseDisp();
            return new Response.Builder().type(ResponseType.GET_DISP_CURSE).data(cursas).build();
        } catch (ServerException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleSEARCH_BY_TEAM(Request request){
        System.out.println("Search by team requested");
        try{
            String team = (String) request.getData();
            DTOBJParticipant [] participants = server.searchByTeam(team);
            return new Response.Builder().type(ResponseType.GET_SEARCH_RESULT).data(participants).build();
        } catch (ServerException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleSUBMIT_INSC(Request request) {
        System.out.println("Submit requested");

        try {
            DTOSubmit infoSubmit = (DTOSubmit) request.getData();
            server.submitInscriere(infoSubmit);
            return OK;

        } catch (ServerException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }



    @Override
    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    SendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
        }
            try{
                input.close();
                output.close();
                connection.close();
            } catch (IOException e4) {
                System.out.println("ERROR "+e4.getMessage());
            }
        }

}

