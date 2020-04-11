package Models;

public class Echipa extends Entity<Integer> {

    private String nume;


    public Echipa(Integer id,String nume){
        super.setId(id);
        this.nume=nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Models.Echipa{" +
                "nume='" + nume + '\'' +
                '}';
    }
}
