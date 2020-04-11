package Repository.Repo;

import Models.Angajat;
import Models.DTOAngajat;
import Repository.IAngajatRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AngajatRepository implements IAngajatRepo<Integer, Angajat>
{
    private JDBC utils;
    private static final Logger logger = LogManager.getLogger(AngajatRepository.class);


    public AngajatRepository(Properties props) {
        logger.info("Initializing AngajatiRepository with properties: {} ",props);
        utils=new JDBC(props);

    }
    public Iterable<DTOAngajat> findOtherEmployees(DTOAngajat angajat){
        Connection con=utils.getConnection();
        List<DTOAngajat> result=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select nume,parola from Angajat where nume!=? and parola!=?")){
            preStmt.setString(1,angajat.getUsername());
            preStmt.setString(2,angajat.getPassword());
            try(ResultSet resultSet=preStmt.executeQuery()){
                while(resultSet.next()){
                    String user=resultSet.getString("nume");
                    String pass=resultSet.getString("parola");
                    DTOAngajat angajat1=new DTOAngajat(1,user,pass);
                    result.add(angajat1);
                }
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit(result);
        return result;
    }

    public Angajat findOneLogin(String nume, String pass) {

        logger.traceEntry("===FindONE=== with name {} ",nume);
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajat where nume=? and parola=?")){
            preStmt.setString(1,nume);
            preStmt.setString(2,pass);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()){
                    Integer id=result.getInt("id");
                    String n,p;
                    n=result.getString("nume");
                    p=result.getString("parola");
                    Angajat a = new Angajat(id,n,p);

                    logger.traceExit(a);

                    return a;
                }
            }
        }
        catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB"+ex);
        }
        logger.traceExit("No angajat found with name {}", nume);

        return null;

    }

    @Override
    public Iterable<Angajat> findAll() {
        Connection con=utils.getConnection();
        List<Angajat> angajati=new ArrayList<>();
        logger.traceEntry("===FINDALL=== Angajat");
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajat")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume,parola;
                    nume=result.getString("nume");
                    parola=result.getString("parola");
                    Angajat a= new Angajat(id,nume,parola);
                    angajati.add(a);
                }
            }
        } catch (SQLException e) {
            logger.error(e);

            System.out.println("Error DB "+e);
        }
        logger.traceExit(angajati);

        return angajati;

    }

    @Override
    public Angajat save(Angajat entity) {
        logger.traceEntry("===Save=== angajat {} ",entity);

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Angajat(nume,parola) values (?,?)")){

            preStmt.setString(1,entity.getNume());
            preStmt.setString(2,entity.getParola());

            int result=preStmt.executeUpdate();
            return entity;

        }catch (SQLException ex){
            logger.error(ex);

            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

        return null;
    }

    @Override
    public Angajat delete(Integer integer) {

        logger.traceEntry("===DELETE=== angajat with id {}",integer);
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Angajat where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();

        }
        catch (SQLException ex){
            logger.error(ex);

            System.out.println("Error DB"+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Angajat update(Angajat entity) {
        logger.traceEntry("===UPDATE=== {}",entity);
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Angajat set nume=?,parola=? where id=?")){
            preStmt.setString(1,entity.getNume());
            preStmt.setString(2,entity.getParola());
            preStmt.setInt(3,entity.getId());
            int result=preStmt.executeUpdate();
            return entity;
        }
        catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB"+ex);
        }
        logger.traceExit();
        return null;
    }
}
