package ru.clevertec.check.runner.services.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.runner.services.ProductServices;
import ru.clevertec.check.runner.streamIO.impl.ProductIO;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DataValidation;

import java.util.List;
import java.util.Map;

@Service
public class ProductServicesImpl implements ProductServices {

    private final ProductRepositoryImpl productRepo;
    private final ProductIO productIO;
    private final DiscountCardServicesImpl cardServices;

    public ProductServicesImpl(ProductRepositoryImpl productRepo, ProductIO productIO, DiscountCardServicesImpl cardServices) {
        this.productRepo = productRepo;
        this.productIO = productIO;
        this.cardServices = cardServices;
    }

    public Product findById(long id) throws ObjectNotFoundException {
        Product product = productRepo.findById(id);
        if (product == null){
            throw new ObjectNotFoundException("Product id:"+id+" not found");
        }
       return product;
    }

    public List<Product> allListProduct() throws Exception {
        return productRepo.findAll();
    }

    public Product saveProduct(Product product) throws Exception {
        DataValidation.validator(product);
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

    public ProductDto addDescriptionInCheck(Map.Entry<Long, Integer> integerMap, Product product) {
        ProductDto productInformation =
                new ProductDto(
                        product.getName()
                        , product.getPrice()
                        , subtractPercentage(product.getDiscountPercent()
                        , Double.parseDouble(product.getPrice())));

        if (Integer.parseInt(product.getAmount()) >= integerMap.getValue()) {
            mapDescription(productInformation, product, integerMap.getValue());
           int result = Integer.parseInt(product.getAmount()) - integerMap.getValue();
            product.setAmount(String.valueOf(result));
        } else {
            mapDescription(productInformation, product, Integer.parseInt(product.getAmount()));
            product.setAmount("0");
        }
        update(product);
        return productInformation;
    }

    private void mapDescription(ProductDto productInformation, Product product, int amount) {
        productInformation.setQty(amount);
        productInformation.setTotalPriceWithDiscount(productInformation.getPriceWithDiscount() * amount);
        productInformation.setTotalPrice(Double.parseDouble(product.getPrice()) * amount);
    }

    public double totalPriceWithDiscount(List<ProductDto> productList) {
        return productList.stream().map(ProductDto::getTotalPriceWithDiscount).mapToInt(Double::intValue).sum();
    }

    public double discountСalculation(List<Product> productList, double total, Long idCard) {
        //ного нулей после точки
        if (cardServices.findById(idCard) != null) {
            total = subtractPercentage(cardServices.findById(idCard).getDiscount(), total);
        }
        if (productList
                .stream()
                .filter(product -> product.getDiscountPercent() != 0)
                .count() >= 5
        ) {
            total = subtractPercentage(10, total);
        }
        return total;
    }

    private double subtractPercentage(int percent, double price) {
        return price - (price * percent) / 100;
    }

}
