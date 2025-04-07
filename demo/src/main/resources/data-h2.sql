-- Yoga Studios
INSERT INTO studio (id, name, location) VALUES (1, 'Zen Flow Studio', 'Downtown');
INSERT INTO studio (id, name, location) VALUES (2, 'Mountain Yoga', 'Uptown');

-- Users
INSERT INTO yoga_user (id, first_name, last_name, age, email, password, gender)
VALUES (1, 'Alice', 'Smith', 30, 'alice@yoga.com', 'password123', 'FEMALE');

INSERT INTO yoga_user (id, first_name, last_name, age, email, password, gender)
VALUES (2, 'Bob', 'Johnson', 35, 'bob@yoga.com', 'securepass', 'MALE');

-- Instructors
INSERT INTO yoga_instructor (id, first_name, last_name, age, gender)
VALUES (1, 'Luna', 'Willow', 28, 'FEMALE');

INSERT INTO yoga_instructor (id, first_name, last_name, age, gender)
VALUES (2, 'Kai', 'Ocean', 40, 'MALE');

INSERT INTO yoga_style (id, class_type) VALUES (1, 'VINYASA');
INSERT INTO yoga_style (id, class_type) VALUES (2, 'PRENATAL');
INSERT INTO yoga_style (id, class_type) VALUES (3, 'HATHA');
INSERT INTO yoga_style (id, class_type) VALUES (4, 'ASHTANGA');
INSERT INTO yoga_style (id, class_type) VALUES (5, 'VINYASA');
INSERT INTO yoga_style (id, class_type) VALUES (6, 'ANUSARA');
INSERT INTO yoga_style (id, class_type) VALUES (7, 'PRENATAL');
INSERT INTO yoga_style (id, class_type) VALUES (8, 'KUNDALINI');
INSERT INTO yoga_style (id, class_type) VALUES (9, 'BIKRAM');

-- Yoga Classes
INSERT INTO yoga_class (id, name, time_and_date, price, yoga_style_id, studio_id, instructor_id)
VALUES
    (1, 'Morning Vinyasa', '2025-04-10T07:00:00', 15.0, 1, 1, 1),
    (2, 'Evening Flow', '2025-04-10T18:30:00', 20.0, 2, 2, 2);

-- Subscriptions
INSERT INTO subscription (id, price, start_date, end_date, is_active, subscription_type, studio_id, user_id)
VALUES
    (1, 50.0, '2025-04-01', '2025-04-30', true, 'STANDARD', 1, 1),
    (2, 90.0, '2025-04-01', '2025-06-30', true, 'PRO', 2, 2);

-- Bookings (Join Table)
INSERT INTO reservations (user_id, yoga_class_id)
VALUES
    (1, 1),
    (2, 2),
    (1, 2); -- Alice also booked Evening Flow
