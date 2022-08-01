package ru.clevertec.check.runner.streamIO;

import java.util.List;

public interface IStreamIO {

    List<?> importServiceFile();

    void exportFile(List<?> orderList, boolean isOverwrite);
}
