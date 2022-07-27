package ru.clevertec.check.runner.repository.impl.streamio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
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
    public List<Check> findAll() throws IOException {
        return (List) checkIO.importServiceFile();    }

    @Override
    public Check add(Check o) throws IOException {
        increment++;
        o.setId(increment);
        map.put(o.getId(),o);
        setFieldProperty();
        checkIO.exportFile(List.of(o), false);
        return o;
    }

    @Override
    public Check update(Check check) throws IOException {

        long id = check.getId();
        if (findById(id).isPresent()) {
            delete(id);
        }
        map.put(id, check);
        checkIO.exportFile(List.of(check), false);
        return check;
    }
}
