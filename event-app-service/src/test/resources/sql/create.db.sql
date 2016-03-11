SET MODE Oracle;

CREATE TABLE SEC_USER
(
  ID NUMBER(10) NOT NULL
, USERNAME VARCHAR2(64 CHAR) NOT NULL
, PASSWORD VARCHAR2(64 CHAR) NOT NULL
, EMAIL VARCHAR2(64 CHAR) NOT NULL
, NAME VARCHAR2(64 CHAR)
, SURNAME VARCHAR2(64 CHAR)
, GENDER VARCHAR2(6 CHAR)
, PHOTO BLOB
, COUNTRY VARCHAR2(64 CHAR)
, CITY VARCHAR2(64 CHAR)
, BIO CLOB
, CONSTRAINT SEC_USER_PK PRIMARY KEY (ID)
, CONSTRAINT SEC_USER_UNIQUE_USERNAME UNIQUE (USERNAME)
, CONSTRAINT SEC_USER_UNIQUE_EMAIL UNIQUE (EMAIL)
, CONSTRAINT CHECK_GENDER CHECK (GENDER in ('MALE', 'FEMALE'))

);


COMMENT ON TABLE SEC_USER IS 'The table for storing Users';


CREATE TABLE EVENT
(
  ID NUMBER(10) NOT NULL
, NAME VARCHAR2(64 CHAR) NOT NULL
, SEC_USER_ID NUMBER(10) NOT NULL
, DESCRIPTION CLOB
, COUNTRY VARCHAR2(64 CHAR)
, CITY VARCHAR2(64 CHAR)
, ADDRESS VARCHAR2(64 CHAR)
, GPS_LATITUDE NUMBER(10, 7)
, GPS_LONGITUDE NUMBER(10, 7)
, EVENT_TIME TIMESTAMP
, CONSTRAINT EVENT_PK PRIMARY KEY (ID)
, CONSTRAINT EVENT_SEC_USER_FK
	FOREIGN KEY (SEC_USER_ID)
	REFERENCES SEC_USER (ID)
);

COMMENT ON TABLE EVENT IS 'The table for storing Events';


CREATE TABLE COMMENTARY
(
  ID NUMBER(10) NOT NULL
, EVENT_ID NUMBER(10) NOT NULL
, SEC_USER_ID NUMBER(10) NOT NULL
, COMMENT_TIME TIMESTAMP
, MESSAGE CLOB
, CONSTRAINT COMMENTARY_PK PRIMARY KEY (ID)
, CONSTRAINT COMMENTARY_EVENT_FK
	FOREIGN KEY (EVENT_ID)
	REFERENCES EVENT (ID)
, CONSTRAINT COMMENTARY_SEC_USER_FK
	FOREIGN KEY (SEC_USER_ID)
	REFERENCES SEC_USER (ID)
);

COMMENT ON TABLE COMMENTARY IS 'The table for storing Commentaries on Events';

CREATE TABLE VIDEO
(
  ID NUMBER(10) NOT NULL
, EVENT_ID NUMBER(10) NOT NULL
, VIDEO_DATA BLOB NOT NULL
, CONSTRAINT VIDEO_PK PRIMARY KEY (ID)
, CONSTRAINT VIDEO_EVENT_FK
	FOREIGN KEY (EVENT_ID)
	REFERENCES EVENT (ID)
);

COMMENT ON TABLE VIDEO IS 'The table for storing Videos to Events';


CREATE TABLE PHOTO
(
  ID NUMBER(10) NOT NULL
, EVENT_ID NUMBER(10) NOT NULL
, PHOTO_DATA BLOB NOT NULL
, CONSTRAINT PHOTO_PK PRIMARY KEY (ID)
, CONSTRAINT PHOTO_EVENT_FK
	FOREIGN KEY (EVENT_ID)
	REFERENCES EVENT (ID)
);

COMMENT ON TABLE PHOTO IS 'The table for storing Photos to Events';

CREATE TABLE TAG
(
  ID NUMBER(10) NOT NULL
, EVENT_ID NUMBER(10) NOT NULL
, TAG_NAME VARCHAR2(64 char) NOT NULL
, CONSTRAINT TAG_PK PRIMARY KEY (ID)
, CONSTRAINT TAG_EVENT_FK
	FOREIGN KEY (EVENT_ID)
	REFERENCES EVENT (ID)
);

COMMENT ON TABLE TAG IS 'The table for storing Event Tags';

ALTER TABLE sec_user ADD enabled CHAR(1) DEFAULT 1 NOT NULL;
ALTER TABLE sec_user ADD CONSTRAINT CHECK_ENABLED CHECK (enabled IN  (0,1));

CREATE TABLE authority(
  id NUMBER(10) NOT NULL ,
  authority VARCHAR2(64 CHAR) NOT NULL ,
  CONSTRAINT authority_pk PRIMARY KEY (id),
  CONSTRAINT authority_authority_unique UNIQUE (authority)
);

CREATE TABLE sec_user_authority(
  sec_user_id NUMBER(10) NOT NULL ,
  authority_id NUMBER(10) NOT NULL,
  CONSTRAINT sec_user_authority_pk PRIMARY KEY (sec_user_id,authority_id),
  CONSTRAINT sec_user_authority_sec_user_fk FOREIGN KEY (sec_user_id) REFERENCES sec_user (id),
  CONSTRAINT sec_user_authority_fk FOREIGN KEY (authority_id) REFERENCES authority (id)

);

COMMENT ON TABLE authority IS 'The table for storing users authority';

CREATE SEQUENCE AUTHORITY_ID_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE COMMENTARY_ID_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE EVENT_ID_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE PHOTO_ID_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_USER_ID_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE TAG_ID_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE VIDEO_ID_SEQ START WITH 1 INCREMENT BY 1;

ALTER TABLE event ADD create_time TIMESTAMP NOT NULL;
COMMENT ON COLUMN event.create_time IS 'time of creation of event';

ALTER TABLE COMMENTARY ADD CONSTRAINT COMMENT_TIME_NOT_NULL CHECK(COMMENT_TIME IS NOT NULL);
ALTER TABLE COMMENTARY ADD CONSTRAINT MESSAGE_NOT_NULL CHECK(MESSAGE IS NOT NULL);
