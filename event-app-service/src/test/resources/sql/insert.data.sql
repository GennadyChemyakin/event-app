insert into sec_user (id, username, password, email, name, surname, gender, photo, country, city, bio, enabled) VALUES(0, 'username','12345','user@gmail.com','Ivan','Ivanov','MALE',null,'Russia','SPb','user from SPb!','1');
insert into event VALUES(0,'birthday party y Ivana',0,'Party at Detsl home','Russia','SPb',null,13.12,12.56,'2015-11-15 15:00:00');
insert into commentary VALUES(COMMENTARY_ID_SEQ.nextval,0,0,'2016-01-19 15:00:00', 'Great!');
insert into commentary VALUES(COMMENTARY_ID_SEQ.nextval,0,0,'2016-01-19 17:00:00', 'I like it!');
insert into commentary VALUES(COMMENTARY_ID_SEQ.nextval,0,0,'2016-01-19 19:00:00', 'Cool!');
insert into authority VALUES(0,'ROLE_USER');

