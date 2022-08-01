package ru.clevertec.check.runner.servlets.javaee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.dto.ProductDtoForSave;
import ru.clevertec.check.runner.services.ProductServiceForUI;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/product/*"})
public class ProductServlet extends AbstractEntityServlet {

    @Autowired
    private ProductServiceForUI productService;

    @Override
    protected Object readerDiscountCard(BufferedReader reader) {
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
        return ProductDto
                .builder()
                .name(jsonObject.get("name").toString())
                .amount(jsonObject.get("amount").toString())
                .discountPercent(jsonObject.get("discountPercent").getAsInt())
                .price(jsonObject.get("price").toString())
                .build();
    }

    @Override
    protected Object createObject(Object o) throws Exception {
        return productService.saveProduct((ProductDtoForSave) o);
    }

    @Override
    protected Object updateObject(Object o) throws IOException, SQLException {
        return productService.updateDto((ProductDto) o);
    }

    @Override
    protected void deleteObject(long id) throws IOException, SQLException, ObjectNotFoundException {
        productService.deleteProduct(id);
    }

    @Override
    protected Optional findByObjectId(long id) throws SQLException, ObjectNotFoundException, IOException {
        return Optional.ofNullable(productService.findByProductDtoId(id));
    }

    @Override
    protected List<Object> findAllObject(int offset, int limit) throws IOException, SQLException {
        return Collections.singletonList(productService.allListProductDto(offset,limit));
    }
}
