package ru.clevertec.check.runner.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.ProductInformation;

import java.util.Map;

@Repository
public class ProductInformationRepository {

    private final Map<Long, ProductInformation> productInformationMap;

    @Autowired
    public ProductInformationRepository(Map<Long, ProductInformation> productInformationMap) {
        this.productInformationMap = productInformationMap;
    }


    public ProductInformation get(long id) {
        return productInformationMap.get(id);
    }

    public void save(ProductInformation productInformation){
        productInformationMap.replace(productInformation.getId(),productInformation);
    }
}
