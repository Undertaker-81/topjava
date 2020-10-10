package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    {
        User user1 = new User(counter.incrementAndGet(),"User1", "user1@mail.ru", "qwerty", Role.USER, Role.values());
        User user2 = new User(counter.incrementAndGet(),"User2", "user2@mail.ru", "qwerty", Role.USER, Role.values());
        User admin = new User(counter.incrementAndGet(),"Admin", "adm@inmail.ru", "qwerty", Role.ADMIN, Role.values());
        repository.put(user1.getId(),user1);
        repository.put(user2.getId(),user2);
        repository.put(admin.getId(),admin);
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)){
            log.info("delete {}", id);
            return repository.remove(id, repository.get(id));
        }
        return false;

    }

    @Override
    public User save(User user) {
        if (user != null){
            log.info("save {}", user);
            return repository.put(counter.incrementAndGet(), user);
        }
        return null;
    }

    @Override
    public User get(int id) {
        if (repository.containsKey(id)){
            log.info("get {}", id);
            return repository.get(id);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        if (!repository.isEmpty()){
            log.info("getAll");
           return new ArrayList<>(repository.values())
                                                    .stream()
                                                    .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                                                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        if (email != null){
            log.info("getByEmail {}", email);
            return repository.values().stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst()
                    .get();
        }
        return null;
    }
}
