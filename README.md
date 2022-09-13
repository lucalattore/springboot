# springboot-demo

SQL used to create the table markets and later modify it adding a new column area.
```
create table markets (
  code integer primary key,
  name varchar(255)
);

alter table markets add column area varchar(255);
```