package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityServlet extends AbstractHttpServlet{

    private static final Logger logger = LogManager.getLogger(AbstractHttpServlet.class);

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Object discountCard = createObject(readerDiscountCard(request.getReader()));
        try (PrintWriter printWriter = response.getWriter()) {
            response.setStatus(201);
            printWriter.write(new Gson().toJson(discountCard));
        }
        logger.debug("add completed");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = DataValidation.validatorHttpUrlSearchId(req.getRequestURI());
        if (id == 0) {
            findAll(req, resp);
        } else {
            findById(id, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object discountCard = updateObject(req.getReader());
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(201);
            printWriter.write(new Gson().toJson(discountCard));
        }
        logger.debug("update completed");
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        deleteObject(DataValidation.validatorHttpUrlSearchId(requestURI));
        resp.setStatus(204);
        logger.debug("delete completed");
    }

    private void findById(long id, HttpServletResponse resp) throws SQLException, ObjectNotFoundException, IOException {
        Optional<Object> byId = findByObjectId(id);
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(200);
            printWriter.write(new Gson().toJson(byId));
            logger.debug("findById completed");
        }
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        List<Object> list = findAllObject(Integer.parseInt(
                req.getParameter("offset"))
                , Integer.parseInt(req.getParameter("limit"))
        );
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(200);
            printWriter.write(new Gson().toJson(list));
            logger.debug("findAll completed");
        }
    }

    abstract protected Object readerDiscountCard(BufferedReader reader);

    abstract protected Object createObject(Object o) throws Exception;

    abstract protected Object updateObject(Object o) throws IOException, SQLException, ObjectNotFoundException;

    abstract protected void deleteObject(long id) throws IOException, SQLException, ObjectNotFoundException;

    abstract protected Optional findByObjectId(long id) throws SQLException, ObjectNotFoundException, IOException;

    abstract protected List<Object> findAllObject(int offset, int limit) throws IOException, SQLException;
}
