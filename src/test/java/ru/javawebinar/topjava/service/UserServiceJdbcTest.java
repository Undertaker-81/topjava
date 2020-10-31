package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

/**
 * @author Dmitriy Panfilov
 * 31.10.2020
 */
@ActiveProfiles({"jdbc","postgres"})
public class UserServiceJdbcTest extends UserServiceTest{
}
