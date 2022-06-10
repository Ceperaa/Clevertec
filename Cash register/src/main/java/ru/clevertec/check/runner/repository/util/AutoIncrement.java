package ru.clevertec.check.runner.repository.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.Properties;

public class AutoIncrement {

    private final Properties properties = new Properties();
    private final Map<Long, ?> map;
    @Value("${datasource.url}")
    long increment = 0;

    public AutoIncrement(Map<Long, ?> map) {
        this.map = map;
    }

    public long getID() {
        //for (Long e : map.keySet()) {
        if (increment < map.entrySet().size()) {
            return increment = map.size() + 1;
        } else {
            increment++;
            return increment;
        }
        //  }
        // return increment;
    }

    public void setID(long id) {
        if (increment < id) {
            increment = id;
        } else {
            increment++;
        }
    }


}

