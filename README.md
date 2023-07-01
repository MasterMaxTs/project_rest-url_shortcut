# job4j_url_shortcut

[![Build Status](https://app.travis-ci.com/MasterMaxTs/project_rest-url_shortcut.svg?branch=master)](https://app.travis-ci.com/MasterMaxTs/project_rest-url_shortcut)

### Это проект по разработке сервиса, заменяющего URL ссылки, доступного в браузере.
#### Сервис работает через REST API.

___
### Требуемый функционал.

<br>
1. <b>Регистрация сайта.</b>

 - Сервисом могут пользоваться разные сайты;
 - каждому сайту выдается пара пароль и логин;
 - чтобы зарегистрировать сайт в систему нужно отправить запрос:

    - URL: POST /registration  с  телом JSON объекта: {site : "job4j.ru"};

 - ответ от сервера: 
    - {registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД};
    - флаг registration указывает, что регистрация выполнена или нет, то есть сайт уже есть в системе.

<br>
2. <b>Авторизация.</b>

 - Авторизацию сделать через JWT;
 - пользователь отправляет POST запрос с login и password и получает ключ.

 - Этот ключ отправляется пользователем в запросе в блоке HEAD:

    - Authorization: Bearer e25d31c5-db66-4cf2-85d4-8faa8c544ad6

<br>    
3. <b>Регистрация URL.</b>

 - Поле того, как пользователь зарегистрировал свой сайт, он может отправлять на сайт ссылки и получать преобразованные ссылки:

   - Пример:
     - отправляем URL: https://job4j.ru/profile/exercise/106/task-view/532;
     - получаем:ZRUfdD2 (ключ ZRUfD2 ассоциирован с URL).

 - Описание вызовов:
   - POST /convert с телом JSON объекта {url: "https://job4j.ru/profile/exercise/106/task-view/532"}:
     - ответ от сервера: {code: УНИКАЛЬНЫЙ_КОД};

<br>
4. <b>Переадресация.</b>

 - Выполняется без авторизации;
 - когда сайт отправляет ссылку с кодом, в ответ нужно вернуть ассоциированный адрес и статус 302.
 - Описание вызовов:
     - GET /redirect/УНИКАЛЬНЫЙ_КОД:
       - ответ от сервера в заголовке: HTTP CODE - 302 REDIRECT URL.

<br>
5. <b>Статистика.</b>

 - В сервисе считается количество вызовов каждого адреса;
 - увеличение счетчика вызовов в базе данных;
 - по сайту можно получить статистку всех адресов и количество вызовов этого адреса.
 - Описание вызовов:
     - GET /statistic:
       - ответ от сервера JSON:{ {url : URL, total : 0},
{url : "https://job4j.ru/profile/exercise/106/task-view/532", total : 103} } 
       
         
  &emsp;&emsp;&nbsp


---
### Стек технологий

- Java 11
- Spring boot v.2.7.13.
- Spring-data-jpa v.2.7.13
- Spring-security v.5.7.9
- Lombok v.1.18.28.
- Java-JWT v.4.4.0
- Liquibase-core v.4.22.0
- СУБД: PostgreSQL v.14.0.


- Тестирование:
  - Spring boot test v.2.7.13.
  - БД: h2database v.2.1.214

<br>

- Упаковка проекта: Web Archive (.war)

---
### Требования к окружению
- Java 11
- Maven v.3.6.3
- PostgreSQL v.14.0

<br>

---
### Запуск проекта
1. Установить СУБД PostgreSQL


2. Создать базу данных с именем accidents:<br>
   ```create database url_shortcuter;```


3. Скачать файлы проекта с github по ссылке и разархивировать в выбранную директорию:<br>
   [https://github.com/MasterMaxTs/project_accidents/archive](https://github.com/MasterMaxTs/project_accidents/archive/refs/heads/master.zip)


4. Перейти в директорию проекта, открыть командную строку.</br>
 - Для <ins>первого</ins> запуска приложения выполнить последовательно команды:
     - ```mvn install```
     - ```java -jar target/urlshortcut-1.0.war```

 - Для <ins>последующего</ins> запуска приложения выполнять команду:
     - ```java -jar target/urlshortcut--1.0.war```
     


---
### Закрытие проекта
Закройте окно командной строки либо:
1. Запишите id процесса (PID) в логах из командной строки:
   ![img.png](img/stop_app_pid.png)
2. Через диспетчер устройств выполните завершение процесса c этим PID


<br>

---
### Взаимодействие с приложением


---
### Контакты
* email: max86ts@gmail.com
* telegram: matsurkanov