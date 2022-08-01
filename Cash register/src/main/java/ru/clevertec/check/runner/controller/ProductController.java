package ru.clevertec.check.runner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sergey Degtyarev
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceForUI productServiceForUI;

    public ProductController(
             ProductServiceForUI productServiceForUI) {
        this.productServiceForUI = productServiceForUI;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto add(ProductDtoForSave product) throws Exception {
            return productServiceForUI.saveProduct(DataValidation.validator(product));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDtoForSave update(ProductDto product) throws Exception {
        // FIXME: 25.07.2022 добавить валидацию или удалить метод
        return productServiceForUI.updateDto(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) throws IOException, ObjectNotFoundException, SQLException {
        productServiceForUI.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable long id) throws SQLException, ObjectNotFoundException {
        return productServiceForUI.findByProductDtoId(id);
    }

    @GetMapping("/all")
    public List<ProductDto> all(@RequestParam int offset,@RequestParam int limit) throws IOException, SQLException {
        return productServiceForUI.allListProductDto(offset,limit);
    }
}
