package ru.clevertec.check.runner.services;

import ru.clevertec.check.runner.dto.CheckDto;

import java.util.List;

public interface CheckRunnerService {

    //@Transactional
    CheckDto createCheck(List<String> itemIdQuantity1, Long idCard) throws Exception;

}
