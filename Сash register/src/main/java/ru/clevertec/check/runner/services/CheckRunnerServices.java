package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.model.Check;

public interface CheckRunnerServices {

    Check run(String[] itemIdQuantity1, int idCard) throws Exception;
}
