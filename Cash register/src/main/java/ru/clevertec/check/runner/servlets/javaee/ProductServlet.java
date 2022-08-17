package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.dto.ProductDto;
import ru.clevertec.check.runner.model.dto.ProductDtoForSave;
import ru.clevertec.check.runner.util.beanPostProcessors.annotations.Servlet;
import ru.clevertec.check.runner.services.EntityServiceCrud;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;

@Component
@Servlet(url = "/product/*")
public class ProductServlet extends AbstractEntityServlet<ProductDto> {

    @Autowired
    @Qualifier("productServiceImpl")
    private EntityServiceCrud<ProductDto> productEntityServiceCrud;

    @PostConstruct
    public void init(){
        setEntityServiceCrud(productEntityServiceCrud);
    }

    @Override
    protected EntityServiceCrud getEntityServiceCrud() {
        return productEntityServiceCrud;
    }

    @Override
    public Object readObject(BufferedReader reader) {
        ProductDtoForSave  productDtoForSave = new Gson().fromJson(reader, ProductDtoForSave.class);
        return productDtoForSave;
    }
}
