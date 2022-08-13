package ru.clevertec.check.runner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.services.CheckRunnerService;

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
    public CheckDto runner(@RequestParam String[] itemIdQuantity , Long idCard) throws Exception {
        return null;// checkRunnerService.createCheck(DataValidation.validator(itemIdQuantity), idCard);
    }
}
