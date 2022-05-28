package ru.clevertec.check.runner.streamIO;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class StreamEntityToString {

    private final List<String> stringList = new ArrayList<>();

    public <T> void fileOutputStream(List<T> stringList, String linkAddress, boolean isOverwrite) throws Exception {
        if (isOverwrite) {
            isClose(linkAddress);
        }
        for (T t : stringList) {
            outputStream(t.toString(), linkAddress);
        }
    }

    public List<String> fileInputStream(String linkAddress) throws Exception {
        createFile(linkAddress);
        BufferedReader bufferedReader = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(linkAddress);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                stringList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

    private void createFile(String linkAddress) throws Exception {
        File file = new File(linkAddress);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    private void outputStream(String line, String linkAddress) throws Exception {

        createFile(linkAddress);
        FileOutputStream outputStream = null;
        line = line.concat("\n");
        try {
            outputStream = new FileOutputStream(linkAddress, true);
            outputStream.write(line.getBytes(StandardCharsets.UTF_8));

        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void isClose(String linkAddress) {
        try {
            new FileOutputStream(linkAddress).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
