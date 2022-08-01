package ru.clevertec.check.runner.repository.impl.streamio;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
public class ProductRepositoryImpl extends RepositoryEntityImpl<Product> {

    private final Map<Long, Product> map;
    private final IStreamIO productIO;

    @Value("${productId}")
    private long increment;

    public ProductRepositoryImpl(Map<Long, Product> map, IStreamIO productIO) {
        super(map, productIO);
        this.map = map;
        this.productIO = productIO;
    }

    @Override
    protected void setProperty(Properties properties) {
        properties.setProperty("productId", String.valueOf(increment));
    }

    @Override
    public List<Product> findAll(int limit, int offset) {
        return (List) productIO.importServiceFile();
    }

    @Override
    public Product add(Product o) {
        increment++;
        o.setId(increment);
        map.put(o.getId(), o);
        productIO.exportFile(List.of(o), false);
        super.setFieldProperty();
        return o;
    }

    public Product update(Product product) {
        long id = product.getId();
        if (super.findById(id).isPresent()) {
            super.delete(id);
        }
        map.put(id, product);
        productIO.exportFile(List.of(product), false);
        return product;
    }
}
