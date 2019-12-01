create table t_twitter_temp (
    `tw_id` int unsigned auto_increment,
    `tw_date` DATE,
    `tw_string` VARCHAR(500) NOT NULL,
    PRIMARY KEY(`tw_id`)
)engine=InnoDB DEFAULT charset=utf8;

create table t_twitter (
    `tw_id` int unsigned auto_increment,
    `tw_date` DATE,
    `tw_string` VARCHAR(500) NOT NULL,
    PRIMARY KEY(`tw_id`)
)engine=InnoDB DEFAULT charset=utf8;