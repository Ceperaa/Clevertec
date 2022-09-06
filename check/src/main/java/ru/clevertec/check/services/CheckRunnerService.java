package ru.clevertec.check.services;

import ru.clevertec.check.model.dto.CheckDto;

import java.io.OutputStream;
import java.util.List;

public interface CheckRunnerService {

    CheckDto createCheck(List<String> itemIdQuantity1, Long idCard,OutputStream outputStream) throws Exception;

    void deleteChecksOlderThanWeek();

}
