package Models;

import java.io.Serializable;

public class DTOBJParticipant extends Entity<Integer> implements Serializable {
    private String nume;
    private int capacitate;
    private String echipa;

    @Override
    public String toString() {
        return "DTOBJParticipant{" +
                "nume='" + nume + '\'' +
                ", capacitate=" + capacitate +
                ", echipa='" + echipa + '\'' +
                '}';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }

    public String getEchipa() {
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    public DTOBJParticipant(Integer id, String nume, int capacitate, String echipa) {
        super.setId(id);
        this.nume = nume;
        this.capacitate = capacitate;
        this.echipa = echipa;
    }

}
