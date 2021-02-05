
CREATE TABLE USERS (
                       id               INT  NOT NULL ,
                       name             VARCHAR(255)            NOT NULL,
                       email            VARCHAR(255)            NOT NULL,
                       password         VARCHAR(255)            NOT NULL,
                       registered       TIMESTAMP DEFAULT now() NOT NULL,
                       enabled          BOOLEAN   DEFAULT TRUE  NOT NULL,
                       calories_per_day INTEGER   DEFAULT 2000  NOT NULL
)AS SELECT * FROM CSVREAD('c:/project/topjava21_boot/users.csv');

CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE MEALS (
                       id            INT  NOT NULL ,
                       user_id     INT   NOT NULL,
                       date_time   TIMESTAMP NOT NULL,
                       description VARCHAR(255)NOT NULL,
                       calories    INT       NOT NULL
) AS SELECT * FROM CSVREAD('c:/project/topjava21_boot/meals.csv');
CREATE TABLE USER_ROLES (
                            user_id   INT   NOT NULL ,
                            role    VARCHAR
) AS SELECT * FROM CSVREAD('c:/project/topjava21_boot/role.csv');

CREATE UNIQUE INDEX meals_unique_user_datetime_idx
    ON meals (user_id, date_time)