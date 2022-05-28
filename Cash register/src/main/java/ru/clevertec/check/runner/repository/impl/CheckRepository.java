package ru.clevertec.check.runner.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.repository.util.AutoIncrement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CheckRepository {

    private final Map<Long, Check> checkMap;
    private final AutoIncrement autoIncrement;

    @Autowired
    public CheckRepository(Map<Long, Check> checkMap, AutoIncrement autoIncrement) {
        this.checkMap = checkMap;
        this.autoIncrement = autoIncrement;
    }

    public void update(Check check){

        Check check1 = findById(check.getId());
        if (check1 != null) {
            delete(check1.getId());
            checkMap.put(check.getId(),check);
        } else {
            autoIncrement.setID(check.getId());
            checkMap.put(check.getId(),check);
        }

        checkMap.replace(check.getId(),check);
    }

    public Check add(Check check){
    if (checkMap.isEmpty()) {
        check.setId(0);
    } else {
        check.setId(autoIncrement.getID());
    }
    checkMap.put(check.getId(),check);
    return check;
}

    public Check findById(long id){
       return checkMap.get(id);
    }

    public void delete(long id){
        checkMap.remove(id);
    }

    public List<Check> findAll(){
       return new ArrayList<>(checkMap.values());
    }
}
