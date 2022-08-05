package ru.clevertec.check.runner.repository.impl.streamio;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
public class ProductInformationRepositoryImpl extends RepositoryEntityImpl<ProductInformation> {

    private final Map<Long, ProductInformation> map;
    private final IStreamIO productInformationIo;
    @Value("${productInformationId}")
    private long increment;

    public ProductInformationRepositoryImpl(Map<Long, ProductInformation> map, @Qualifier("productInformationIO") IStreamIO productInformationIo) {
        super(map, productInformationIo);
        this.map = map;
        this.productInformationIo = productInformationIo;
    }

    @Override
    protected void setProperty(Properties property) {
        property.setProperty("productId", String.valueOf(increment));

    }

    @Override
    public List<ProductInformation> findAll(Integer limit, int offset) {
        return (List) productInformationIo.importServiceFile();
    }

    @Override
    public ProductInformation add(ProductInformation o) {
        increment++;
        o.setId(increment);
        map.put(o.getId(), o);
        productInformationIo.exportFile(List.of(o), false);
        super.setFieldProperty();
        return null;
    }

    @Override
    public ProductInformation update(ProductInformation productInformation) {
        long id = productInformation.getId();
        if (super.findById(id).isPresent()) {
            super.delete(id);
        }
        map.put(id, productInformation);
        productInformationIo.exportFile(List.of(productInformation), false);
        return productInformation;
    }
}
