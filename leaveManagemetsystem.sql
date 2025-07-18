DROP DATABASE leaveManagement;
CREATE DATABASE leaveManagement;
USE leaveManagement;

CREATE TABLE users (
user_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(255),
  role ENUM('user', 'admin') DEFAULT 'user',
  leave_balance INT DEFAULT 10
);

CREATE TABLE leave_request (
request_id INT PRIMARY KEY AUTO_INCREMENT,
  User_id INT,
  F_name VARCHAR(100),
  L_name VARCHAR(100),
  start_date DATE,
  end_date DATE,
  leave_type ENUM('Sick', 'Casual', 'Earned'),
  reason TEXT,
  status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
  applied_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO users (name, email, password, role, leave_balance)
VALUES ('Admin', 'admin@example.com', 'admin123', 'admin', 0);

SELECT * FROM users;

INSERT INTO leave_request (user_id, F_name, L_name, start_date, end_date, leave_type, reason)
VALUES 
  (1, 'Paras', 'Sawalkar', '2025-07-20', '2025-07-22', 'Casual', 'Family function'),
  (1, 'Sanskruti', 'Ingle', '2025-07-25', '2025-07-26', 'Sick', 'Fever and rest needed'),
  (1, 'Glenn', 'Maxwell', '2025-08-01', '2025-08-03', 'Earned', 'Travel to hometown'),
  (1, 'David', 'Warner', '2025-08-10', '2025-08-12', 'Casual', 'Friend’s wedding'),
  (1, 'Faf', 'Duplesis', '2025-08-15', '2025-08-15', 'Sick', 'Dental appointment'),
  (1, 'Steve', 'Smith', '2025-08-20', '2025-08-22', 'Earned', 'Short vacation');


SELECT * FROM users;

