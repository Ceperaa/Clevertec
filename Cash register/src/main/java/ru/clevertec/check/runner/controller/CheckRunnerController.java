package ru.clevertec.check.runner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.util.validation.DataValidation;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/check")
public class CheckRunnerController {

    private final CheckRunnerService checkRunnerService;


    @Autowired
    public CheckRunnerController(CheckRunnerService checkRunnerService) {
        this.checkRunnerService = checkRunnerService;
    }

    @PostMapping("/runner")
    public CheckDto runner(String[] itemIdQuantity , Long idCard) throws Exception {
        return checkRunnerService.createCheck(DataValidation.validator(itemIdQuantity), idCard);
    }
}
