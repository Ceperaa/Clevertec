package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.CheckDto;

public interface CheckRunnerServices {

    CheckDto creatCheck(String[] itemIdQuantity1, Long idCard) throws Exception;

}
