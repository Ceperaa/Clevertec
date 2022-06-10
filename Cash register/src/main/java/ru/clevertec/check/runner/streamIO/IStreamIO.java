package ru.clevertec.check.runner.streamIO;

import java.util.List;

public interface IStreamIO {

    List<?> importServiceFile() throws Exception;

    void exportFile(List<?> orderList, boolean isOverwrite) throws Exception;
}
