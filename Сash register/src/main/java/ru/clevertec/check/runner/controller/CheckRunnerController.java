package ru.clevertec.check.runner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.check.runner.services.CheckRunnerServices;
import ru.clevertec.check.runner.model.Check;

@RestController
@RequestMapping("/check")
public class CheckRunnerController {

    private final CheckRunnerServices checkRunner;

    @Autowired
    public CheckRunnerController(CheckRunnerServices checkRunner) {
        this.checkRunner = checkRunner;
    }

    @GetMapping("/runner")
    public Check runner(String[] itemIdQuantity , int idCard) throws Exception {
        return checkRunner.run(itemIdQuantity, idCard);
    }
}
