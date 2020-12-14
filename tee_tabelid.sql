-- DROP TABLE  application CASCADE;
-- DROP TABLE  app_service CASCADE;

CREATE TABLE application (
  app_code serial PRIMARY KEY
, name    text NOT NULL
, app_group    text NOT NULL
, app_type    text NOT NULL
, description    text NOT NULL
, app_cost      numeric NOT NULL DEFAULT 0
, last_modified date NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE app_service (
  service_code  serial PRIMARY KEY
, app_code     int REFERENCES application (app_code) ON UPDATE CASCADE
, name    text NOT NULL
, type    text NOT NULL
, sub_type    text NOT NULL
, description    text NOT NULL
, last_modified date NOT NULL DEFAULT CURRENT_DATE
);
