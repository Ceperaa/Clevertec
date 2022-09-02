package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.beanPostProcessors.annotations.Servlet;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Servlet(url = "/product/*")
@RequiredArgsConstructor
public class ProductServlet extends AbstractEntityServlet{

    private final ProductServiceForUI productService;


    @Override
    public Object readObject(BufferedReader reader) {
        ProductDtoForSave  productDtoForSave = new Gson().fromJson(reader, ProductDtoForSave.class);
        return productDtoForSave;
    }

    @Override
    public Object createObject(Object o) {
        return productService.saveProduct((ProductDtoForSave) o);
    }

    @Override
    public Object updateObject(Object o) throws ObjectNotFoundException {
        return productService.updateDto((ProductDtoForSave) o);
    }

    @Override
    public void deleteObject(long id) throws ObjectNotFoundException {
        productService.deleteProduct(id);
    }

    @Override
    public Optional findByObjectId(long id) throws ObjectNotFoundException {
        return Optional.ofNullable(productService.findByProductDtoId(id));
    }

    @Override
    public List<Object> findAllObject(int offset, Integer limit) {
        return Collections.singletonList(productService.allListProductDto(offset,limit));
    }
}
