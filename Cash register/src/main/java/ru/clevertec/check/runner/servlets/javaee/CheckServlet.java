package ru.clevertec.check.runner.servlets.javaee;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.util.mapper.PdfMapper;
import ru.clevertec.check.runner.util.validation.DataValidation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = {"/check/*"})
public class CheckServlet extends AbstractHttpServlet {

    @Autowired
    private CheckRunnerService runnerService;
    private static final Logger logger = LogManager.getLogger(CheckServlet.class);
    public static final String PDF_FILE_PATH =
            "E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\check.pdf";

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CheckDto checkDto = runnerService.createCheck(
                DataValidation.validator(req.getParameterValues("id"))
                , Long.valueOf(req.getParameter("icCard"))
        );
        PdfMapper.checkPdf(checkDto);
        File pdfFile = new File(PDF_FILE_PATH);
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
        logger.debug("addCheck");
    }

//    private void savePdfFail(CheckDto checkDto) throws FileNotFoundException {
//        PdfDocument pdf = new PdfDocument(new PdfWriter(PDF_FILE_PATH));
//        Document document = new Document(pdf);
//        Paragraph paragraph = new Paragraph(checkDto.toString());
//        paragraph.setFixedLeading(10);
//        document.add(new Paragraph(checkDto.toString()));
//        document.close();
//    }
}
