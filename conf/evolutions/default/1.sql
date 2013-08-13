# facebookInfo schema

# --- !Ups

CREATE SEQUENCE facebookInfo_id_seq;
CREATE TABLE facebookInfo (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    fb_id long,
    fb_response varchar(255),
    fb_name varchar(255),
    fb_cover_url varchar(255),
    fb_cover_offset_y long,
    fb_mission varchar(255),
    fb_website varchar(255),
    fb_year_founded varchar(255),
    fb_likes long,
    fb_link varchar(255)
    );

# --- !Downs

DROP TABLE facebookInfo;
DROP SEQUENCE facebookInfo_id_seq;
