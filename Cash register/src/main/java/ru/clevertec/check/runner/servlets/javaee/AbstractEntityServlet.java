package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
public  abstract class AbstractEntityServlet extends HttpServlet implements EntityCrud {

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Object object =  createObject(readObject(request.getReader()));
        try (PrintWriter printWriter = response.getWriter()) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType(MediaType.APPLICATION_JSON);
            printWriter.write(new Gson().toJson(object));
        }
        log.debug("add completed");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        long id = DataValidation.validatorHttpUrlSearchId(req.getRequestURI());
        if (id == 0) {
            findAll(req, resp);
        } else {
            findById(id, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        Object discountCard = updateObject(req.getReader());
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType(MediaType.APPLICATION_JSON);
            printWriter.write(new Gson().toJson(discountCard));
        }
        log.debug("update completed");
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        deleteObject(DataValidation.validatorHttpUrlSearchId(requestURI));
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        resp.setContentType(MediaType.APPLICATION_JSON);
        log.debug("delete completed");
    }

    private void findById(long id, HttpServletResponse resp) throws IOException, ObjectNotFoundException, SQLException {
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(MediaType.APPLICATION_JSON);
            printWriter.write(new Gson().toJson(findByObjectId(id)));
            log.debug("findById completed");
        }
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        List<Object> list = findAllObject(Integer.parseInt(
                Optional.ofNullable(req.getParameter("offset")).orElse("0"))
                , Integer.parseInt(
                        Optional.ofNullable(req.getParameter("limit")).orElse("0"))
        );
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(MediaType.APPLICATION_JSON);
            printWriter.write(new Gson().toJson(list));
            log.debug("findAll completed");
        }
    }

    abstract protected Object readObject(BufferedReader reader);
}