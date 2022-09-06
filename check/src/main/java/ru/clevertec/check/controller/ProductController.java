package ru.clevertec.check.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.model.dto.MaxOfSaleProductDto;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.ProductDtoForSave;
import ru.clevertec.check.services.ProductServiceForUI;
import ru.clevertec.check.util.exception.ObjectNotFoundException;
import ru.clevertec.check.util.validation.DataValidation;

import java.util.List;

/**
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceForUI productServiceForUI;
    private static final String DEFAULT_LIMIT = "20";
    private static final String DEFAULT_OFFSET = "0";

    public ProductController(
            ProductServiceForUI productServiceForUI) {
        this.productServiceForUI = productServiceForUI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto add(@RequestBody ProductDtoForSave product) throws Exception {
        return productServiceForUI.saveProduct(DataValidation.validator(product));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto update(@RequestBody ProductDto product) throws Exception {
        return productServiceForUI.updateDto(DataValidation.validator(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) throws ObjectNotFoundException {
        productServiceForUI.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable long id) throws ObjectNotFoundException {
        return productServiceForUI.findProductDtoById(id);
    }

    @GetMapping
    public List<ProductDto> all(@RequestParam(defaultValue = DEFAULT_LIMIT) int limit,
                                @RequestParam(defaultValue = DEFAULT_OFFSET) int offset
    ) {
        return productServiceForUI.allListProductDto(offset, limit);
    }

    @GetMapping("/maxSale")
    public MaxOfSaleProductDto findProductMostSales() throws ObjectNotFoundException {
        return productServiceForUI.findProductMostSales();
    }
}
