drop table order_t cascade constraints;

create table order_t(
	o_num number(10),
	u_id varchar2(10),
	c_num number(3),
	u_adr varchar2(10),
	constraint o_num_pk primary key(o_num),
	constraint u_id_fk foreign key(u_id) references user_t(u_id),
	constraint c_num_fk foreign key(c_num) references car(c_num)
);


drop sequence o_num_seq;

create sequence o_num_seq
start with 1
increment by 1
maxvalue 10
minvalue 1;




