use `computer-database-db`;
drop table if exists user;
drop table if exists role;

create table role (
    id                        bigint not null auto_increment,
    role_name                      varchar(255),
    constraint pk_user primary key (id))
  ;
  insert into role(role_name) VALUES('user');
  insert into role(role_name) VALUES('admin');

  create table user (
    id                        bigint not null auto_increment,
    username                      varchar(255),
    password                      varchar(255),
    role_id                         bigint not null,
    constraint pk_user primary key (id))
  ;
   alter table user add constraint fk_user_role_1 foreign key (role_id) references role (id) on delete restrict on update restrict;
  insert into user(username,password,role_id) VALUES('user','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',1);
  insert into user(username,password,role_id) VALUES('admin','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',2);