package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.model.Check;

public interface CheckRunnerServices {

    Check creatCheck(String[] itemIdQuantity1, Integer idCard) throws Exception;
}
