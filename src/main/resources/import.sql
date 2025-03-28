insert into role(name) values('ADMIN'), ('USER');

INSERT INTO user(email, username, created_at, password) VALUES ('admin@admin.com', 'admin', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (1,1);
insert into user_roles(role_id, user_id) VALUES (2,1);

INSERT INTO user(email, username, created_at, password) VALUES ('abc@gmail.com', 'abc', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (2,2);

INSERT INTO user(email, username, created_at, password) VALUES ('criaddt@gmail.com', 'criaddt', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (2,3);

INSERT INTO category_tag(name) VALUES ('Matemática'), ('Ciências'), ('História'), ('Geografia'), ('Língua Portuguesa'), ('Língua Estrangeira'), ('Artes'), ('Educação Física'), ('Educação Infantil'), ('Ensino Fundamental'), ('Ensino Médio'), ('Educação Superior'), ('Educação de Adultos'), ('Educação a Distância'), ('Aprendizagem Ativa'), ('Ensino Híbrido'), ('Educação Inclusiva'), ('Metodologias Ativas'), ('Gamificação'), ('Ensino Baseado em Projetos'), ('Tecnologia Educacional'), ('E-learning'), ('Ferramentas Digitais'), ('Inteligência Artificial na Educação'), ('Realidade Aumentada'), ('Plataformas de Aprendizagem'), ('Habilidades Socioemocionais'), ('Liderança'), ('Empreendedorismo'), ('Criatividade'), ('Comunicação Eficaz'), ('Trabalho em Equipe'), ('Educação Sustentável'), ('Diversidade e Inclusão'), ('Educação Financeira'), ('Saúde Mental na Educação'), ('Educação Global'), ('Workshops'), ('Palestras'), ('Seminários'), ('Conferências'), ('Feiras de Educação');

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'ONLINE', utc_timestamp, '2024-10-15 11:00:00', '2024-10-15 11:41:00', 1, null, 'Palestra Agendada', 'Batman da Silva', 'Palestra de matemática', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 1);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('IN_PROGRESS', 'ONLINE', utc_timestamp, '2024-09-01 03:00:00', '2024-09-01 23:00:00', 1, null, 'Palestra em Andamento', 'Batman da Silva', 'Palestra de tecnologia', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (20, 20, 2);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('COMPLETED', 'ONLINE', utc_timestamp, '2024-09-01 03:00:00', '2024-09-01 23:00:00', 1, null, 'Palestra Finalizada', 'Batman da Silva', 'Palestra de geografia', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (5, 5, 3);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('CANCELED', 'ONLINE', utc_timestamp, '2024-09-01 03:00:00', '2024-09-01 23:00:00', 1, null, 'Palestra Cancelada', 'Batman da Silva', 'Palestra de inglês', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (15, 15, 4);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'ONLINE', utc_timestamp, '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, null, 'Email Online', 'Batman da Silva', 'Palestra de física', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (11, 23, 5);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'PRESENTIAL', utc_timestamp, '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Presencial', 'Batman da Silva', 'Palestra de artes', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (53, 100, 6);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', utc_timestamp, '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Batman da Silva', 'Palestra de espanhol', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 7);

INSERT INTO lecture_tags(lecture_id, category_tag_id) VALUES (1, 1), (1, 2), (1, 3);

