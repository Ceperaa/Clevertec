package ru.clevertec.check.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.model.dto.CheckDto;
import ru.clevertec.check.services.CheckRunnerService;
import ru.clevertec.check.util.validation.DataValidation;

import javax.servlet.http.HttpServletResponse;

/**
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

    @PostMapping
    public CheckDto runner(String[] productIdQuantity,
                           Long idCard, HttpServletResponse response) throws Exception {
        return checkRunnerService
                .createCheck(DataValidation.validator(productIdQuantity), idCard, response.getOutputStream());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChecksOlderThanWeek(){
        checkRunnerService.deleteChecksOlderThanWeek();
    }
}
