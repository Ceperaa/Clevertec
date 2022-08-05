package ru.clevertec.check.runner.repository.impl.streamio;

import lombok.SneakyThrows;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public abstract class RepositoryEntityImpl<E> implements RepositoryEntity<E> {

    private final Map<Long, E> map;
    private final IStreamIO streamIO;
    private final Properties properties;

    public RepositoryEntityImpl(Map<Long, E> map, IStreamIO streamIO) {
        this.map = map;
        this.streamIO = streamIO;
        this.properties = new Properties();
    }


    protected abstract void setProperty(Properties property);

    @SneakyThrows(IOException.class)
    protected void setFieldProperty() {

            properties.load(new FileInputStream("E:\\Clevertec\\Cash register\\src\\main\\resources\\increment.properties"));
            setProperty(properties);
            properties.store(new FileOutputStream("E:\\Clevertec\\Cash register\\src\\main\\resources\\increment.properties"), null);
    }

    public Optional findById(Long id){
        return Optional.ofNullable(map.get(id));
    }



    public void delete(long id) {
        map.remove(id);
        streamIO.exportFile(List.copyOf(map.values()),true);
    }

   public abstract List<E> findAll(Integer limit,int offset);

    public abstract E add(E e);

    public abstract E update(E e);
}
