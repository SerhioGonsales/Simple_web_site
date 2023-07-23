use simple_web_site_db;

insert into users(id, username, password, active)
VALUES (1, 'admin', 'admin', true);

insert into user_role(user_id, role)
VALUES (1, 'USER'), (1, 'ADMIN');