package ru.clevertec.check.runner.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.runner.model.dto.CheckDto;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Check;
import ru.clevertec.check.runner.model.entity.Product;
import ru.clevertec.check.runner.model.entity.ProductInformation;
import ru.clevertec.check.runner.repository.CheckRepository;
import ru.clevertec.check.runner.services.CheckRunnerService;
import ru.clevertec.check.runner.services.ProductInformationService;
import ru.clevertec.check.runner.services.ProductService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.mapper.PdfMapper;
import ru.clevertec.check.runner.util.mapperMapstruct.CheckMapper;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sergey Degtyarev
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CheckRunnerServiceImpl implements CheckRunnerService {

    private final ProductService productService;
    private final CheckRepository checkRepository;
    private final ProductInformationService productInformationService;
    private final CheckMapper mapper;
    private static final LocalDateTime DATE = LocalDateTime.now();

    @Transactional
    public CheckDto createCheck(List<String> itemIdQuantity, Long idCard, OutputStream stream)
            throws SQLException, ObjectNotFoundException, IOException {
        List<ProductInformationDto> productInformationList = new ArrayList<>();
        List<ProductInformation> productList = new ArrayList<>();
        productListForCheck(splitItemIdQuantity(itemIdQuantity), productList, productInformationList);
        final int TOTAL_PERCENT = 100;
        double totalPriceWithDiscount = 0;
        double discountAmount = 0;
        int totalPercent = 0;
        double totalPrice = totalPrice(productInformationList);
        if (totalPrice != 0) {
            totalPriceWithDiscount = productInformationService.discountСalculation(productList,
                    productService.totalPriceWithDiscount(productInformationList),
                    idCard);
            discountAmount = totalPrice - totalPriceWithDiscount;
            totalPercent = (int) ((discountAmount * TOTAL_PERCENT) / totalPrice);
        }
        Check check = Check.builder()
                .date(DATE)
                .productList(productList)
                .totalPriceWithDiscount(DoubleFormatting.formatting(totalPriceWithDiscount))
                .discountAmount(DoubleFormatting.formatting(discountAmount))
                .totalPercent(totalPercent)
                .totalPrice(DoubleFormatting.formatting(totalPrice))
                .build();
        check = saveCheck(check);
        CheckDto checkDto = mapToCheckDto(check, productInformationList);
        checkMapToPdf(checkDto, stream);
        return checkDto;
    }

    private void checkMapToPdf(CheckDto checkDto, OutputStream responseOutputStream) throws IOException {
        PdfMapper.checkPdf(checkDto);
        File pdfFile = new File(PdfMapper.PDF_FILE_PATH);
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        log.debug("fileInputstream length : " + fileInputStream.available());
        int length;
        byte[] buffer = new byte[4096];
        while ((length = fileInputStream.read(buffer)) > 0) {
            responseOutputStream.write(buffer, 0, length);
        }
        log.debug(" outputstream length : " + responseOutputStream.toString());
        fileInputStream.close();
        responseOutputStream.flush();
        responseOutputStream.close();
        log.debug("addCheck completed");
    }

    private void productListForCheck(
            Map<Long, Integer> map,
            List<ProductInformation> productList,
            List<ProductInformationDto> productInformationDtoList
    ) throws ObjectNotFoundException {
        for (Map.Entry<Long, Integer> integerEntry : map.entrySet()) {
            Product product = productService.findById(integerEntry.getKey());
            productList.add(productInformationService.addDescriptionInCheck(
                    ProductInformation
                            .builder()
                            .totalPrice(Double.parseDouble(product.getPrice()))
                            .discountPercent(product.getDiscountPercent())
                            .product(product)
                            .amount(integerEntry.getValue())
                            .build(),
                    productInformationDtoList
            ));
        }
    }

    private double totalPrice(List<ProductInformationDto> productList) {
        return productList
                .stream()
                .map(ProductInformationDto::getTotalPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public CheckDto mapToCheckDto(Check check, List<ProductInformationDto> productInformationDtoList) {
        CheckDto checkDto = mapper.entityToDto(check);
        checkDto.setProductList(productInformationDtoList);
        return checkDto;
    }

    private Map<Long, Integer> splitItemIdQuantity(List<String> itemIdQuantity) {
        return itemIdQuantity
                .stream()
                .map(s->  s.split("-"))
                .collect(Collectors.toMap(s-> Long.parseLong(s[0]),s->Integer.parseInt(s[1])));
    }

    @Transactional
    public void deleteChecksOlderThanWeek() {
        int WEEKS = 1;
        checkRepository.deleteCheck(LocalDateTime.now().minusWeeks(WEEKS));
    }

    public Check saveCheck(Check check) {
        return checkRepository.save(check);
    }
}

