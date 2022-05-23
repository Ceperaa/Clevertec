package ru.clevertec.check.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.DiscountCardRepository;
import ru.clevertec.check.runner.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CheckRunner {

    private final ProductRepository productRepository;
    private final DiscountCardRepository discountCardRepository;

    @Autowired
    public CheckRunner(ProductRepository productRepository, DiscountCardRepository discountCardRepository) {
        this.productRepository = productRepository;
        this.discountCardRepository = discountCardRepository;
    }

    //набор параметров в формате itemId-quantity (itemId - идентификатор товара, quantity - его количество
    //Например: java CheckRunner 3-1 2-5 5-1 card-1234
    public Check run(Map<Long, Integer> itemIdQuantity, int idCard) {

        Check check = null;

        List<Product> productList = new ArrayList<>();
        for (Map.Entry<Long, Integer> product : itemIdQuantity.entrySet()) {
            Product productForCheck = productRepository.get(product.getKey());

            productForCheck.setCount(productForCheck.getCount() - product.getValue());
            productForCheck.setTotalPriceForEverything(productForCheck.getPrice() * product.getValue());
            productList.add(productForCheck);
        }
        check.setProductList(productList);
        check.setTotalPrice(totalPrice(productList));
        check.setTotalPriceWithDiscount(isDiscount(productList, totalPriceWithDiscount(productList),idCard));
        return check;
    }

    private int totalPrice(List<Product> productList) {
        return productList.stream().map(Product::getTotalPriceForEverything).mapToInt(x -> x).sum();
    }

    private int totalPriceWithDiscount(List<Product> productList) {
        return productList
                .stream()
                .map(product -> subtractPercentage(product.getDiscountPercent(), product.getPrice()))
                .mapToInt(x -> x)
                .sum();
    }

  //  Если их в чеке больше пяти, то сделать скидку 10% по этой позиции. Данную информацию отразить в чеке.
    private int isDiscount(List<Product> productList, int total,int idCard) {
        if (productList
                .stream()
                .filter(product -> product.getDiscountPercent() != 0)
                .count() >= 5
        ) {
            total = subtractPercentage(10, total) -
                    Optional.of(discountCardRepository.get(idCard).getDiscount()).orElse(0);
        }
        return total;
    }

    private int subtractPercentage(int percent, int price) {
        return price - (price * percent) / 100;
    }
}
/*
* Разработать консольное приложение, реализующее функционал формирования чека в магазине.

Приложение запускается java RunnerClassName <набор_параметров>, где набор параметров в формате itemId-quantity (itemId - идентификатор товара, quantity - его количество.
Например: java CheckRunner 3-1 2-5 5-1 card-1234 должен сформировать и вывести в консоль чек содержащий в себе наименование товара с id=3 в количестве 1шт, то же самое с id=2 в
*  количестве 5 штук, id=5 - одна штука и т. д.
*  Card-1234 означает, что была предъявлена скидочная карта с номером 1234. Необходимо вывести в консоль сформированный чек
*  (вариант на рисунке), содержащий в себе список товаров и их количество с ценой, а также рассчитанную сумму с учетом скидки по предъявленной карте (если она есть).

Среди товаров, предусмотреть акционные. Если их в чеке больше пяти, то сделать скидку 10% по этой позиции. Данную информацию отразить в чеке.
Набор товаров и скидочных карт может задаваться прямо в коде, массивом или коллекцией объектов. их количество и номенклатура имеет тестовый характер, поэтому наименование и
*  количество свободные.
*
*
Готовое задание можно передать zip архивом с исходным кодом (набор .java файлов), которые можно скомпилировать при помощи javac и полученный .class файл запустить через java.
*
*  Можно использовать сборщики (gradle, maven, ant и пр.). В любом случае, лучше приложить инструкцию по запуску вашего приложения. Кроме того, кодовую базу можно разместить в
*  любом из публичных репозиториев (Bitbucket, github, gitlab).
*
В данном задание важно показать понимание ООП, умение строить модели (выделять классы, интерфейсы, их связи), разделять функционал между ними  а также знать синтаксис самого языка.
*  Обратить внимание на устойчивость к изменениям в логике и избегать копипаста. Только после этого можно перейти к необязательным заданиям отмеченных *.
* Организовать чтение исходных данных (товары и скидочные карты) из файлов (в таком случае, можно передавать имя файла в набор параметров команды java)/.
* Реализовать вывод чека в файл.
* Реализовать обработку исключений (например, товара с id или файла не существует  и т. д.).
* Покрыть функционал тестами
* * Реализовать web - интерфейс (например, получать чек по GET http://localhost/check?itemId=1&itemId=1). UI не обязателен.
*** Расширить функционал на свое усмотрение.
* */