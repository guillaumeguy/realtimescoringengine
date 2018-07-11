

DROP TABLE IF EXISTS marketing.predictors ;
CREATE TABLE marketing.predictors (

ID Int primary key , 
RECENCY double,
LAST_EMAIL_OPEN double, 
HOUSEHOLD_INCOME double, 
CHANNEL_ACQUISITION varchar(200)
);

CREATE UNIQUE INDEX p1 ON predictors (ID);

load data local infile 'C:/Users/Guy\ Guillaume/Research/RealTimeScoringEngine/CustomerValue.csv' into table marketing.predictors
 fields terminated by ','
 -- enclosed by '"'
 lines terminated by '\n'
 IGNORE 1 LINES;
 -- (column1, column2, column3,...)

select * FROM marketing.predictors  where ID = 4

select distinct channel_acquisition from marketing.predictors

