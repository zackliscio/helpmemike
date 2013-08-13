# facebookInfo schema

# --- !Ups

CREATE SEQUENCE facebookInfo_id_seq;
CREATE TABLE facebookInfo (
    id integer NOT NULL DEFAULT nextval('facebookInfo_id_seq'),
    fb_id long,
    fb_response varchar(2000),
    fb_name varchar(2000),
    fb_cover_url varchar(2000),
    fb_cover_offset_y long,
    fb_mission varchar(2000),
    fb_website varchar(2000),
    fb_year_founded varchar(2000),
    fb_likes long,
    fb_link varchar(2000)
    );

# --- !Downs

DROP TABLE facebookInfo;
DROP SEQUENCE facebookInfo_id_seq;
