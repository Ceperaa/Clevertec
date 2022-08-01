package ru.clevertec.check.runner.servlets.javaee.temporary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FindAllProduct",urlPatterns = {"/helloServlet"})
public class FindAllProduct extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(FindAllProduct.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        logger.debug("Hello World");
        printWriter.write("Hello World");
    }
}
