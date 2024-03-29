# Описание проекта
Модуль для работы с рейсами авиакомпании.\
Есть список сотрудников, воздушных судов и расписание рейсов.

Менеджер 
* формирует экипаж из сотрудников
* редактирует рейсы: добавляет (изменяет/удаляет) к рейсу самолет, экипаж, полетное задание
* отслеживает статус рейса

Сотрудник 
* просматривает свое расписание в личном кабинете
* получает уведомление за несколько часов до своего рейса

## Основные сценарии использования

### Редактирование рейсов 
Actor: менеджер
1. Менеджер авторизуется в системе и получает список всех рейсов.
2. Менеджер выбирает рейс для редактирования и переходит в форму с информацией о рейсе.
3. Менеджер выбирает редактирование самолета и получает список доступных самолетов (по типу и дате). После подтверждения выбранный самолет добавляется к рейсу и происходит возврат к форме с информацией о рейсе. 
4. Менеджер выбирает редактирование экипажа и получает список сотрудников, назначенных на рейс (если они уже назначены). Ограничение: для экипажа строго определен его состав (набор должностей и кол-во человек для каждой должности); сотрудник должен иметь доступ к типу выбранного самолета. Для каждой должности менеджер выбирает сотрудника из отфильтрованного списка. После подтверждения экипаж добавляется к рейсу и происходит возврат к форме с информацией о рейсе.
5. Менеджер добавляет к рейсу файл с полетным заданием
6. Менеджер завершает редактирование рейса и возвращается к списку рейсов.

### Просмотр расписания 
Actor: сотрудник
1. Сотрудник авторизуется в системе и получает список рейсов, на которые он назначен

### Рассылка уведомлений
Actor: система
1. Система за 5-8 часов до начала рейса отправляет уведомления членам экипажа этого рейса
