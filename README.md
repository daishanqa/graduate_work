
## План автоматизации тестирования приложения:

### Перечень автоматизируемых сценариев:

 1. Покупка тура с обычной оплатой по дебетовой карте с одобрением оплаты.
 2. Покупка тура с выдачей кредита по данным банковской карты с одобрением кредита.
 3. Попытка покупки тура с обычной оплатой по дебетовой карте с отказом оплаты.
 4. Попытка покупки тура с выдачей кредита по данным банковской карты с отказом кредита.
 5. Попытка покупки тура с не заполненными полями формы ввода данных.
 6. Попытка покупки тура с введёнными некорректными данными для полей:
    * "Номер карты"
    * "Год"
    * "Месяц"
    * "Владелец"
    * "CVV"


### Перечень используемых инструментов с обоснованием выбора:

* Язык программирования: Java (для автоматизации Java-приложения).
* Фреймворк для автоматизации тестирования: Selenid (для взаимодействия с веб-сервисом).
* Система сборки: Gradle (для управления зависимостями и сборкой проекта).
* Библиотеки для работы с REST API (для взаимодействия с эмулятором банковских сервисов).
* Docker (для запуска контейнеров с СУБД и эмулятором банковских сервисов).

### Перечень и описание возможных рисков при автоматизации:

1. Изменения в структуре веб-сервиса или СУБД могут привести к несоответствию автотестов.
2. Невозможность эмулировать все возможные сценарии банковских сервисов.
3. Сложность настройки окружения с запуском контейнеров и настройкой СУБД.
4. Ошибки в настройках эмулятора банковских сервисов, что может влиять на результаты тестов.

### Интервальная оценка с учётом рисков в часах:

* Разработка автотестов: от 48 до 72 часов.
* Настройка окружения и запуск контейнеров: то 10 до 12 часов.
* Разработка сценариев для эмулятора банковских сервисов: 5 часов.
* Написание отчетов и документации: 10 часов.
* Итого:  от 73 до 89 часов.

### План сдачи работ:

* Готовность автотестов: ориентировочно 11.10.2023.
* Результаты прогона тестов: ориентировочно 14.10.2023.
* Отчеты и документация:ориентировочно 16.10.2023.
