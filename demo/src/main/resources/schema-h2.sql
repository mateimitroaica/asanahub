CREATE TABLE studio (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255),
                        location VARCHAR(255)
);

CREATE TABLE yoga_user (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(100),
                           last_name VARCHAR(100),
                           age INT,
                           email VARCHAR(255),
                           password VARCHAR(255),
                           gender VARCHAR(10)
);

CREATE TABLE yoga_instructor (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 first_name VARCHAR(100),
                                 last_name VARCHAR(100),
                                 age INT,
                                 gender VARCHAR(10)
);

CREATE TABLE yoga_style (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            class_type VARCHAR(50)
);

CREATE TABLE yoga_class (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255),
                            time_and_date TIMESTAMP,
                            price DOUBLE,
                            yoga_style_id BIGINT,
                            studio_id BIGINT,
                            instructor_id BIGINT,
                            FOREIGN KEY (yoga_style_id) REFERENCES yoga_style(id),
                            FOREIGN KEY (studio_id) REFERENCES studio(id),
                            FOREIGN KEY (instructor_id) REFERENCES yoga_instructor(id)
);

CREATE TABLE subscription (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              price DOUBLE,
                              start_date DATE,
                              end_date DATE,
                              is_active BOOLEAN,
                              subscription_type VARCHAR(50),
                              studio_id BIGINT,
                              user_id BIGINT,
                              FOREIGN KEY (studio_id) REFERENCES studio(id),
                              FOREIGN KEY (user_id) REFERENCES yoga_user(id)
);

CREATE TABLE reservations (
                              user_id BIGINT,
                              yoga_class_id BIGINT,
                              PRIMARY KEY (user_id, yoga_class_id),
                              FOREIGN KEY (user_id) REFERENCES yoga_user(id),
                              FOREIGN KEY (yoga_class_id) REFERENCES yoga_class(id)
);

ALTER TABLE yoga_class ALTER COLUMN id RESTART WITH 100;
ALTER TABLE yoga_style ALTER COLUMN id RESTART WITH 100;
ALTER TABLE studio ALTER COLUMN id RESTART WITH 100;
ALTER TABLE yoga_user ALTER COLUMN id RESTART WITH 100;
ALTER TABLE subscription ALTER COLUMN id RESTART WITH 100;
ALTER TABLE yoga_instructor ALTER COLUMN id RESTART WITH 100;