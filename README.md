# Мессенджер на сокетах джава
## Описание
Клиент-серверное приложение на языке JAVA с использованием socket, для широковещательного общения пользователей. Приложение - консольное. Клиентское приложение считывает данные из стандартного ввода и отсылает сообщение серверу (с помощью TCP/IP). Сервер, в свою очередь, накапливает сообщения и раз в 5 секунд осуществляет массовую рассылку всем клиентам. Если сообщений за указанный период не поступило, то рассылка не производится. Клиент, получивший сообщение, отображает на экране текст данного сообщения.
## Использование
1. Скопируйте этот проект
2. Запустите файл `src/com/company/Server.java`
3. Запустите один или несколько раз файл `src/com/company/Client.java`
4. Введите имя пользователя в каждой из консолей клиентского файла
5. Пишите сообщения
## Пример
![Иллюстрация к проекту](https://github.com/hydroponic/sockets-messenger/blob/main/template.png)
