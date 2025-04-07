insert into role(name) values('ADMIN'), ('USER');

INSERT INTO user(email, username, created_at, password) VALUES ('admin@admin.com', 'admin', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (1,1);
insert into user_roles(role_id, user_id) VALUES (2,1);

INSERT INTO user(email, username, created_at, password) VALUES ('abc@gmail.com', 'abc', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (2,2);

INSERT INTO user(email, username, created_at, password) VALUES ('criaddt@gmail.com', 'criaddt', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (2,3);

INSERT INTO `refresh_token` (`created_at`, `expires_at`, `user_id`, `id`) VALUES (utc_timestamp, DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 MONTH), 1, unhex(replace(uuid(),'-','')));
INSERT INTO `refresh_token` (`created_at`, `expires_at`, `user_id`, `id`) VALUES (utc_timestamp, DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 MONTH), 2, unhex(replace(uuid(),'-','')));
INSERT INTO `refresh_token` (`created_at`, `expires_at`, `user_id`, `id`) VALUES (utc_timestamp, DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 MONTH), 3, unhex(replace(uuid(),'-','')));

INSERT INTO category_tag(name) VALUES ('Matemática'), ('Ciências'), ('História'), ('Geografia'), ('Língua Portuguesa'), ('Língua Estrangeira'), ('Artes'), ('Educação Física'), ('Educação Infantil'), ('Ensino Fundamental'), ('Ensino Médio'), ('Educação Superior'), ('Educação de Adultos'), ('Educação a Distância'), ('Aprendizagem Ativa'), ('Ensino Híbrido'), ('Educação Inclusiva'), ('Metodologias Ativas'), ('Gamificação'), ('Ensino Baseado em Projetos'), ('Tecnologia Educacional'), ('E-learning'), ('Ferramentas Digitais'), ('Inteligência Artificial na Educação'), ('Realidade Aumentada'), ('Plataformas de Aprendizagem'), ('Habilidades Socioemocionais'), ('Liderança'), ('Empreendedorismo'), ('Criatividade'), ('Comunicação Eficaz'), ('Trabalho em Equipe'), ('Educação Sustentável'), ('Diversidade e Inclusão'), ('Educação Financeira'), ('Saúde Mental na Educação'), ('Educação Global'), ('Workshops'), ('Palestras'), ('Seminários'), ('Conferências'), ('Feiras de Educação');

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('COMPLETED', 'ONLINE', utc_timestamp, '2024-09-01 03:00:00', '2024-09-01 23:00:00', 1, null, 'Palestra Finalizada', 'Wang da Silva', 'Mais recente', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (5, 5, 1);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'ONLINE', '2024-10-15 12:00:00', '2024-10-15 11:00:00', '2024-10-15 11:41:00', 1, null, 'Palestra Agendada', 'Batman da Silva', 'Mais visitado', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 1000, 2);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('IN_PROGRESS', 'ONLINE', '2024-10-15 11:50:00', '2024-09-01 03:00:00', '2024-09-01 23:00:00', 1, null, 'Palestra em Andamento', 'Jonkler da Silva', 'Mais compartilhado', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (350, 20, 3);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('CANCELED', 'ONLINE', '2024-10-15 11:00:00', '2024-09-01 03:00:00', '2024-09-01 23:00:00', 1, null, 'Palestra Cancelada', 'NickDusk da Silva', 'Palestra de inglês', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (15, 15, 4);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'ONLINE', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, null, 'Email Online', 'Sunny da Silva', 'Palestra de física', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (11, 23, 5);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'PRESENTIAL', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Presencial', 'Taylor da Silva', 'Palestra de artes', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (53, 100, 6);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Julien da Silva', 'Comunicação Eficiente em Idiomas Latinos', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 7);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url)values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Dominando a Arte da Conversação', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 8);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Como Aprender um Novo Idioma com Consistência', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 9);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Do Zero à Fluência: Estratégias Reais', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 10);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Linguagem e Cultura: Uma Jornada Conectada', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 11);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Aprendizado Natural de Idiomas no Dia a Dia', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 12);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Criando Hábitos para Aprender Qualquer Idioma', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 13);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Entendendo a Linguagem Corporal nas Conversas', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 14);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Como Ler Textos em Outro Idioma com Segurança', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 15);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Audição Ativa: Compreendendo Sem Traduzir', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 16);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Prática de Diálogo: Superando a Timidez', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 17);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'A Importância do Ritmo na Comunicação Oral', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 18);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Entonação e Emoção ao Falar', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 19);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Construindo Vocabulário de Forma Criativa', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 20);

insert into lecture(status, type, created_at, starts_at, ends_at, organizer_id, address, description, lecturer, title, url) values ('SCHEDULED', 'HYBRID', '2024-10-15 11:00:00', '2024-11-01 03:00:00', '2024-11-06 23:00:00', 1, 'rua pingola da silva', 'Email Hybrid', 'Shang da Silva', 'Oficina de Interpretação de Texto', 'https://teste.com');
insert into metrics(times_shared, times_visited, lecture_id) VALUES (10, 10, 21);

INSERT INTO lecture_tags(lecture_id, category_tag_id) VALUES (1, 1), (1, 2), (1, 5), (1,6), (1,7), (1,8), (2,1), (2,4);
