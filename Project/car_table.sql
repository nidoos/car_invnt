drop table car cascade constraints;


--자동차테이블
create table car(
	c_num number(3),
	c_name varchar2(10),
	c_brand varchar2(10) not null,
	c_ivnt number(2) not null,
	constraint c_num_pk primary key(c_num)
);

--시퀀스 삭제
drop sequence c_num_seq;

--등록번호 시퀀스
create sequence c_num_seq
start with 1
increment by 1
maxvalue 20
minvalue 1;



insert into CAR values (c_num_seq.nextval, 	'VENUE', 'HYUNDAI', 1);
insert into car values (c_num_seq.nextval, 'SELTOS','KIA', 2);
insert into car values (c_num_seq.nextval, 'X3','BMW', 3);

select * from car;