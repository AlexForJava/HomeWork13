ALTER TABLE projects ADD COLUMN date DATE;
SELECT *
FROM projects;
UPDATE projects
SET date = '2000-11-01'
WHERE id = 1;
UPDATE projects
SET date = '2001-5-12'
WHERE id = 2;
UPDATE projects
SET date = '2000-12-08'
WHERE id = 3;
UPDATE projects
SET date = '2001-08-28'
WHERE id = 4;
UPDATE projects
SET date = '2001-03-01'
WHERE id = 5;

SELECT
  date,
  project_name,
  count(developers.id)
FROM dev_proj
  LEFT JOIN projects ON projects.id = dev_proj.proj_id
  LEFT JOIN developers ON developers.id = dev_proj.dev_id
GROUP BY project_name;
