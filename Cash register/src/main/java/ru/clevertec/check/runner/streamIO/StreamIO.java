package ru.clevertec.check.runner.streamIO;

import ru.clevertec.check.runner.model.Check;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class StreamIO {

    private final String LINK_ADDRESS;
    private final StreamEntityToString streamIO = new StreamEntityToString();

    public StreamIO(String LINK_ADDRESS) {
        this.LINK_ADDRESS = LINK_ADDRESS;
    }

    public void importServiceFile() throws Exception {
        streamIO.fileInputStream(LINK_ADDRESS).forEach(this::objectAssembly);
    }

    public void exportServiceFile(List<Check> orderList, boolean isOverwrite) throws Exception {
        streamIO.fileOutputStream(orderList, LINK_ADDRESS, isOverwrite);
    }

    protected Map<String, String> parseMap(String text) {
        Map<String, String> map = new LinkedHashMap<>();
        if (text.compareTo("") != 0) {
            for (String keyValue : text.split(", ")) {
                String[] parts = keyValue.split("=", 2);
                map.put(parts[0], parts[1]);
            }
        }
        return map;
    }

    abstract protected void objectAssembly(String text);
}
