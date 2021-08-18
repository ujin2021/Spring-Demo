-- 스프링 부트 server가 시작될 때마다 table을 drop하므로 server가 시작될 때 데이터를 넣어줄 수 있도록 sql문 작성

INSERT INTO USER (ID, USER_ID, PASSWORD) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi');
--INSERT INTO USER (USER_ID, USERNAME, PASSWORD) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC');
--INSERT INTO USER (USER_ID, USERNAME, PASSWORD) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC');

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');
--INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (2, 'ROLE_USER');