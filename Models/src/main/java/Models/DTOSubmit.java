package Models;

import java.io.Serializable;

public class DTOSubmit implements Serializable {
    private DTOAngajat who;
    private int capacitate;
    private String numeParticipant;
    private String numeEchipa;
    private int idCursa;
    private int idPart;

    @Override
    public String toString() {
        return "DTOSubmit{" +
                "who=" + who +
                ", capacitate=" + capacitate +
                ", numeParticipant='" + numeParticipant + '\'' +
                ", numeEchipa='" + numeEchipa + '\'' +
                '}';
    }

    public DTOAngajat getWho() {
        return who;
    }

    public void setWho(DTOAngajat who) {
        this.who = who;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }

    public String getNumeParticipant() {
        return numeParticipant;
    }

    public void setNumeParticipant(String numeParticipant) {
        this.numeParticipant = numeParticipant;
    }

    public String getNumeEchipa() {
        return numeEchipa;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    public int getIdCursa() {
        return idCursa;
    }

    public void setIdCursa(int idCursa) {
        this.idCursa = idCursa;
    }

    public DTOSubmit(DTOAngajat a, int cap, String numeP, String numeE, int i, int ii) {
        this.capacitate=cap;
        this.who=a;
        this.numeEchipa=numeE;
        this.numeParticipant=numeP;
        this.idCursa=i;
        this.idPart=ii;
    }

    public String getUserWho(){
        return this.who.getUsername();
    }

    public int getIdPart() {
        return idPart;
    }

    public void setIdPart(int idPart) {
        this.idPart = idPart;
    }
}
