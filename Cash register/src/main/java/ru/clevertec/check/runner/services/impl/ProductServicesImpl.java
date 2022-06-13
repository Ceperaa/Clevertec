package ru.clevertec.check.runner.services.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.runner.streamIO.impl.ProductIO;

import java.util.List;

@Service
public class ProductServicesImpl {

    private final ProductRepositoryImpl productRepo;
    private final ProductIO productIO;

    public ProductServicesImpl(ProductRepositoryImpl productRepo, ProductIO productIO) {
        this.productRepo = productRepo;
        this.productIO = productIO;
    }

    public Product findById(long id){
       return productRepo.findById(id);
    }

    public List<Product> allListProduct() throws Exception {
        return productRepo.findAll();
    }

    public Product saveProduct(Product product) throws Exception {
        return productRepo.add(product);
    }

    public Product update(Product product)  {

        try {
            return productRepo.update(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void exportAllFile() throws Exception {
        productIO.exportFile(allListProduct(), true);
    }

    public void deleteProduct(long id) throws Exception {
        productRepo.delete(id);
    }
}
