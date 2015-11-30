ALTER TABLE sec_user ADD enabled BOOLEAN NOT NULL;

CREATE TABLE authority(
  id NUMBER(10) NOT NULL ,
  authority VARCHAR2(64 CHAR) NOT NULL ,
  CONSTRAINT authority_pk PRIMARY KEY (id),
  CONSTRAINT authority_authority_unique UNIQUE (authority)
);

CREATE TABLE sec_user_authority(
  sec_user_id NUMBER(10) NOT NULL ,
  authority_id number(10) NOT NULL,
  CONSTRAINT sec_user_authority_pk PRIMARY KEY (sec_user_id,authority_id),
  CONSTRAINT sec_user_authority_sec_user_fk FOREIGN KEY (sec_user_id) REFERENCES sec_user (id),
  CONSTRAINT sec_user_authority_authority_fk FOREIGN KEY (authority_id) REFERENCES authority (id)
);



COMMENT ON TABLE authority IS 'The table for storing users authority';