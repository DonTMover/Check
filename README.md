# Check
Стек:Java 21, Gradle 8.5+, PostgreSQL 16+
Инструкция по запуску: 
```
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity id-quantity discountCard=XXXX balanceDebitCard=Number datasource.url=ххх datasource.username=ххх datasource.password=ххх saveToFile=xxxx
```
## balanceDebitCard
Баланс может быть ><= 0 (больше,меньше,равен)  
Его требуется указать, без него приложение закроется с ошибкой  
## discountCard
Скидочную карту можно не указывать, любая скидочная карта кроме* некоторых имеет 2% скидки  
* Некоторые имеется ввиду от списка которые можно посмотреть [здесь](https://github.com/DonTMover/Check/blob/entry-core/src/main/resources/discountCards.csv)

[//]: # (## pathToFile)

[//]: # (В этом отвлетвлении от основного кода добавлена поддержка сторонних csv файлов  )

[//]: # (Если данный агрумент не будет указан -> ```throw new BadRequestException```)

[//]: # (* Вам требуется его указать, в нем должна находиться бд содержащщая продукты типо [такого]&#40;https://github.com/DonTMover/Check/blob/entry-file/src/main/resources/products.csv&#41;  )

[//]: # (* **Нужно указывать относительный путь**)

## datasource.url

* Требуется указать, иначе Exception.
* Вид: `jdbc:postgresql://example.com:port/Example`
### Разбор вида:
`example.com` - ссылка на сервер, на котором располагается База Данных  
`port` - порт по которому работает DataBase, стандартный 5432  
`Example` - это название Базы данных


## datasource.username

* Требуется указать, иначе Exception
* Вид: `datasource.username=postgresql`, где `postgresql` нужно указать имя пользователя

## datasource.password

* Требуется указать, иначе Exception
* Вид `datasource.password=postgresql`, где `postgresql` нужно указать пароль

## saveToFile
Вместе с `pathToFile` так-же добавлен `saveToFile`, если его не указать, будет тоже самое исключение
* **Нужно указывать относительный путь**



## Post Scriptum
* Не совветую пользоватся этим ответвлением из-за [CVE-2024-1597](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2024-1597), у которого Score: 10


[//]: # (## Другие случаи)

[//]: # (* Если аргумент `pathToFile` и `saveToFile` не были переданы, ошибка будет в `result.csv`)

[//]: # (* Если аргумент `pathToFile` не был передан, но был передан `saveToFile`, то ошибка будет в `saveToFile`)

[//]: # (* Если не передан `saveToFile`, то ошибка будет в `result.csv`)