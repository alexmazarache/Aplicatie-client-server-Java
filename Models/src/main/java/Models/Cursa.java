package Models;

public class Cursa extends Entity<Integer> {
    private String nume;
    private int capacitate;
    private int nr_participanti;

    public Cursa(Integer id,String nume, int capacitate, int nr_participanti) {
        super.setId(id);
        this.nume = nume;
        this.capacitate = capacitate;
        this.nr_participanti = nr_participanti;
    }

    @Override
    public String toString() {
        return "Models.Cursa{" + "id= "+super.getId()+
                ", nume='" + nume + '\'' +
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
