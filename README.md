# Cash register

Программа реализующяя функционал формирования чека в магазине
## Запуск
- Собранный Cash register-1.0.war положить в папку webapps
- Запустить Tomcat
## Использование
Доступные адреса для запроса:
- POST 
  - /check/ - формирование чека.
 Параметры запроса:
     - productId-quantity={productId-quantity} - id продукта, количество
     - idCard={id} - id дисконтной карты
- POST,PUT,DELETE,GET -
  - /product/{id} - работа с продуктами
  -  /card/{id} - работа с дисконтными картами
     Возможные параметры:
      - limit={pageSize}
      - offset={page}
