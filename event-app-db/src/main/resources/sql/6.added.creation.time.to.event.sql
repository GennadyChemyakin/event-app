ALTER TABLE eventapp.event ADD CREATE_TIME TIMESTAMP NOT NULL;
COMMENT ON COLUMN eventapp.event.create_time IS 'time of creation of event';