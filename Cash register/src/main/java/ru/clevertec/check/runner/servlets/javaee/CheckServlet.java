package ru.clevertec.check.runner.servlets.javaee;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@WebServlet(urlPatterns = {"/check/*"})
public class CheckServlet extends AbstractHttpServlet {

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
