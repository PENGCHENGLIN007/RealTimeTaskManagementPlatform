
drop table if exists  admin_info;
create table admin_info
(
  ID       int auto_increment
    primary key,
  NAME     varchar(100) not null,
  PASSWORD varchar(100) not null
);
drop table if exists  user_info;
create table user_info
(
  ID       int auto_increment
    primary key,
  NAME     varchar(100) not null,
  EMAIL    varchar(500) null,
  PASSWORD varchar(100) not null
);

drop table if exists  task_info;
create table task_info
(
  ID       int auto_increment
    primary key,
  NAME     varchar(100)  not null,
  USER_ID  int           not null,
  FLINK_ID varchar(500)   null,
  `SQL`    varchar(1000) not null
);

drop table if exists  source_info;
create table source_info
(
  ID        int auto_increment
    primary key,
  NAME      varchar(100) not null,
  TYPE      varchar(100) not null,
  USER_ID   int          not null,
  TASK_ID   int          not null,
  PARAMETER varchar(500) not null
);

drop table if exists  sink_info;
create table sink_info
(
  ID        int auto_increment
    primary key,
  NAME      varchar(100) not null,
  TYPE      varchar(100) not null,
  USER_ID   int          not null,
  TASK_ID   int          not null,
  PARAMETER varchar(500) not null
);

drop table if exists  column_info;
create table column_info
(
ID int auto_increment
  primary key,
  NAME      varchar(100) not null,
  TYPE      varchar(100) not null,
  TABLE_ID int not null,
  TABLE_TYPE  varchar(100) not null,
  `index` int  not null
);



