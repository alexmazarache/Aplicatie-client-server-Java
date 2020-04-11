package Repository.Repo;

import Models.Cursa;
import Models.DTOBJCursa;
import Repository.ICursaRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaRepository implements ICursaRepo<Integer, Cursa> {
    private JDBC utils;
    private static final Logger logger = LogManager.getLogger(CursaRepository.class);

    public CursaRepository( Properties props) {
        logger.info("Initializing CursaRepository with properties: {} ",props);
        utils=new JDBC(props);

    }


    @Override
    public void addParticipant(int n) {
        Connection con=utils.getConnection();
        List<Cursa> curse=new ArrayList<>();
        logger.traceEntry("===Add participant=== CURSA");
        try(PreparedStatement preStmt=con.prepareStatement("update Cursa set nr_participanti=nr_participanti+1 where id=?")) {

            preStmt.setInt(1,n);
            int result=preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);

            System.out.println("Error DB "+e);
        }
        logger.traceExit(n);

    }

    @Override
    public Iterable<DTOBJCursa> findAll() {
        Connection con=utils.getConnection();
        List<DTOBJCursa> curse=new ArrayList<>();
        logger.traceEntry("===FINDALL=== CURSA");
        try(PreparedStatement preStmt=con.prepareStatement("select * from Cursa")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume;
                    nume=result.getString("nume");
                    Integer capacitate,nr_part;
                    capacitate=result.getInt("capacitate");
                    nr_part=result.getInt("nr_participanti");
                    DTOBJCursa c= new DTOBJCursa(id,nume,capacitate,nr_part);
                    curse.add(c);
                }
            }
        } catch (SQLException e) {
            logger.error(e);

            System.out.println("Error DB "+e);
        }
        logger.traceExit(curse);

        return curse;
    }

    @Override
    public int getIdCursa(String nume) {
        logger.traceEntry("===Find id cursa=== with name {} ",nume);
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select id from Cursa where nume=?")){
            preStmt.setString(1,nume);

            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()){
                    Integer id=result.getInt("id");


                    logger.traceExit(id);

                    return id;
                }
            }
        }
        catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB"+ex);
        }
        logger.traceExit("No angajat found with name {}", nume);

        return 0;
    }

}
