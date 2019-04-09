SELECT name
FROM dev_skills
  LEFT JOIN developers ON developers.id = dev_skills.dev_id
  LEFT JOIN skills ON skills.id = dev_skills.skill_id
WHERE level = 'middle';
