package ru.clevertec.check.runner.servlets.javaee;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.beanPostProcessors.annotations.Servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.PrintWriter;

@Servlet(url = "/product/maxSale")
@RequiredArgsConstructor
public class ProductMaxSale extends HttpServlet {

    private final ProductServiceForUI productService;

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter printWriter = resp.getWriter()) {
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.print(
                    new ObjectMapper()
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString(productService.findProductMostSales()));
        }
    }
}
