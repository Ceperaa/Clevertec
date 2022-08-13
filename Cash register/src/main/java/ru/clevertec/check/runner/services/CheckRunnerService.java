package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.CheckDto;

import java.io.OutputStream;
import java.util.List;

public interface CheckRunnerService {

    CheckDto createCheck(List<String> itemIdQuantity1, Long idCard, OutputStream stream) throws Exception;

}
