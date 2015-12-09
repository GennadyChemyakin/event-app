ALTER TABLE sec_user ADD enabled CHAR(1) DEFAULT 1 NOT NULL;
ALTER TABLE sec_user ADD CONSTRAINT CHECK_ENABLED CHECK (enabled IN  (0,1));
CREATE TABLE authority(
  id NUMBER(10) NOT NULL ,
  authority VARCHAR2(64 CHAR) NOT NULL ,
  CONSTRAINT authority_pk PRIMARY KEY (id),
  CONSTRAINT authority_authority_unique UNIQUE (authority)
) TABLESPACE eventapp_tablespace;

CREATE TABLE sec_user_authority(
  sec_user_id NUMBER(10) NOT NULL ,
  authority_id NUMBER(10) NOT NULL,
  CONSTRAINT sec_user_authority_pk PRIMARY KEY (sec_user_id,authority_id),
  CONSTRAINT sec_user_authority_sec_user_fk FOREIGN KEY (sec_user_id) REFERENCES sec_user (id),
  CONSTRAINT sec_user_authority_fk FOREIGN KEY (authority_id) REFERENCES authority (id)
) TABLESPACE eventapp_tablespace;



COMMENT ON TABLE authority IS 'The table for storing users authority';