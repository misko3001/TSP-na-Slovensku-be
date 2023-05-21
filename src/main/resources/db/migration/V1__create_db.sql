CREATE TABLE `point` (
    `city` VARCHAR(40) NOT NULL,
    `latitude` FLOAT NOT NULL,
    `longitude` FLOAT NOT NULL,
     PRIMARY KEY (`city`)
);

CREATE TABLE route (
    `start_point` VARCHAR(40) NOT NULL,
    `end_point` VARCHAR(40) NOT NULL,
    `distance` DOUBLE NOT NULL,
    `polyline` TEXT NOT NULL,
    PRIMARY KEY (`start_point`, `end_point`)
);