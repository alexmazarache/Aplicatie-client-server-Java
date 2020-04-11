package Models;

public class Participant extends Entity<Integer> {
    private String nume;
    private int capacitate;
    private String echipa;

    public Participant(Integer id,String nume, int capacitate, String echipa) {
        super.setId(id);
        this.nume = nume;
        this.capacitate = capacitate;
        this.echipa = echipa;
    }

    @Override
    public String toString() {
        return "Models.Participant{" + "id="+super.getId()+
                ", nume='" + nume + '\'' +
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
}
