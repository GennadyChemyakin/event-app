create synonym eventapp_user.sec_user for eventapp.sec_user;
create synonym eventapp_user.event for eventapp.event;
create synonym eventapp_user.commentary for eventapp.commentary;
create synonym eventapp_user.video for eventapp.video;
create synonym eventapp_user.photo for eventapp.photo;
create synonym eventapp_user.tag for eventapp.tag;
create synonym eventapp_user.authority for eventapp.authority;
create synonym eventapp_user.sec_user_authority for eventapp.sec_user_authority;

GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.sec_user TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.event TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.commentary TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.video TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.photo TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.authority TO eventapp_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON eventapp.sec_user_authority TO eventapp_user;