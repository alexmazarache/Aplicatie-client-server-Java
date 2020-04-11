package Models;

public class Inscriere extends Entity<Integer> {
    private Integer cursaID;
    private Integer participantID;
    private String angajat;

    public Inscriere(Integer id,Integer cursaID, Integer participantID,String angajat) {
        super.setId(id);
        this.cursaID = cursaID;
        this.participantID = participantID;
        this.angajat=angajat;
    }

    public String getAngajat() {
        return angajat;
    }

    public void setAngajat(String angajat) {
        this.angajat = angajat;
    }

    public Integer getCursaID() {
        return cursaID;
    }

    public void setCursaID(Integer cursaID) {
        this.cursaID = cursaID;
    }

    public Integer getParticipantID() {
        return participantID;
    }

    public void setParticipantID(Integer participantID) {
        this.participantID = participantID;
    }
}
