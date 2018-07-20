CREATE TABLE user (
  id         CHAR(32)     NOT NULL PRIMARY KEY,
  profile_id CHAR(32),
  username   VARCHAR(60)  NOT NULL UNIQUE,
  nickname   VARCHAR(45)  NOT NULL,
  password   VARCHAR(256) NOT NULL,
  salt       VARCHAR(256) NOT NULL,
  strategy   VARCHAR(45)  NOT NULL,
  created    TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  updated    TIMESTAMP    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
)
  ENGINE innodb
  CHARSET utf8;

CREATE TABLE user_profile (
  id          CHAR(32)    NOT NULL PRIMARY KEY,
  name        VARCHAR(45) NOT NULL DEFAULT '',
  family_name VARCHAR(45) NOT NULL DEFAULT '',
  phone       VARCHAR(16) NOT NULL DEFAULT '',
  email       VARCHAR(60) NOT NULL DEFAULT '',
  locale      VARCHAR(45) NOT NULL DEFAULT '',
  gender      TINYINT(1)  NOT NULL DEFAULT 0,
  birthday    DATE,
  created     TIMESTAMP   NOT NULL DEFAULT current_timestamp,
  updated     TIMESTAMP   NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
)
  ENGINE innodb
  CHARSET utf8;