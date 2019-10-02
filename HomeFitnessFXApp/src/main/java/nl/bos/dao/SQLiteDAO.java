package nl.bos.dao;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import nl.bos.models.Exercise;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteDAO implements GenericDAO {
    private static final Logger LOGGER = Logger.getLogger(SQLiteDAO.class.getName());
    private final static String DB_NAME = "fitness.db";
    private static SQLiteDAO instance;
    private Connection connection = null;
    private Statement stmt;
    private ResultSet rs;

    public static SQLiteDAO getInstance() {
        if (instance == null)
            instance = new SQLiteDAO();
        return instance;
    }

    @Override
    public long getNextId() {
        return -1;
    }

    @Override
    public String createDatabase() {
        File dir;
        String dbUrl = "jdbc:sqlite:";
        try {
            dir = Services.get(StorageService.class)
                    .map(s -> s.getPrivateStorage().get())
                    .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
            File db = new File(dir, DB_NAME);
            dbUrl = dbUrl + db.getAbsolutePath();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return ex.getMessage();
        }

        try {
            connection = DriverManager.getConnection(dbUrl);
            LOGGER.info("Connection established: " + dbUrl);
            return dbUrl;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return ex.getMessage();
        }
    }

    @Override
    public List<Exercise> readDatabase() {
        List<Exercise> exercises = new ArrayList<>();
        try {
            if (connection != null) {
                stmt = connection.createStatement();
                stmt.setQueryTimeout(30);

                LOGGER.info("Retrieving records from table 'exercise'...");
                rs = stmt.executeQuery("select * from exercise");
                while (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    Exercise exercise = new Exercise();
                    exercise.setName(name);
                    exercise.setDescription(description);
                    exercises.add(exercise);
                }
                LOGGER.info("End retrieving records");
                return exercises;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return exercises;
    }

    @Override
    public void insertDemoData() {
        try {
            if (connection != null) {
                stmt = connection.createStatement();
                stmt.setQueryTimeout(30);

                LOGGER.info("Creating table 'exercise'...");
                stmt.executeUpdate("drop table if exists exercise");
                stmt.executeUpdate("create table exercise (id integer, name string, description string)");
                stmt.executeUpdate("insert into exercise values(1, 'Plank', 'Lie straight with both forearms \nsupporting body; hold position')");
                stmt.executeUpdate("insert into exercise values(2, 'Push-ups', 'Lie prone with both arms stretched \nsupporting body; lower body with arms')");
                stmt.executeUpdate("insert into exercise values(3, 'Superman', 'Lie on stomach with arms extended straigth; \nRaise both arms and legs up off the floor; \nhold for 2 sec. return and repeat')");
                LOGGER.info("End creating table");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    @Override
    public String checkDriver() {
        try {
            Class c = null;
            if (Platform.isAndroid()) {
                c = Class.forName("org.sqldroid.SQLDroidDriver");
            } else if (Platform.isDesktop()) {
                c = Class.forName("org.sqlite.JDBC");
            }
            if (c != null)
                return c.getName();
            return "No class found!";
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return e.getMessage();
        }
    }
}
