insert into role(name) values('ADMIN'), ('USER');

INSERT INTO user(id, email, username, created_at, password) VALUES (1, 'admin@admin.com', 'admin', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');

insert into user_roles(role_id, user_id) VALUES (1,1);
insert into user_roles(role_id, user_id) VALUES (2,1);

insert into lecture(status, type, created_at, ends_at, organizer_id, starts_at, address, description, lecturer, title, url) values ('SCHEDULED', 'ONLINE', utc_timestamp, '2024-09-01 23:00:00', 1, '2024-09-01 03:00:00', null, 'vrauuuuu', 'Batman', 'Caneta Azul', 'https://vrau.com');