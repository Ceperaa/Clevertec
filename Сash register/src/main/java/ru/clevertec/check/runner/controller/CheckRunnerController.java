package ru.clevertec.check.runner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.check.runner.CheckRunner;
import ru.clevertec.check.runner.model.Check;

import java.util.Map;

@RestController
@RequestMapping("/check")
public class CheckRunnerController {

    private CheckRunner checkRunner;

    @Autowired
    public CheckRunnerController(CheckRunner checkRunner) {
        this.checkRunner = checkRunner;
    }

    @GetMapping("/runner")
    public Check runner(Map<Long,Integer> itemIdQuantity, int idCard){
       return checkRunner.run(itemIdQuantity, idCard);
    }
}
