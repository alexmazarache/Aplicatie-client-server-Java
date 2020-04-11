package Models;

import java.io.Serializable;

public class DTOBJCursa extends Entity<Integer> implements Serializable {
    private String nume;
    private int capacitate;
    private int nr_participanti;

    public DTOBJCursa(Integer id, String nume, int cap, int nr){
        super.setId(id);
        this.capacitate=cap;
        this.nr_participanti=nr;
        this.nume=nume;
    }

    @Override
    public String toString() {
        return "DTOBJCursa{" +
                "nume='" + nume + '\'' +
                ", capacitate=" + capacitate +
                ", nr_participanti=" + nr_participanti +
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

    public int getNr_participanti() {
        return nr_participanti;
    }

    public void setNr_participanti(int nr_participanti) {
        this.nr_participanti = nr_participanti;
    }
}
