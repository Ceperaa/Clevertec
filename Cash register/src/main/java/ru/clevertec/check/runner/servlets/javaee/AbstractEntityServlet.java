package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.clevertec.check.runner.services.EntityServiceCrud;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Slf4j
public  abstract class AbstractEntityServlet<T> extends HttpServlet {

    private EntityServiceCrud entityServiceCrud;

    protected EntityServiceCrud getEntityServiceCrud() {
        return entityServiceCrud;
    }

    protected void setEntityServiceCrud(EntityServiceCrud entityServiceCrud) {
        this.entityServiceCrud = entityServiceCrud;
    }

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        T object = (T) getEntityServiceCrud().create(readObject(request.getReader()));
        try (PrintWriter printWriter = response.getWriter()) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
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
        T discountCard = (T) getEntityServiceCrud().update(req.getReader());
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpStatus.CREATED.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(discountCard));
        }
        log.debug("update completed");
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        getEntityServiceCrud().delete(DataValidation.validatorHttpUrlSearchId(requestURI));
        resp.setStatus(HttpStatus.NO_CONTENT.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.debug("delete completed");
    }

    private void findById(long id, HttpServletResponse resp) throws ObjectNotFoundException, IOException {
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpStatus.OK.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(getEntityServiceCrud().findById(id)));
            log.debug("findById completed");
        }
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<T> list = (List<T>) getEntityServiceCrud().allList(Integer.parseInt(
                Optional.ofNullable(req.getParameter("offset")).orElse("0"))
                , Integer.parseInt(
                        Optional.ofNullable(req.getParameter("limit")).orElse("0"))
        );
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpStatus.OK.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(list));
            log.debug("findAll completed");
        }
    }

    abstract protected Object readObject(BufferedReader reader);
}
