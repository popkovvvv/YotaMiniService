# YotaMiniService

[![Build Status](https://travis-ci.com/popkovvvv/YotaMiniService.svg?branch=master)](https://travis-ci.com/popkovvvv/YotaMiniService)

# Сервис должен обеспечивать следующий функционал:

- Пользовательский перевод от пользователя к пользователю по номеру телефона
- Получение истории переводов по номеру телефона
- Возврат перевода
- Активация и блокировка возможности перевода
- Проверка возможности совершения перевода
- Начисление/списание баланса пользователей 

 

# Технологии для использования:

- Spring Boot
- Gradle
- Любая встраиваемая СУБД
- Не использовать ORM
- Любые другие библиотеки

# Запуск сервера

- создать бд в MySQL: "yota"
- login: root
- password 12345678
- gradle bootRun

Swagger и Docker не успел запустить.

# Логика
При переводе денег от аккаунта к аккаунту осуществляются проверки на баланс, на возможность переводить человеку, если все хорошо, то происходить перевод с созданием сузности транзакции с данными по переводу.
При возврате денежных средств по номеру телефона, осуществляется разбор сущности последней транзакции для перевода с проверкой на то что был уже возврат или нет.
Далее методы по тз.

