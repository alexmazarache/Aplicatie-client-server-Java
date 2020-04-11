package Models;

public class Angajat extends Entity<Integer> {
    private String nume;
    private String parola;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    @Override
    public String toString() {
        return "Models.Angajat{" + "id= "+super.getId()+
                ", nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Angajat( Integer id,String nume, String parola) {
        super.setId(id);
        this.nume = nume;
        this.parola = parola;
    }
    public Angajat(){

    }

    public DTOAngajat convert(){
        DTOAngajat result=new DTOAngajat(1,this.nume,this.parola);
        return result;
    }
}
