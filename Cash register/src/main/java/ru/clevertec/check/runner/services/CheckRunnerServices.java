package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.CheckDto;

import java.util.List;

public interface CheckRunnerServices {

    CheckDto creatCheck(List<String> itemIdQuantity1, Long idCard) throws Exception;

}
