package ru.clevertec.check.runner.util.mapper;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import ru.clevertec.check.runner.dto.CheckDto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class PdfMapper {

    private static final String PDF_FILE_PATH =
            "E:\\Clevertec\\Cash register\\src\\main\\resources\\files\\check.pdf";

    private static final String REGULAR =
            "E:\\work\\Multithreding\\src\\main\\resources\\Anonymous.ttf";

    private static final LocalDate date = LocalDateTime.now().toLocalDate();
    private static final LocalTime time = LocalDateTime.now().toLocalTime().withNano(0);

    public static void checkPdf(CheckDto checkDto) throws IOException {

        String checkToString = ".                        CASH RECEIPT                      .\n" +
                String.format("CHECK ID:%3s                              DATE:%8s\n"
                        , checkDto.getId(),date) +
                String.format(".                                         TIME:%8s \n"
                        , time) +
                "-----------------------------------------------------------------\n" +
                "qty   description                         price       total\n" +
                checkDto.getProductList()
                        .stream()
                        .map(productInformationDto ->
                                String.format(" %-4s  %-35s $ %-10s$ %-5s \n"
                                        , productInformationDto.getAmount()
                                        , productInformationDto.getName()
                                        , productInformationDto.getPrice()
                                        , productInformationDto.getTotalPrice()))
                        .collect(Collectors.joining()) +
                "-----------------------------------------------------------------\n" +
                String.format("TOTAL                                                 $%5s\n"
                        , checkDto.getTotalPrice()) +
                String.format("DISCOUNT %3s%s                                         $%5s\n"
                        , checkDto.getTotalPercent(), "%", checkDto.getDiscountAmount()) +
                String.format("TOTAL WITH DISCOUNT.                                  $ %5s\n"
                        , checkDto.getTotalPriceWithDiscount());
        System.out.println(checkToString);

        savePdf(checkToString);
    }

    private static void savePdf(String checkToString) throws IOException {
        FontProgram fontProgram =
                FontProgramFactory.createFont(REGULAR);
        PdfFont font = PdfFontFactory.createFont(
                fontProgram, PdfEncodings.WINANSI);

        PdfDocument pdf = new PdfDocument(new PdfWriter(PDF_FILE_PATH));
        Document document = new Document(pdf);

        Paragraph paragraph = new Paragraph(checkToString);
        paragraph.setFont(font);

        document.add(paragraph);
        document.close();
    }
}
