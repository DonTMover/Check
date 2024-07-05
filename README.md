# Check
Стек:Java 21, Gradle 8.5+
Инструкция по запуску: 
```
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity id-quantity discountCard=XXXX balanceDebitCard=Number
```
## balanceDebitCard
Баланс может быть ><= 0 (больше,меньше,равен)
Его требуется указать, без него приложение закроется с ошибкой
## discountCard
Скидочную карту можно не указывать, любая скидочная карта кроме* некоторых имеет 2% скидки
* Некоторые имеется ввиду от списка которые можно посмотреть [здесь](https://github.com/DonTMover/Check/blob/entry-core/src/main/resources/discountCards.csv)