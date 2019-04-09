CREATE TABLE developers (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  male TINYINT(1) NULL,
  PRIMARY KEY (id));

CREATE TABLE skills (
  id INT NOT NULL AUTO_INCREMENT,
  branch VARCHAR(255) NOT NULL,
  level VARCHAR(255) NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE projects (
  id INT NOT NULL AUTO_INCREMENT,
  project_name VARCHAR(255) NOT NULL,
  type VARCHAR(255),
  PRIMARY KEY (id));

CREATE TABLE companies (
  id INT NOT NULL AUTO_INCREMENT,
  company_name VARCHAR(255) NOT NULL,
  location VARCHAR(255),
  PRIMARY KEY (id));

CREATE TABLE customers (
  id INT NOT NULL AUTO_INCREMENT,
  customer_name VARCHAR(255) NOT NULL,
  country VARCHAR(255),
  PRIMARY KEY (id));

CREATE TABLE dev_proj(
  dev_id INT NOT NULL,
  proj_id INT NOT NULL,
  FOREIGN KEY(dev_id) REFERENCES developers(id),
  FOREIGN KEY(proj_id) REFERENCES projects(id)
);

CREATE TABLE dev_skills(
  dev_id INT NOT NULL,
  skill_id INT NOT NULL,
  FOREIGN KEY(dev_id) REFERENCES developers(id),
  FOREIGN KEY(skill_id) REFERENCES skills(id)
);

CREATE TABLE comp_proj(
  company_id INT NOT NULL ,
  project_id INT NOT NULL ,
  FOREIGN KEY (company_id) REFERENCES companies(id),
  FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE cust_proj(
  customer_id INT NOT NULL ,
  project_id INT NOT NULL ,
  FOREIGN KEY (customer_id) REFERENCES customers(id),
  FOREIGN KEY (project_id) REFERENCES projects(id)
);
