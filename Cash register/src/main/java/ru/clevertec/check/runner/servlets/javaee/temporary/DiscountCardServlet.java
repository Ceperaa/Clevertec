package ru.clevertec.check.runner.servlets.javaee.temporary;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.dto.DiscountCardDtoForSave;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

//@WebServlet(urlPatterns = {"/card/*"})
public class DiscountCardServlet /*extends AbstractHttpServlet*/ {

    @Autowired
    private DiscountCardService discountCardService;
    private static final Logger logger = LogManager.getLogger(DiscountCardServlet.class);

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        DiscountCard discountCard = discountCardService.saveCard(readerDiscountCard(request.getReader()));
        try (PrintWriter printWriter = response.getWriter()) {
            response.setStatus(201);
            printWriter.write(new Gson().toJson(discountCard));
        }
        logger.debug("add");
    }

    @SneakyThrows
   // @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = DataValidation.validatorHttpUrlSearchId(req.getRequestURI());
        if (id == 0) {
            findAll(req, resp);
        } else {
            findById(id, resp);
        }
    }

    @SneakyThrows
   // @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiscountCard discountCard = discountCardService.updateDiscountCard(readerDiscountCard(req.getReader()));
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(201);
            printWriter.write(new Gson().toJson(discountCard));
        }
        logger.debug("update");
    }

    @SneakyThrows
  //  @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        discountCardService.deleteCard(DataValidation.validatorHttpUrlSearchId(requestURI));
        resp.setStatus(204);
        logger.debug("delete");
    }

    private DiscountCardDtoForSave readerDiscountCard(BufferedReader reader) {
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
        return DiscountCardDtoForSave
                .builder()
                .discount(Integer.parseInt(jsonObject.get("discount").toString()))
                .build();
    }

    private void findById(long id, HttpServletResponse resp) throws SQLException, ObjectNotFoundException, IOException {
        Optional<DiscountCard> byId = discountCardService.findById(id);
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(201);
            printWriter.write(new Gson().toJson(byId));
            logger.debug("findById");
        }
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
//        List<DiscountCard> list = discountCardService.allListDiscountCard();
//        try (PrintWriter printWriter = resp.getWriter()) {
//            resp.setStatus(201);
//            printWriter.write(new Gson().toJson(list));
//            logger.debug("findAll");
//        }
    }
}
