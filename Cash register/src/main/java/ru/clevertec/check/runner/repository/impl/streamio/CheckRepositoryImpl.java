package ru.clevertec.check.runner.repository.impl.streamio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.streamIO.IStreamIO;
import ru.clevertec.check.runner.util.Pagination;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
public class CheckRepositoryImpl extends RepositoryEntityImpl<Check> {

    private final Map<Long, Check> map;
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
    public List<Check> findAll(Integer limit, int offset) {
        return Pagination.getPage((List) checkIO.importServiceFile(), offset, limit);
    }

    @Override
    public Check add(Check o) {
        increment++;
        o.setId(increment);
        map.put(o.getId(), o);
        super.setFieldProperty();
        checkIO.exportFile(List.of(o), false);
        return o;
    }

    @Override
    public Check update(Check check) {

        long id = check.getId();
        if (super.findById(id).isPresent()) {
            super.delete(id);
        }
        map.put(id, check);
        checkIO.exportFile(List.of(check), false);
        return check;
    }
}
