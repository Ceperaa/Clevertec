package ru.clevertec.check.runner.servlets.javaee.temporary;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

//@WebServlet(urlPatterns = {"/product/*"})
public class ProductSer /*extends AbstractHttpServlet*/ {

    @Autowired
    private ProductServiceForUI productService;
    protected final Logger logger = LogManager.getLogger(ProductSer.class);
    private final ModelMapper modelMapper = new ModelMapper();

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("add");
        ProductDto productDto = productService.saveProduct(DataValidation.validator(
                modelMapper.map(readerDiscountCard(request.getReader()), ProductDtoForSave.class))
        );
        try (PrintWriter printWriter = response.getWriter()) {
            response.setStatus(201);
            printWriter.write(new Gson().toJson(productDto));
        }
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
        ProductDtoForSave productDto = productService.updateDto(readerDiscountCard(req.getReader()));
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(201);
            printWriter.write(new Gson().toJson(productDto));
            logger.debug("update");
        }
    }

    @SneakyThrows
   // @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        productService.deleteProduct(DataValidation.validatorHttpUrlSearchId(requestURI));
        resp.setStatus(204);
        logger.debug("delete");
    }

    private ProductDto readerDiscountCard(BufferedReader reader) {
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
        return ProductDto
                .builder()
                .name(jsonObject.get("name").toString())
                .amount(jsonObject.get("amount").toString())
                .discountPercent(jsonObject.get("discountPercent").getAsInt())
                .price(jsonObject.get("price").toString())
                .build();
    }

    private void findById(long id, HttpServletResponse resp) throws SQLException, ObjectNotFoundException, IOException {
        ProductDto byId = productService.findByProductDtoId(id);
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setStatus(201);
            printWriter.write(new Gson().toJson(byId));
            logger.debug("findById");
        }
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
//        List<ProductDto> list = productService.allListProductDto();
//        try (PrintWriter printWriter = resp.getWriter()) {
//            resp.setStatus(201);
//            printWriter.write(new Gson().toJson(list));
//            logger.debug("findAll");
//        }
    }
}