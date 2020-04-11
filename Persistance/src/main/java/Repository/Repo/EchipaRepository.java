package Repository.Repo;

import Models.Echipa;
import Repository.IEchipaRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class EchipaRepository implements IEchipaRepo<Integer, Echipa> {

    private JDBC utils;
    private static final Logger logger= LogManager.getLogger(EchipaRepository.class);

    public EchipaRepository(Properties props){
        logger.info("Initializam EchipaRepository with properties: {}",props);
        utils=new JDBC(props);

    }

    @Override
    public Iterable<Echipa> findAll() {
        Connection con=utils.getConnection();
        List<Echipa> echipe=new ArrayList<>();
        logger.traceEntry("===FINDALL=== Echipa");
        try(PreparedStatement preStmt=con.prepareStatement("select * from Echipa")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume;
                    nume=result.getString("nume");
                    Echipa e= new Echipa(id,nume);
                    echipe.add(e);
                }
            }
        } catch (SQLException e) {
            logger.error(e);

            System.out.println("Error DB "+e);
        }
        logger.traceExit(echipe);

        return echipe;
    }


}
