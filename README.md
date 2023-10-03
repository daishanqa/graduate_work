# Дипломный проект по профессии «Тестировщик»

## Документация
* [Задание](https://github.com/netology-code/qa-diploma)
* [План автоматизации](https://github.com/daishanqa/graduate_work/blob/main/Dokuments/Plan.md)
* [Отчёт о проведённом тестировании](https://github.com/daishanqa/graduate_work/blob/main/Dokuments/Report.md)
* [Отчёт о проведённой автоматизации](https://github.com/daishanqa/graduate_work/blob/main/Dokuments/Summary.md)

## Перечень необходимых инструментов 
1. IntelliJ IDEA 2022.2.1 (Community Edition)
2. Chrome Версия 117.0.5938.92 (Официальная сборка), (64 бит)
3. Docker Desktop 4.22.0 
4. DBeaver 23.2.1.
## Инструкция для запуска автотестов
1. Клонировать [проект](https://github.com/daishanqa/graduate_work)
2. Открыть проект в IntelliJ IDEA
3. Запустить Docker Desktop 
4. Выполнить команду `docker-compose up --build` в терминале IntelliJ IDEA  
### Подключение SUT к MySQL
1. В терминале 2 запустить приложение: ` java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar `
2. Проверить запуск приложение в браузере Chrome:`http://localhost:8080`
3. В терминале 3 запустить тесты: `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"` 
4. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
5. Закрыть отчёт в терминале 3: `CTRL + C` -> `y` -> `Enter`
6. Остановить приложение в терминале 2: `CTRL + C`
7. Остановить контейнеры в терминале 4:`docker-compose down`
### Подключение SUT к PostgreSQL
1. В терминале 2 запустить приложение: `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
2. Проверить запуск приложение в браузере Chrome:`http://localhost:8080`
3. В терминале 3 запустить тесты: `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
4. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
5. Закрыть отчёт в терминале 3: `CTRL + C` -> `y` -> `Enter`
6. Остановить приложение в терминале 2: `CTRL + C`
7. Остановить контейнеры в терминале 4:`docker-compose down`
