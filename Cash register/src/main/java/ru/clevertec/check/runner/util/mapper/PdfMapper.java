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
import ru.clevertec.check.runner.model.dto.CheckDto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.stream.Collectors;

public class PdfMapper {

    public static final String PDF_FILE_PATH = "classpath:check.pdf";

    private static final LocalDate DATE = LocalDateTime.now().toLocalDate();
    private static final LocalTime TIME = LocalDateTime.now().toLocalTime().withNano(0);

    public static void checkPdf(CheckDto checkDto) throws IOException {

        StringBuilder builder = new StringBuilder();
        builder.append(".                        CASH RECEIPT                      .\n")
                .append(String.format("CHECK ID:%3s                              DATE:%8s\n"
                        , checkDto.getId(), DATE))
                .append(String.format(".                                         TIME:%8s \n"
                        , TIME))
                .append("-----------------------------------------------------------------\n")
                .append("qty   description                         price       total\n")
                .append(checkDto.getProductList()
                        .stream()
                        .map(productInformationDto ->
                                String.format("%-4s  %-35s $ %-10s$ %-5s \n"
                                        , productInformationDto.getAmount()
                                        , productInformationDto.getName()
                                        , productInformationDto.getPrice()
                                        , productInformationDto.getTotalPrice()))
                        .collect(Collectors.joining()))
                .append("-----------------------------------------------------------------\n")
                .append(String.format("TOTAL                                                 $%5s\n"
                        , checkDto.getTotalPrice()))
                .append(String.format("DISCOUNT %3s%s                                         $%5s\n"
                        , checkDto.getTotalPercent(), "%", checkDto.getDiscountAmount()))
                .append(String.format("TOTAL WITH DISCOUNT.                                  $%5s\n"
                        , checkDto.getTotalPriceWithDiscount()));
        System.out.println(builder.toString());

        savePdf(builder.toString());
    }

    private static void savePdf(String checkToString) throws IOException {
        FontProgram fontProgram =
                FontProgramFactory.createFont(Objects.requireNonNull(PdfMapper.class
                        .getClassLoader().getResourceAsStream("Anonymous.ttf")).readAllBytes());

        PdfFont font = PdfFontFactory.createFont(
                fontProgram, PdfEncodings.UTF8);
        PdfDocument pdf = new PdfDocument(new PdfWriter(PDF_FILE_PATH));
        Document document = new Document(pdf);
        Paragraph paragraph = new Paragraph(checkToString);
        paragraph.setFont(font);
        document.add(paragraph);
        document.close();
    }
}