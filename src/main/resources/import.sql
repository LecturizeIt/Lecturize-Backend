insert into role(name) values('ADMIN'), ('USER');

INSERT INTO user(email, username, created_at, password) VALUES ('admin@admin.com', 'admin', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (1,1);
insert into user_roles(role_id, user_id) VALUES (2,1);

INSERT INTO user(email, username, created_at, password) VALUES ('abc@gmail.com', 'abc', utc_timestamp, '$2a$10$CNr3GCdNp8wLUGP/XbUqHOjkSA2josmmAHu38jQgP11g/P/Xulgoa');
insert into user_roles(role_id, user_id) VALUES (2,2);

INSERT INTO category_tag(name) VALUES ('Matemática'), ('Ciências'), ('História'), ('Geografia'), ('Língua Portuguesa'), ('Língua Estrangeira'), ('Artes'), ('Educação Física'), ('Educação Infantil'), ('Ensino Fundamental'), ('Ensino Médio'), ('Educação Superior'), ('Educação de Adultos'), ('Educação a Distância'), ('Aprendizagem Ativa'), ('Ensino Híbrido'), ('Educação Inclusiva'), ('Metodologias Ativas'), ('Gamificação'), ('Ensino Baseado em Projetos'), ('Tecnologia Educacional'), ('E-learning'), ('Ferramentas Digitais'), ('Inteligência Artificial na Educação'), ('Realidade Aumentada'), ('Plataformas de Aprendizagem'), ('Habilidades Socioemocionais'), ('Liderança'), ('Empreendedorismo'), ('Criatividade'), ('Comunicação Eficaz'), ('Trabalho em Equipe'), ('Educação Sustentável'), ('Diversidade e Inclusão'), ('Educação Financeira'), ('Saúde Mental na Educação'), ('Educação Global'), ('Workshops'), ('Palestras'), ('Seminários'), ('Conferências'), ('Feiras de Educação');

insert into lecture(status, type, created_at, ends_at, organizer_id, starts_at, address, description, lecturer, title, url) values ('SCHEDULED', 'ONLINE', utc_timestamp, '2024-09-01 23:00:00', 1, '2024-09-01 03:00:00', null, 'Palestra de teste', 'Batman da Silva', 'Teste', 'https://teste.com');

INSERT INTO lecture_tags(lecture_id, category_tag_id) VALUES (1, 1), (1, 2), (1, 3);

