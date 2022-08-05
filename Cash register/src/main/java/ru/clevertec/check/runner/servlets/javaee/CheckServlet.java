package ru.clevertec.check.runner.servlets.javaee;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.util.mapper.PdfMapper;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Optional;

@WebServlet(urlPatterns = {"/check/*"})
public class CheckServlet extends AbstractHttpServlet {

    @Autowired
    private CheckRunnerService runnerService;
    private static final Logger logger = LogManager.getLogger(CheckServlet.class);

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String idCard = Optional.ofNullable(req.getParameter("idCard")).orElseGet(()->"0") ;

        CheckDto checkDto = runnerService.createCheck(
                DataValidation.validator(req.getParameterValues("productId-quantity"))
                , Long.valueOf(idCard)
        );
        PdfMapper.checkPdf(checkDto);
        File pdfFile = new File(PdfMapper.PDF_FILE_PATH);
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = resp.getOutputStream();
        logger.debug("fileInputstream length : " + fileInputStream.available());
        int length;
        byte[] buffer = new byte[4096];
        while ((length = fileInputStream.read(buffer)) > 0) {
            responseOutputStream.write(buffer, 0, length);
        }
        logger.debug(" outputstream length : " + responseOutputStream.toString());
        fileInputStream.close();
        responseOutputStream.flush();
        responseOutputStream.close();
        logger.debug("addCheck completed");
    }
}
