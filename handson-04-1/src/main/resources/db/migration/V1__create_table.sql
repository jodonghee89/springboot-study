create table email (
   id bigint not null auto_increment,
    created_at datetime,
    email varchar(200),
    is_deleted bit not null,
    is_valid bit not null,
    account_id bigint,
    primary key (id)
) engine=MyISAM;

create table role (
   id bigint not null auto_increment,
    name varchar(200),
    account_id bigint,
    primary key (id)
) engine=MyISAM;

create table user (
   id bigint not null auto_increment,
    created_at datetime,
    created_by varchar(200),
    updated_at datetime,
    updated_by varchar(200),
    password varchar(200),
    age integer not null,
    gender varchar(200),
    name varchar(200),
    username varchar(200),
    primary key (id)
) engine=MyISAM;

create table user_roles (
   user_id bigint not null,
    roles varchar(200)
) engine=MyISAM;

alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
alter table email add constraint FKjoqcwhln0bj6q2jdv19ll18ak foreign key (account_id) references user (id);
alter table role add constraint FKfbeh79qn11ra1if2u5hf1ojxj foreign key (account_id) references user (id);
alter table user_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);

