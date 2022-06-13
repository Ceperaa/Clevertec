package ru.clevertec.check.runner.validation.modul;


import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Operation {

   // private final PropertyFile propertyFile;


    public void configProperty(Map<String, Object> map) {
        for (Object obj : map.values()) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(ConfigProperty.class)) {
                    ConfigProperty ann = field.getAnnotation(ConfigProperty.class);
                    String pattern = ann.pattern();
                   // propertyFile.setPropertyName(ann.propertyName());
                    String value = "";// propertyFile.updateFieldProperty(configName);
                    field.setAccessible(true);
                    try {
                        switch (ann.type()) {
                            case BOOLEAN:
                                boolean value2 = Boolean.parseBoolean(value);
                                field.set(obj, value2);
                                break;
                            case INT:
                                int value1 = Integer.parseInt(value);
                                field.set(obj, value1);
                                break;
                            case STRING:
                                field.set(obj, value);
                                break;
                            case COLLECTION:
                                List<Object> list = Collections.singletonList(value);
                                field.set(obj, list);
                                break;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//    public void inject(Map<String, Object> map)
//        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        map.put("propertyFile",/* propertyFile*/);
//        for (Entry<String, Object> component : map.entrySet()) {
//            for (Field field : component.getValue().getClass().getDeclaredFields()) {
//                if (field.isAnnotationPresent(Autowire.class)) {
//                    Autowire annotation = field.getAnnotation(Autowire.class);
//                    Object object = map.get(annotation.name());
//                    field.setAccessible(true);
//                    field.set(component.getValue(), object);
//                }
//            }
//        }
//    }
}

