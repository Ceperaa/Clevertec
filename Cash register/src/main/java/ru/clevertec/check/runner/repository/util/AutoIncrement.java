package ru.clevertec.check.runner.repository.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class AutoIncrement {

    private final Map<Long, ?> checkMap;
    long increment = 0;

    @Autowired
    public AutoIncrement(Map<Long, ?> checkMap) {
        this.checkMap = checkMap;
    }

    public long getID() {
        for (Long e : checkMap.keySet()) {
            if (increment < e) {
                increment = e;
            } else {
                increment++;
                return increment;
            }
        }
        return increment;
    }

    public void setID(long id) {
        if (increment < id) {
            increment = id;
        } else {
            increment++;
        }
    }
}
