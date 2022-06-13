package ru.clevertec.check.runner.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class CheckRepositoryImpl extends RepositoryEntityImpl<Check> {

    private final Map<Long,Check> map;
    private final IStreamIO checkIO;
    @Value("${checkId}")
    private long increment;

    public CheckRepositoryImpl(Map<Long, Check> map, IStreamIO checkIO) {
        super(map, checkIO);
        this.map = map;
        this.checkIO = checkIO;
    }

    @Override
    protected void setProperty(Properties properties) {
        properties.setProperty("checkId", String.valueOf(increment));
    }

    @Override
    public List<Check> findAll() throws Exception {
        return (List) checkIO.importServiceFile();    }

    @Override
    public Check add(Check o) {
        increment++;
        o.setId(increment);
        map.put(o.getId(),o);
        setFieldProperty();
        return o;
    }

    @Override
    public Check update(Check check) throws Exception {
        super.updateId(check,check.getId());
        map.put(check.getId(),check);
        return check;
    }
}
