drop table if exists users_stats;
drop table if exists users_roles;
drop table if exists users_subscription_authors;
drop table if exists posts_topics;
drop table if exists comments;
drop table if exists votes;
drop table if exists post_bookmarks;
drop table if exists posts;
drop table if exists topics;
drop table if exists users;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start 20 increment 1;

create table users
(
    id         bigint       not null default nextval('hibernate_sequence'),
    created_at timestamp    not null default now(),
    email      varchar(255) not null,
    enabled    boolean               default true,
    image_url  varchar,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    updated_at timestamp             default now(),
    primary key (id)
);

create unique index users_email_uq_idx on users (email);

create table comments
(
    id         bigint       not null default nextval('hibernate_sequence'),
    body       varchar(255) not null,
    created_at timestamp    not null default now(),
    updated_at timestamp             default now(),
    post_id    bigint       not null,
    user_id    bigint       not null,
    primary key (id),
    unique (body, post_id, user_id)
);

create table users_stats
(
    comment_count      int    default 0,
    post_count         int    default 0,
    bookmark_count     int    default 0,
    subscription_count int    default 0,
    subscribed_count   int    default 0,
    user_id            bigint not null,
    primary key (user_id),
    foreign key (user_id) references users (id) on delete cascade
);

create table users_subscription_authors
(
    id        bigint not null,
    user_id   bigint not null,
    author_id bigint not null,
    primary key (id)
);

create table posts
(
    id             bigint        not null default nextval('hibernate_sequence'),
    body           varchar(2048) not null,
    created_at     timestamp     not null default now(),
    published      boolean                default true,
    title          varchar(255)  not null,
    updated_at     timestamp              default now(),
    vote_count     int                    default 0,
    comment_count  int                    default 0,
    bookmark_count int                    default 0,
    author_id      bigint        not null,
    primary key (id)
);

create unique index posts_title_uq_idx on posts (title);

create table posts_topics
(
    post_id  bigint not null,
    topic_id bigint not null
);

create table topics
(
    id         bigint       not null default nextval('hibernate_sequence'),
    name       varchar(255) not null,
    post_count int                   default 0,
    primary key (id),
    unique (name)
);

create table users_roles
(
    user_id bigint not null,
    roles   varchar(255)
);

create table votes
(
    id      bigint not null default nextval('hibernate_sequence'),
    value   boolean         default true,
    post_id bigint not null,
    user_id bigint not null,
    primary key (id)
);

create table post_bookmarks
(
    id         bigint    default nextval('hibernate_sequence'),
    created_at timestamp default now(),
    updated_at timestamp default now(),
    user_id    bigint not null,
    post_id    bigint not null,
    primary key (id),
    unique (user_id, post_id),
    foreign key (post_id) references posts (id) on delete cascade,
    foreign key (user_id) references users (id) on delete cascade
);

alter table if exists comments
    add constraint comments_post_fk foreign key (post_id) references posts on
        delete cascade;
alter table if exists comments
    add constraint comments_user_fk foreign key (user_id) references users on
        delete cascade;
alter table if exists users_subscription_authors
    add constraint users_authors_user_fk foreign key (user_id) references users on delete cascade;
alter table if exists users_subscription_authors
    add constraint users_authors_author_fk foreign key (author_id) references users on delete cascade;
alter table if exists posts
    add constraint posts_author_fk foreign key (author_id) references users on delete cascade;
alter table if exists posts_topics
    add constraint posts_topics_topic_fk foreign key (topic_id) references topics on delete cascade;
alter table if exists posts_topics
    add constraint posts_topics_post_fk foreign key (post_id) references posts on delete cascade;
alter table if exists users_roles
    add constraint user_roles_user_fk foreign key (user_id) references users on delete cascade;
alter table if exists votes
    add constraint votes_post_fk foreign key (post_id) references posts on
        delete cascade;
alter table if exists votes
    add constraint votes_user_fk foreign key (user_id) references users;

create or replace function changeVoteCount()
    returns trigger as
$x$
begin
    if new.value then
        update posts set vote_count = vote_count + 1 where id = new.post_id;
    else
        update posts set vote_count = vote_count - 1 where id = new.post_id;
    end if;
    return new;
end;
$x$
    language plpgsql;

create trigger after_insert_vote_votes
    after insert
    on votes
    for each row
execute procedure changeVoteCount();

create or replace function incrementCommentCount()
    returns trigger as
$x$
begin
    update posts set comment_count = comment_count + 1 where id = new.post_id;
    update users_stats set comment_count = comment_count + 1 where user_id = new.user_id;
    return new;
end;
$x$
    language plpgsql;

create trigger after_insert_comment_comments
    after insert
    on comments
    for each row
execute procedure incrementCommentCount();

create or replace function decrementCommentCount()
    returns trigger as
$x$
begin
    update posts set comment_count = comment_count - 1 where id = old.post_id;
    update users_stats set comment_count = comment_count - 1 where user_id = old.user_id;
    return old;
end;
$x$
    language plpgsql;

create trigger after_delete_comment_comments
    after delete
    on comments
    for each row
execute procedure decrementCommentCount();

create or replace function incrementTopicPostCount()
    returns trigger as
$x$
begin
    update topics set post_count = post_count + 1 where id = new.topic_id;
    return new;
end;
$x$
    language plpgsql;

create trigger after_insert_post_post_topics
    after insert
    on posts_topics
    for each row
execute procedure incrementTopicPostCount();

create or replace function decrementTopicPostCount()
    returns trigger as
$x$
begin
    update topics set post_count = post_count - 1 where id = old.topic_id;
    return old;
end;
$x$
    language plpgsql;

create trigger after_delete_post_post_topics
    after delete
    on posts_topics
    for each row
execute procedure decrementTopicPostCount();

create or replace function incrementBookmarkCount()
    returns trigger as
$x$
begin
    update posts set bookmark_count = bookmark_count + 1 where id = new.post_id;
    update users_stats set bookmark_count = bookmark_count + 1 where user_id = new.user_id;
    return new;
end;
$x$
    language plpgsql;

create trigger after_insert_post_bookmark
    after insert
    on post_bookmarks
    for each row
execute procedure
    incrementBookmarkCount();

create or replace function decrementBookmarkCount()
    returns trigger as
$x$
begin
    update posts set bookmark_count = bookmark_count - 1 where id = old.post_id;
    update users_stats set bookmark_count = bookmark_count + 1 where user_id = old.user_id;
    return old;
end;
$x$
    language plpgsql;

create trigger after_delete_post_bookmark
    after delete
    on post_bookmarks
    for each
        row
execute procedure decrementBookmarkCount();

create or replace function incrementPostCount()
    returns trigger as
$$
begin
    update users_stats
    set post_count = post_count + 1
    where user_id = new.author_id;
    return new;
end;
$$
    language plpgsql;

create trigger after_insert_post_posts
    after insert
    on posts
    for each row
execute procedure incrementPostCount();


create or replace function decrementPostCount()
    returns trigger as
$$
begin
    update users_stats
    set post_count = post_count - 1
    where user_id = old.author_id;
    return old;
end;
$$
    language plpgsql;

create trigger after_delete_post_posts
    after delete
    on posts
    for each row
execute procedure decrementPostCount();

create or replace function incrementSubscriptionCount()
    returns trigger as
$$
begin
    update users_stats
    set subscription_count = subscription_count + 1
    where user_id = new.user_id;
    update users_stats
    set subscribed_count = subscribed_count + 1
    where user_id = new.author_id;
    return new;
end;
$$
    language plpgsql;

create trigger after_insert_subscription_subscriptions
    after insert
    on users_subscription_authors
    for each row
execute procedure incrementSubscriptionCount();

create or replace function decrementSubscriptionCount()
    returns trigger as
$$
begin
    update users_stats
    set subscription_count = subscription_count - 1
    where user_id = old.user_id;
    update users_stats
    set subscribed_count = subscribed_count - 1
    where user_id = old.author_id;
    return old;
end;
$$
    language plpgsql;

create trigger after_delete_subscription_subscriptions
    after delete
    on users_subscription_authors
    for each row
execute procedure decrementSubscriptionCount();

