drop table admin cascade constraints;

create table admin(
	a_id varchar2(10),
	a_pw varchar2(10),
	a_name varchar2(10),
	constraint a_id_pk primary key (a_id)
);

select * from admin;

insert into admin values('a1','1234', 'ÇÑ¼öºó');

