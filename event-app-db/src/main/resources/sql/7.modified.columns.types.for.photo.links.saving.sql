ALTER TABLE SEC_USER drop column photo;
ALTER TABLE SEC_USER add PHOTO VARCHAR2(600 char);

ALTER TABLE PHOTO drop column PHOTO_DATA;
ALTER TABLE PHOTO add PHOTO_DATA VARCHAR2(600 char);