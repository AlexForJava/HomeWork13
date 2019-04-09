SELECT
  SUM(salary),
  project_name
FROM dev_proj
  LEFT JOIN developers ON developers.id = dev_proj.dev_id
  LEFT JOIN projects ON projects.id = dev_proj.proj_id
GROUP BY proj_id
ORDER BY sum(salary) DESC
LIMIT 1;
