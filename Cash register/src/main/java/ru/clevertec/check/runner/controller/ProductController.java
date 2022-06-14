package ru.clevertec.check.runner.controller;

import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.services.impl.ProductServicesImpl;
import ru.clevertec.check.runner.util.validation.DataValidation;

import java.util.List;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServicesImpl productServices;

    public ProductController(ProductServicesImpl productServices) {
        this.productServices = productServices;
    }

    @PostMapping("/add")
    public Product add(@RequestBody Product product) throws Exception {
            return productServices.saveProduct(DataValidation.validator(product));

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) throws Exception {
        productServices.deleteProduct(id);
    }

    @GetMapping("/all")
    public List<Product> all() throws Exception {
        return productServices.allListProduct();
    }
}
