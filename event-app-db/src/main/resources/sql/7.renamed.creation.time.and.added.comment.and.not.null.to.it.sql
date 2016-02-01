ALTER TABLE event RENAME COLUMN creation_time TO create_time;
ALTER TABLE event MODIFY create_time NOT NULL;
COMMENT ON COLUMN event.create_time IS 'time of creation of event';