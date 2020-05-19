create table email_aud (
   id bigint not null,
    rev integer not null,
    revtype tinyint,
    created_at datetime,
    email varchar(200),
    is_deleted bit,
    is_valid bit,
    account_id bigint,
    primary key (id, rev)
) engine=MyISAM;

create table revinfo (
   rev integer not null auto_increment,
    revtstmp bigint,
    primary key (rev)
) engine=MyISAM;

 create table role_aud (
   id bigint not null,
    rev integer not null,
    revtype tinyint,
    name varchar(200),
    account_id bigint,
    primary key (id, rev)
) engine=MyISAM;

 create table user_aud (
   id bigint not null,
    rev integer not null,
    revtype tinyint,
    password varchar(200),
    age integer,
    gender varchar(200),
    name varchar(200),
    username varchar(200),
    primary key (id, rev)
) engine=MyISAM;


create table user_roles_aud (
   rev integer not null,
    user_id bigint not null,
    roles varchar(200) not null,
    revtype tinyint,
    primary key (rev, user_id, roles)
) engine=MyISAM;

alter table user_aud add constraint FK89ntto9kobwahrwxbne2nqcnr foreign key (rev) references revinfo (rev);

alter table role_aud add constraint FKrks7qtsmup3w81fdp0d6omfk7 foreign key (rev) references revinfo (rev);

alter table user_roles_aud add constraint FKox6xyy64fyq0y3dvv5ve53a0h foreign key (rev) references revinfo (rev);

alter table email_aud add constraint FKreffget15lydqdrc2gpl5g5it foreign key (rev) references revinfo (rev)