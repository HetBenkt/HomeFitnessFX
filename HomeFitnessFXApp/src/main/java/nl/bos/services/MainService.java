package nl.bos.services;

import nl.bos.dao.SQLiteDAO;
import nl.bos.models.Exercise;

import java.util.List;

public class MainService {
    private SQLiteDAO sqLiteDAO;

    public MainService() {
        this.sqLiteDAO = SQLiteDAO.getInstance();
    }

    public String createDatabase() {
        return sqLiteDAO.createDatabase();
    }

    public void insertDemoData() {
        sqLiteDAO.insertDemoData();
    }

    public List<Exercise> readDatabase() {
        return sqLiteDAO.readDatabase();
    }

    public String checkDriver() {
        return sqLiteDAO.checkDriver();
    }


}
