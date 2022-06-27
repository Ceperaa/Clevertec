package ru.clevertec.check.runner.controller;

import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.runner.dto.ProductCreatDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.services.ProductServices;
import ru.clevertec.check.runner.util.validation.DataValidation;

import java.util.List;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    @PutMapping("/add")
    public ProductCreatDto add(ProductCreatDto product) throws Exception {
            return productServices.saveProduct(DataValidation.validator(product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) throws Exception {
        productServices.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable long id) throws Exception {
        return productServices.findById(id);
    }

    @GetMapping("/all")
    public List<ProductCreatDto> all() throws Exception {
        return productServices.allListProductDto();
    }
}
