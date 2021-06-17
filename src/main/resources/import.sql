--------For Oracle----------
--  INSERT INTO user_accounts (created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,1,'rasel',0,'zaman','01711111111',111,111111,'rasel@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
--  INSERT INTO user_accounts (created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,1,'Avijit',0,'Saha','01715731066',555,'024582','avijit@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
--  INSERT INTO user_accounts (created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,1,'K.M.',0,'Hossain','01777121212',333,333333,'monowar@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
--  INSERT INTO roles (created_by, updated_by, role_name) VALUES (1,0,'ADMIN');
--  INSERT INTO users_roles (user_id, role_id) VALUES (1,1);
--  INSERT INTO users_roles (user_id, role_id) VALUES (2,1);
--  INSERT INTO users_roles (user_id, role_id) VALUES (4,1);


--------For MySQL----------
INSERT INTO `user_accounts`(created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,1,'rasel',0,'zaman','01711111111',111,111111,'rasel@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
INSERT INTO `user_accounts`(created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,1,'Avijit',0,'Saha','01715731066',555,'024582','avijit@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
INSERT INTO `user_accounts`(created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,1,'K.M.',0,'Hossain','01777121212',333,333333,'monowar@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
INSERT INTO `roles`(created_by, updated_by, role_name) VALUES (1,0,'ADMIN');
INSERT INTO `users_roles`(user_id, role_id) VALUES (1,1);
INSERT INTO `users_roles`(user_id, role_id) VALUES (2,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (3,1);
--------For PostgreSQL----------
-- INSERT INTO user_accounts (created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,'1','rasel','0','zaman','01711111111',111,111111,'rasel@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
-- INSERT INTO user_accounts (created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,'1','Avijit','0','Saha','01715731066',555,'024582','avijit@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
-- INSERT INTO `user_accounts`(created_by, updated_by,  enabled, first_name, is_using2fa, last_name, mobile_number, secret, user_bank_id, user_email, user_password, ACCOUNT_NON_LOCKED) VALUES (1,0,1,'K.M.',0,'Hossain','01777121212',333,333333,'monowar@jb.com','$2a$10$DFm33SYbUHTZii91QHBDle.qyQep53ycNrFaVH9Ss.DUTSQqsrhZy',1);
-- INSERT INTO roles (created_by, updated_by, role_name) VALUES (1,0,'ADMIN');
-- INSERT INTO users_roles (user_id, role_id) VALUES (1,1);
-- INSERT INTO `users_roles`(user_id, role_id) VALUES (2,1);
-- INSERT INTO users_roles (user_id, role_id) VALUES (4,1);