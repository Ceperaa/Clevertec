package ru.clevertec.check.runner.services.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.streamIO.impl.ProductInformationIO;

import java.util.List;
import java.util.Map;

@Service
public class ProductInformationService {

    private final RepositoryEntity<ProductInformation> productInformationRepo;
    private final ProductServicesImpl productServices;
    private final DiscountCardServicesImpl cardServices;
    private final ProductInformationIO productInformationIO;


    public ProductInformationService(
            RepositoryEntity<ProductInformation> productInformationRepo
            , ProductServicesImpl productServices
            , DiscountCardServicesImpl cardServices
            , ProductInformationIO productInformationIO
    ) {
        this.productInformationRepo = productInformationRepo;
        this.productServices = productServices;
        this.cardServices = cardServices;
        this.productInformationIO = productInformationIO;
    }

  public void exportFile(List<ProductInformation> list) throws Exception {
      productInformationIO.exportFile(list, false);
  }

    public int totalPriceWithDiscount(List<ProductInformation> productList) {
        return productList.stream().map(ProductInformation::getTotalPriceWithDiscount).mapToInt(x -> x).sum();
    }

    public int discountСalculation(List<Product> productList, int total, Long idCard) {
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

    public ProductInformation addDescriptionInCheck(Map.Entry<Long, Integer> integerMap, Product product) {
        ProductInformation productInformation =
                new ProductInformation(
                        product.getName()
                        , product.getPrice()
                        , subtractPercentage(product.getDiscountPercent()
                        , product.getPrice()));

        if (product.getAmount() >= integerMap.getValue()) {
            mapDescription(productInformation, product, integerMap.getValue());
            product.setAmount(product.getAmount() - integerMap.getValue());
        } else {
            mapDescription(productInformation, product, product.getAmount());
            product.setAmount(0);
        }
        productServices.update(product);
        return productInformation;
    }

    private void mapDescription(ProductInformation productInformation, Product product, int amount) {
        productInformation.setQty(amount);
        productInformation.setTotalPriceWithDiscount(productInformation.getPriceWithDiscount() * amount);
        productInformation.setTotalPrice(product.getPrice() * amount);
    }

    private int subtractPercentage(int percent, int price) {
        return price - (price * percent) / 100;
    }

}
