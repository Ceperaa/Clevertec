package ru.clevertec.check.runner.servlets.javaee;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class CheckServlet extends HttpServlet {

    @Autowired
    private CheckRunnerService runnerService;

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String idCard = Optional.ofNullable(req.getParameter("idCard")).orElse("0");
        runnerService.createCheck(
                DataValidation.validator(req.getParameterValues("productId-quantity"))
                , Long.valueOf(idCard)
                , resp.getOutputStream()
        );
    }
}
