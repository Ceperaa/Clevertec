package ru.clevertec.check.runner.servlets.javaee;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.util.beanPostProcessors.annotations.Servlet;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Servlet(url = "/check/*")
@Slf4j
public class CheckServlet extends HttpServlet {

    @Autowired
    private CheckRunnerService runnerService;

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String idCard = Optional.ofNullable(req.getParameter("idCard")).orElse("0");
        runnerService.createCheck(
                DataValidation.validator(req.getParameterValues("productId-quantity")),
                Long.valueOf(idCard),
                resp.getOutputStream()
        );
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        runnerService.deleteChecksOlderThanWeek();
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        log.debug("delete completed");
    }
}
