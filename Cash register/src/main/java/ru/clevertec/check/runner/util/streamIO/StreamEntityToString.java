package ru.clevertec.check.runner.util.streamIO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StreamEntityToString {

    public <T> void fileOutputStream(List<T> stringList, String linkAddress, boolean isOverwrite) throws IOException {
        if (isOverwrite) {
            isClose(linkAddress);
        }
        for (T t : stringList) {
            outputStream(t.toString(), linkAddress);
        }
    }

    public List<String> fileInputStream(String linkAddress) throws IOException {
        List<String> stringList = new ArrayList<>();
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

    private void createFile(String linkAddress) throws IOException {
        File file = new File(linkAddress);
            file.createNewFile();
    }

    private void outputStream(String line, String linkAddress) throws IOException {

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
