create synonym eventapp_user.AUTHORITY_ID_SEQ       for eventapp.AUTHORITY_ID_SEQ;
create synonym eventapp_user.COMMENTARY_ID_SEQ      for eventapp.COMMENTARY_ID_SEQ;
create synonym eventapp_user.EVENT_ID_SEQ           for eventapp.EVENT_ID_SEQ;
create synonym eventapp_user.PHOTO_ID_SEQ           for eventapp.PHOTO_ID_SEQ;
create synonym eventapp_user.SEC_USER_ID_SEQ        for eventapp.SEC_USER_ID_SEQ;
create synonym eventapp_user.TAG_ID_SEQ             for eventapp.TAG_ID_SEQ;
create synonym eventapp_user.VIDEO_ID_SEQ           for eventapp.VIDEO_ID_SEQ;

GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.AUTHORITY_ID_SEQ  TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.COMMENTARY_ID_SEQ TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.EVENT_ID_SEQ      TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.PHOTO_ID_SEQ      TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.SEC_USER_ID_SEQ   TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.TAG_ID_SEQ        TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.VIDEO_ID_SEQ      TO eventapp_user;