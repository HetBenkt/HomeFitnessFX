package nl.bos.dao;

import nl.bos.models.Exercise;

import java.util.List;

public class InMemoryDAO implements GenericDAO {

    @Override
    public long getNextId() {
        return 0;
    }

    @Override
    public String createDatabase() {
        return "";
    }

    @Override
    public List<Exercise> readDatabase() {
        return null;
    }

    @Override
    public void insertDemoData() {

    }

    @Override
    public String checkDriver() {
        return null;
    }
}
