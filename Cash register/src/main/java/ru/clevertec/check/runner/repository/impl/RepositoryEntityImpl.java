package ru.clevertec.check.runner.repository.impl;

import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.repository.util.AutoIncrement;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class RepositoryEntityImpl<E> implements RepositoryEntity<E> {

    private final Map<Long, E> map;
    private final AutoIncrement autoIncrement;
    private final IStreamIO streamIO;
    private final Properties properties;

    public RepositoryEntityImpl(Map<Long, E> map, IStreamIO streamIO) {
        this.map = map;
        this.streamIO = streamIO;
        this.autoIncrement = new AutoIncrement(map);
        this.properties = new Properties();
    }

    protected void updateId(E check, long id) throws Exception {
        Object check1 = findById(id);
        if (check1 != null) {
            delete(id);
        } else {
            autoIncrement.setID(id);
        }
        map.put(id,check);
        map.replace(id,check);
    }

    protected long createId(E check){
        long id;
        if (map.isEmpty()) {
            id = 0;
        } else {
            id = autoIncrement.getID();
        }
        map.put(id,check);
        return id;
    }

    protected abstract void setProperty(Properties property);

    protected void setFieldProperty() {
        try {
            properties.load(new FileInputStream("E:\\Clevertec\\Cash register\\src\\main\\resources\\increment.properties"));
            setProperty(properties);
            properties.store(new FileOutputStream("E:\\Clevertec\\Cash register\\src\\main\\resources\\increment.properties"), null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public E findById(Long id){
        return map.get(id);
    }


    public void delete(long id) throws Exception {
        map.remove(id);
        streamIO.exportFile(List.copyOf(map.values()),true);
    }

//    public List<E> findAll(){
//        return new ArrayList<>(map.values());
//    }

   public abstract List<E> findAll() throws Exception;

    public abstract E add(E e) throws Exception;

    public abstract E update(E e) throws Exception;
}