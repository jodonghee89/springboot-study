drop table account if exists;

drop table account_roles if exists;

drop table email if exists;

drop table role if exists;

create table account (
   id bigint generated by default as identity,
    created_at timestamp,
    created_by varchar(255),
    password varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    age integer not null,
    gender varchar(255),
    name varchar(255),
    username varchar(255),
    primary key (id)
);

create table account_roles (
   account_id bigint not null,
    roles varchar(255)
);

create table email (
   id bigint generated by default as identity,
    created_at timestamp,
    email varchar(255),
    is_deleted boolean not null,
    is_valid boolean not null,
    account_id bigint,
    primary key (id)
);

create table role (
   id bigint generated by default as identity,
    name varchar(255),
    account_id bigint,
    primary key (id)
);

alter table account_roles
   add constraint FKtp61eta5i06bug3w1qr6286uf
   foreign key (account_id)
   references account;

alter table email
   add constraint FKnqpo3l85k3agkldfrcjhx9o2s
   foreign key (account_id)
   references account;

alter table role
   add constraint FKm7tjaj4k18apvr1raulwdgd3
   foreign key (account_id)
   references account;