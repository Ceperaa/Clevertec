package ru.clevertec.check.runner.streamIO;

import java.io.IOException;
import java.util.List;

public interface IStreamIO {

    List<?> importServiceFile() throws IOException;

    void exportFile(List<?> orderList, boolean isOverwrite) throws IOException;
}
