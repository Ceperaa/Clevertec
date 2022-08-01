package ru.clevertec.check.runner.streamIO.impl;

import lombok.SneakyThrows;
import ru.clevertec.check.runner.streamIO.IStreamIO;
import ru.clevertec.check.runner.streamIO.StreamEntityToString;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class StreamIO implements IStreamIO {

    private final String LINK_ADDRESS;
    private final StreamEntityToString streamIO = new StreamEntityToString();

    public StreamIO(String LINK_ADDRESS) {
        this.LINK_ADDRESS = LINK_ADDRESS;
    }

    @SneakyThrows(IOException.class)
    public List<?> importServiceFile() {
        List<String> list = streamIO.fileInputStream(LINK_ADDRESS);
        return list
                .stream()
                .map(this::objectAssembly)
                .collect(Collectors.toList());
    }

    @SneakyThrows(IOException.class)
    public void exportFile(List<?> orderList, boolean isOverwrite) {
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

    abstract protected Object objectAssembly(String text);
}
