package ru.clevertec.check.runner.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.DiscountCardServices;
import ru.clevertec.check.runner.services.ProductServices;
import ru.clevertec.check.runner.streamIO.impl.ProductIO;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;
import ru.clevertec.check.runner.util.validation.DoubleFormatting;

import java.util.List;
import java.util.Map;

@Service
public class ProductServicesImpl implements ProductServices {

    private final RepositoryEntity<Product> productRepo;
    private final ProductIO productIO;
    private final DiscountCardServices cardServices;
    private final ModelMapper modelMapper;
    private final RepositoryEntity<ProductInformation> informationRepositoryEntity;

    public ProductServicesImpl(
            RepositoryEntity<Product> productRepo
            , ProductIO productIO
            , DiscountCardServices cardServices
            , ModelMapper modelMapper, RepositoryEntity<ProductInformation> informationRepositoryEntity) {
        this.productRepo = productRepo;
        this.productIO = productIO;
        this.cardServices = cardServices;
        this.modelMapper = modelMapper;
        this.informationRepositoryEntity = informationRepositoryEntity;
    }

    public Product findById(long id) throws ObjectNotFoundException {
        Product product = productRepo.findById(id);
        if (product == null) {
            throw new ObjectNotFoundException("Product id:" + id + " not found");
        }
        return product;
    }

    public List<Product> allListProduct() throws Exception {
        return productRepo.findAll();
    }

    public Product saveProduct(Product product) throws Exception {
        return productRepo.add(product);
    }

    public Product update(Product product) {

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

    public ProductDto addDescriptionInCheck(Map.Entry<Long, Integer> integerMap, Product product) throws Exception {
        ProductInformation productInformation =
                new ProductInformation(
                        subtractPercentage(product.getDiscountPercent()
                                , Double.parseDouble(product.getPrice())), product.getDiscountPercent()
                );

        if (Integer.parseInt(product.getAmount()) >= integerMap.getValue()) {
            mapDescription(productInformation, product, integerMap.getValue());
            int result = Integer.parseInt(product.getAmount()) - integerMap.getValue();
            product.setAmount(String.valueOf(result));
        } else {
            mapDescription(productInformation, product, Integer.parseInt(product.getAmount()));
            product.setAmount("0");
        }
        update(product);
        informationRepositoryEntity.add(productInformation);
        ProductDto productDto = modelMapper.map(productInformation, ProductDto.class);
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        return productDto;
    }

    private void mapDescription(ProductInformation productInformation, Product product, int amount) {
        productInformation.setTotalPriceWithDiscount(DoubleFormatting.formatting(productInformation.getPriceWithDiscount() * amount));
        productInformation.setTotalPrice(Double.parseDouble(product.getPrice()) * amount);
    }

    public double totalPriceWithDiscount(List<ProductDto> productList) {
        return DoubleFormatting.formatting(productList.stream().map(ProductDto::getTotalPriceWithDiscount).mapToDouble(Double::doubleValue).sum());
    }

    @Override
    public double discountСalculation(List<Product> productList, double total, Long idCard) {
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
        return DoubleFormatting.formatting(total);
    }

    private double subtractPercentage(int percent, double price) {
        return DoubleFormatting.formatting(price - (price * percent) / 100.00);
    }
}
