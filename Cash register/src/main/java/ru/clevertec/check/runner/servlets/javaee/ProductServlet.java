package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/product/*"})
public class ProductServlet extends AbstractEntityServlet {

    @Autowired
    private ProductServiceForUI productService;

    @Override
    protected Object readObject(BufferedReader reader) {
        ProductDtoForSave  productDtoForSave = new Gson().fromJson(reader, ProductDtoForSave.class);
        return productDtoForSave;
    }

    @Override
    protected Object createObject(Object o) {
        return productService.saveProduct((ProductDtoForSave) o);
    }

    @Override
    protected Object updateObject(Object o) throws ObjectNotFoundException {
        return productService.updateDto((ProductDto) o);
    }

    @Override
    protected void deleteObject(long id) throws ObjectNotFoundException {
        productService.deleteProduct(id);
    }

    @Override
    protected Optional findByObjectId(long id) throws ObjectNotFoundException {
        return Optional.ofNullable(productService.findByProductDtoId(id));
    }

    @Override
    protected List<Object> findAllObject(int offset, Integer limit) {
        return Collections.singletonList(productService.allListProductDto(offset,limit));
    }
}
