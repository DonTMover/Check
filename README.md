# Check
Стек:Java 21, Gradle 8.5+
Инструкция по запуску: 
```
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity id-quantity discountCard=XXXX balanceDebitCard=Number pathToFile=xxxx saveToFile=xxxx
```
## balanceDebitCard
Баланс может быть ><= 0 (больше,меньше,равен)  
Его требуется указать, без него приложение закроется с ошибкой  
## discountCard
Скидочную карту можно не указывать, любая скидочная карта кроме* некоторых имеет 2% скидки  
* Некоторые имеется ввиду от списка которые можно посмотреть [здесь](https://github.com/DonTMover/Check/blob/entry-core/src/main/resources/discountCards.csv)
## pathToFile
В этом отвлетвлении от основного кода добавлена поддержка сторонних csv файлов  
Если данный агрумент не будет указан -> ```throw new BadRequestException```
* Вам требуется его указать, в нем должна находиться бд содержащщая продукты типо [такого](https://github.com/DonTMover/Check/blob/entry-file/src/main/resources/products.csv)  
* **Нужно указывать относительный путь**
## saveToFile
Вместе с `pathToFile` так-же добавлен `saveToFile`, если его не указать, будет тоже самое исключение
* **Нужно указывать относительный путь**

## Другие случаи
* Если аргумент `pathToFile` и `saveToFile` не были переданы, ошибка будет в `result.csv`
* Если аргумент `pathToFile` не был передан, но был передан `saveToFile`, то ошибка будет в `saveToFile`
* Если не передан `saveToFile`, то ошибка будет в `result.csv`