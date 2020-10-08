drop table user_t cascade constraints;


create table user_t(
	u_id varchar2(10),
	u_pw varchar2(10),
	u_name varchar2(10),
	u_adr varchar2(10),
	constraint u_id_pk primary key(u_id)
);

select *from user_t;

insert into user_t values ('sun', '1234', '±èÀº¼±', 'Ãµ¾È');
insert into user_t values ('narae', 'abcd','ÀÌ³ª·¡', '±¤ÁÖ');

select count(u_id), count(u_pw) from user_t where u_id='sun' and u_pw='1234';
