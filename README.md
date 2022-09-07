# Cash register

Программа реализующяя функционал формирования чека в магазине
## Использование
Доступные адреса для запроса:
 - формирование чека:
```sh
POST/check?productId-quantity={productId-quantity}&idCard={id}
```
 - удаление чеков старше недели
```sh
DELETE/check
```
  - работа с продуктами
 > Product:
    - id
    - name
     - amount
     - discountPercent
     - price
```sh
POST/product
```
```sh
GET/product/{id}
```
```sh
GET/product?limit={pageSize}&offset={page}
```
```sh
PUT/product
```
```sh
DELETE/product/{id}
```
 - продукт который чаще всего продавался
```sh
GET/product/maxSale
```
  - работа с дисконтными картами
  >DiscountCard
     - id
     - discount
 ```sh
POST/card
```
```sh
GET/card/{id}
```
```sh
GET/card?limit={pageSize}&offset={page}
```
```sh
PUT/card
```
```sh
DELETE/card/{id}
```  
