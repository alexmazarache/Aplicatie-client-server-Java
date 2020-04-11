package Models;

import java.io.Serializable;

public class DTOAngajat extends Entity<Integer> implements Serializable, Comparable<Angajat> {
    private String username;
    private String password;
    public DTOAngajat(Integer id,String user, String pass){
        super.setId(id);
        this.username=user;
        this.password=pass;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "DTOAngajat{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(Angajat o) {
        return this.username.compareTo(o.getNume());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
