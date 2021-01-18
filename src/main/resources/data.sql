delete from comments;
delete from posts_topics;
delete from posts;
delete
from topics;
delete from users_stats;
delete
from users_roles;
delete
from users;

insert into users(id, email, first_name, last_name, password, image_url)
values (1, 'shakirovusa@gmail.com', 'Ruslan', 'Shakirov', '{noop}123456', 'avatars/avatar.jpg');

insert into users_roles(user_id, roles)
values (1, 'USER');

insert into users_stats(user_id) values (1);

insert into topics(id, name)
values (2, 'Frontend'),
       (3, 'Backend'),
       (4, 'Linux'),
       (5, 'Java');
insert into posts(id, body, title, author_id)
values (6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur hendrerit, ex quis sagittis pretium, velit erat lobortis tortor, non lacinia metus diam quis mi. Mauris nec iaculis urna. Pellentesque malesuada est at ante feugiat tempor. Donec arcu nunc, porttitor bibendum lacinia sit amet, commodo eget purus. Nulla lacinia lacus porta leo lobortis, vehicula eleifend dolor bibendum. Curabitur tincidunt, lorem ut efficitur rhoncus, nunc urna hendrerit sapien, in sagittis felis diam eu nisi. Phasellus varius scelerisque mattis. Suspendisse tempus a est a blandit. Praesent consequat mollis fringilla.
', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at.1', 1),
       (7, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur hendrerit, ex quis sagittis pretium, velit erat lobortis tortor, non lacinia metus diam quis mi. Mauris nec iaculis urna. Pellentesque malesuada est at ante feugiat tempor. Donec arcu nunc, porttitor bibendum lacinia sit amet, commodo eget purus. Nulla lacinia lacus porta leo lobortis, vehicula eleifend dolor bibendum. Curabitur tincidunt, lorem ut efficitur rhoncus, nunc urna hendrerit sapien, in sagittis felis diam eu nisi. Phasellus varius scelerisque mattis. Suspendisse tempus a est a blandit. Praesent consequat mollis fringilla.
', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at.2', 1),
       (8, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur hendrerit, ex quis sagittis pretium, velit erat lobortis tortor, non lacinia metus diam quis mi. Mauris nec iaculis urna. Pellentesque malesuada est at ante feugiat tempor. Donec arcu nunc, porttitor bibendum lacinia sit amet, commodo eget purus. Nulla lacinia lacus porta leo lobortis, vehicula eleifend dolor bibendum. Curabitur tincidunt, lorem ut efficitur rhoncus, nunc urna hendrerit sapien, in sagittis felis diam eu nisi. Phasellus varius scelerisque mattis. Suspendisse tempus a est a blandit. Praesent consequat mollis fringilla.
', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at.3', 1),
       (9, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur hendrerit, ex quis sagittis pretium, velit erat lobortis tortor, non lacinia metus diam quis mi. Mauris nec iaculis urna. Pellentesque malesuada est at ante feugiat tempor. Donec arcu nunc, porttitor bibendum lacinia sit amet, commodo eget purus. Nulla lacinia lacus porta leo lobortis, vehicula eleifend dolor bibendum. Curabitur tincidunt, lorem ut efficitur rhoncus, nunc urna hendrerit sapien, in sagittis felis diam eu nisi. Phasellus varius scelerisque mattis. Suspendisse tempus a est a blandit. Praesent consequat mollis fringilla.
', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at.4', 1);
insert into posts_topics(post_id, topic_id)
values (6, 2),
       (6, 3),
       (7, 5),
       (7, 2),
       (8, 5),
       (9, 4),
       (9, 5);

insert into comments(body, post_id, user_id)
values ('Круто', 6, 1),
       ('Не клево', 6, 1),
       ('Одобряю', 6, 1);



