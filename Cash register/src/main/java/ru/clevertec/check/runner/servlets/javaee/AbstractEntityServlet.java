package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Log4j2
public abstract class AbstractEntityServlet extends AbstractHttpServlet{

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Object object = createObject(DataValidation.validator((ProductDtoForSave) readObject(request.getReader())));
        try (PrintWriter printWriter = response.getWriter()) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(object));
        }
        log .debug("add completed");
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
        deleteObject(DataValidation.validatorHttpUrlSearchId(requestURI));
        resp.setStatus(HttpStatus.NO_CONTENT.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.debug("delete completed");
    }

    private void findById(long id, HttpServletResponse resp) throws SQLException, ObjectNotFoundException, IOException {
        Optional<Object> byId = findByObjectId(id);
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpStatus.OK.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(byId));
            log.debug("findById completed");
        }
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {


        List<Object> list = findAllObject(Integer.parseInt(
                Optional.ofNullable(req.getParameter("offset")).orElse("0"))
                , Integer.parseInt(
                        Optional.ofNullable(req.getParameter("limit")).orElse("0"))
        );
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(HttpStatus.OK.value());
            printWriter.write(new Gson().toJson(list));
            log.debug("findAll completed");
        }
    }

    abstract protected Object readObject(BufferedReader reader);

    abstract protected Object createObject(Object o) throws Exception;

    abstract protected Object updateObject(Object o) throws IOException, SQLException, ObjectNotFoundException;

    abstract protected void deleteObject(long id) throws IOException, SQLException, ObjectNotFoundException;

    abstract protected Optional findByObjectId(long id) throws SQLException, ObjectNotFoundException, IOException;

    abstract protected List<Object> findAllObject(int offset, Integer limit) throws IOException, SQLException;
}
