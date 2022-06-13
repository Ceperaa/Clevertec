package ru.clevertec.check.runner.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
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
    public List<Product> findAll() throws Exception {
        return (List) productIO.importServiceFile();
    }

    @Override
    public Product add(Product o) throws Exception {
        //o.setId(super.createId(o));
        increment++;
        o.setId(increment);
        map.put(o.getId(), o);
        productIO.exportFile(List.of(o), false);
        setFieldProperty();
        return o;
    }

    @Override
    public Product update(Product product) throws Exception {
        super.updateId(product, product.getId());
        map.put(product.getId(), product);
        return product;
    }
}
