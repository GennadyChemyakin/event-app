ALTER TABLE sec_user ADD enabled BOOLEAN NOT NULL;

CREATE TABLE authority(
  sec_user_id NUMBER(10) NOT NULL ,
  authority VARCHAR2(64 char) NOT NULL,
  CONSTRAINT authority_pk PRIMARY KEY (sec_user_id,authority),
  CONSTRAINT authority_sec_user_fk FOREIGN KEY (sec_user_id) REFERENCES sec_user (id)
);

COMMENT ON TABLE authority IS 'The table for storing users authority';