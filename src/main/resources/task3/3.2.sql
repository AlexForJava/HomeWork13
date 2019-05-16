SELECT name
FROM dev_proj
  INNER JOIN developers ON developers.id = dev_proj.dev_id
  INNER JOIN projects ON projects.id = dev_proj.proj_id
WHERE project_name = 'some_project';
