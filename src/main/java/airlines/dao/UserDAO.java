package airlines.dao;

import airlines.entities.employees.User;

public interface UserDAO {

    // добавление
    void create(User user);

    // обновление
    void update(User user);

    // удаление
    void delete(User user);

    // поиск по login
    User findByLoginAndPassword(String login, String password);
}
