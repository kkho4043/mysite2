select * from users;

select * from board;

CREATE TABLE board(
    no number  ,
    title varchar2(500) not null,
    content varchar2(4000),
    hit number (10),
    reg_date date not null,
    user_no number not null,
    primary key(no),
    constraint board_fk foreign key(user_no)
    references users(no)
);

create SEQUENCE seq_board_no increment by 1 start with 1;
drop SEQUENCE seq_board_no;

drop table board;

insert into board(no,title,content,hit,reg_date,user_no)
values(seq_board_no.nextval,'제목','내용',0,sysdate,1);


SELECT
    b.no,
    b.title,
    u.name,
    b.hit,
    TO_CHAR((b.reg_date),'YYYY/MM/DD HH:MM')
FROM board b,users u
where b.user_no = u.no
and b.no = 22;


update board 
set hit = hit + 1 
where no = 2;
select * from board;

commit;
			