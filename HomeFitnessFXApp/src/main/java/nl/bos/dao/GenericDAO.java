package nl.bos.dao;

import nl.bos.models.Exercise;

import java.util.List;

public interface GenericDAO {
    long getNextId();

    String createDatabase();

    List<Exercise> readDatabase();

    void insertDemoData();

    String checkDriver();
}

