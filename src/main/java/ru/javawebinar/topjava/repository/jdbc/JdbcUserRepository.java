package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;


    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator  = validatorFactory.getValidator();

    }

    @Override
    @Transactional
    public User save(User user)  {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.size() !=0) {
            throw new ConstraintViolationException(violations);
        }
            if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            setRoles(user, user.getRoles(), user.getRoles().size());

        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name,  email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0 & setRoles(user, user.getRoles(), user.getRoles().size() ) == null) {
            return null;
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
      //  List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
       // return DataAccessUtils.singleResult(users);
        List<User> users = jdbcTemplate.query("SELECT * FROM users  WHERE id=?", ROW_MAPPER, id);
      /*  List<Role> roles =  users.stream()
                                        .map(user -> user.getRoles().stream().findFirst().get()).collect(Collectors.toList());

       */
        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            List<Role> roles = jdbcTemplate.query("select role from user_roles where user_id=?", new RowMapper<Role>() {
                @Override
                public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                    Role role = Role.valueOf(resultSet.getObject("role", String.class));
                    return role;
                }
            }, user.getId());

            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users  WHERE email=?", ROW_MAPPER, email);

        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            List<Role> roles = jdbcTemplate.query("select role from user_roles where user_id=?", new RowMapper<Role>() {
                @Override
                public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                    return Role.valueOf(resultSet.getObject("role", String.class));
                }
            }, user.getId());

            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public List<User> getAll() {

       return
            jdbcTemplate.query("""
            select id, name, email, password,registered,enabled, calories_per_day, STRING_AGG(role, ',') as roles from users 
            left join  user_roles u on u.user_id = users.id 

            GROUP BY id, name, email, password,registered,enabled, calories_per_day 
            ORDER BY name, email 
            """,ROW_MAPPER);


    }
    @Transactional
    int[][] setRoles(User user, Set<Role> roles, int batcSize){
        jdbcTemplate.update("Delete from user_roles where user_id=?", user.getId());

        return  jdbcTemplate.batchUpdate("""
                                INSERT INTO user_roles (user_id, role)  values (?, ?)       
                        """,
                roles,
                batcSize,
                new ParameterizedPreparedStatementSetter<Role>() {
            @Override
            public void setValues(PreparedStatement preparedStatement, Role role) throws SQLException {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, role.toString());
            }

        });

    }
}
