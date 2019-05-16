ALTER TABLE developers ADD COLUMN salary INT NOT NULL;

UPDATE developers SET salary = 1000 WHERE id = 1;

UPDATE developers SET salary = 1300 WHERE id = 2;

UPDATE developers SET salary = 700 WHERE id = 3;

UPDATE developers SET salary = 400 WHERE id = 4;

UPDATE developers SET salary = 900 WHERE id = 5;

UPDATE developers SET salary = 700 WHERE id = 6;

UPDATE developers SET salary = 1100 WHERE id = 7;
