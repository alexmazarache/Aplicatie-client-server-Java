package Repository.Repo;

import Models.Inscriere;
import Repository.IInscriereRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InscriereRepository implements IInscriereRepo<Integer, Inscriere> {
    private JDBC utils;
    private static final Logger logger = LogManager.getLogger(AngajatRepository.class);

    public InscriereRepository(Properties props){
        logger.info("Initializing AngajatiRepository with properties: {} ",props);
        utils=new JDBC(props);
    }

    @Override
    public Inscriere save(Inscriere entity) {
        logger.traceEntry("===Save=== inscriere {} ",entity);

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Inscriere(cursaID,participantID,angajat) values (?,?,?)")){

            preStmt.setInt(1,entity.getCursaID());
            preStmt.setInt(2,entity.getParticipantID());
            preStmt.setString(3,entity.getAngajat());

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
    public Iterable<Inscriere> findAll() {
        Connection con=utils.getConnection();
        List<Inscriere> inscrieri=new ArrayList<>();
        logger.traceEntry("===FINDALL=== Inscriere");
        try(PreparedStatement preStmt=con.prepareStatement("select * from Inscriere")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int cursaid,partid;
                   cursaid=result.getInt("cursaID");
                    partid=result.getInt("participantID");
                    String angajat= result.getString("angajat");
                    Inscriere a = new Inscriere(id,cursaid,partid,angajat);
                    inscrieri.add(a);
                }
            }
        } catch (SQLException e) {
            logger.error(e);

            System.out.println("Error DB "+e);
        }
        logger.traceExit(inscrieri);

        return inscrieri;
    }
}
