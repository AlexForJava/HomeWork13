SELECT *
FROM projects;
ALTER TABLE projects ADD COLUMN cost INT;
UPDATE projects
SET cost = 3000
WHERE id = 1;
UPDATE projects
SET cost = 2800
WHERE id = 2;
UPDATE projects
SET cost = 4300
WHERE id = 3;
UPDATE projects
SET cost = 4100
WHERE id = 4;
UPDATE projects
SET cost = 3500
WHERE id = 5;
