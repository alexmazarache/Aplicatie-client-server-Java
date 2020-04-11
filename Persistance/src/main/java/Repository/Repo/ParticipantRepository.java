package Repository.Repo;

import Models.DTOBJParticipant;
import Models.Participant;
import Repository.IParticipantRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantRepository implements IParticipantRepo<Integer, Participant> {

    private JDBC utils;
    private static final Logger logger = LogManager.getLogger(ParticipantRepository.class);
    public ParticipantRepository(Properties props) {

        logger.info("Initializing ParticipantRepository with properties: {} ",props);
        utils=new JDBC(props);

    }

    @Override
    public Iterable<DTOBJParticipant> findAll() {
        Connection con=utils.getConnection();
        List<DTOBJParticipant> angajati=new ArrayList<>();
        logger.traceEntry("===FINDALL=== Participant");
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume,echipa;
                    Integer cap;
                    nume=result.getString("nume");
                    cap=result.getInt("capacitate");
                    echipa=result.getString("echipa");
                    DTOBJParticipant a= new DTOBJParticipant(id,nume,cap,echipa);
                    angajati.add(a);
                }
            }
        } catch (SQLException e) {
            logger.error(e);

            System.out.println("Error DB "+e);
        }
        logger.traceExit(angajati);

        return angajati;
    } public Iterable<DTOBJParticipant> findAllEchipa(String echipa) {
        Connection con=utils.getConnection();
        List< DTOBJParticipant> angajati=new ArrayList<>();
        logger.traceEntry("===FINDALL=== Participant");
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant WHERE echipa=?")) {
            preStmt.setString(1,echipa);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume,ech;
                    Integer cap;
                    nume=result.getString("nume");
                    cap=result.getInt("capacitate");
                    ech=result.getString("echipa");
                    DTOBJParticipant a= new  DTOBJParticipant(id,nume,cap,echipa);
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
    public Participant save(Participant entity) {
        logger.traceEntry("===Save=== participant {} ",entity);

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Participant(nume,capacitate,echipa) values (?,?,?)")){

            preStmt.setString(1,entity.getNume());
            preStmt.setInt(2,entity.getCapacitate());
            preStmt.setString(3,entity.getEchipa());
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
    public Participant findOne(String nume) {
        logger.traceEntry("===FindONE Participant=== with name {} ",nume);
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant where nume=?")){
            preStmt.setString(1,nume);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()){
                    Integer id=result.getInt("id");
                    String n,p;
                    n=result.getString("nume");
                    Integer cap = result.getInt("capacitate");
                    p=result.getString("echipa");
                    Participant a =  new Participant(id,n,cap,p);

                    logger.traceExit(a);

                    return a;
                }
            }
        }
        catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB"+ex);
        }
        logger.traceExit("No participant found with name {}", nume);

        return null;
    }


}
