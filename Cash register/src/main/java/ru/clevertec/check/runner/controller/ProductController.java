package ru.clevertec.check.runner.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.services.impl.ProductServicesImpl;
import ru.clevertec.check.runner.streamIO.StreamEntityToString;

import java.util.List;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServicesImpl productServices;
    private final StreamEntityToString streamEntityToString;

    public ProductController(ProductServicesImpl productServices, StreamEntityToString streamEntityToString) {
        this.productServices = productServices;
        this.streamEntityToString = streamEntityToString;
    }

    @PostMapping("/add")
    public Product add(@RequestBody @Validated Product product, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()){
            streamEntityToString.fileOutputStream(List.of(bindingResult.getObjectName()),"E:\\Clevertec\\Cash register\\src\\main\\java\\ru\\clevertec\\check\\runner\\streamIO\\files\\invalidData.txt",false);
            return null;
        } else {
            return productServices.saveProduct(product);
        }
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
