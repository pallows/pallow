-- 트랜잭션 시작
START TRANSACTION;

INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-02 03:15:04', '2023-09-01 03:15:04', 'user1@example.com', 'User1', 'password1',
        'Name1', 'WOMAN', 'ROOT', 'user1', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-02 12:54:27', '2024-04-25 12:54:27', 'user2@example.com', 'User2', 'password2',
        'Name2', 'WOMAN', 'USER', 'user2', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-09-29 20:49:36', '2023-10-08 20:49:36', 'user3@example.com', 'User3', 'password3',
        'Name3', 'WOMAN', 'USER', 'user3', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-19 21:16:21', '2024-07-21 21:16:21', 'user4@example.com', 'User4', 'password4',
        'Name4', 'MAN', 'ROOT', 'user4', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-04 08:03:03', '2024-01-28 08:03:03', 'user5@example.com', 'User5', 'password5',
        'Name5', 'MAN', 'ROOT', 'user5', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-12-26 13:54:56', '2024-01-11 13:54:56', 'user6@example.com', 'User6', 'password6',
        'Name6', 'WOMAN', 'USER', 'user6', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-12-09 01:08:11', '2023-12-22 01:08:11', 'user7@example.com', 'User7', 'password7',
        'Name7', 'MAN', 'USER', 'user7', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-22 10:17:21', '2023-09-08 10:17:21', 'user8@example.com', 'User8', 'password8',
        'Name8', 'MAN', 'USER', 'user8', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-05-24 01:24:46', '2024-06-17 01:24:46', 'user9@example.com', 'User9', 'password9',
        'Name9', 'WOMAN', 'ROOT', 'user9', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-08 08:23:28', '2023-10-25 08:23:28', 'user10@example.com', 'User10', 'password10',
        'Name10', 'WOMAN', 'ROOT', 'user10', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-07 04:48:27', '2024-04-29 04:48:27', 'user11@example.com', 'User11', 'password11',
        'Name11', 'MAN', 'USER', 'user11', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-02-29 10:29:22', '2024-03-10 10:29:22', 'user12@example.com', 'User12', 'password12',
        'Name12', 'MAN', 'USER', 'user12', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-18 19:06:10', '2023-08-18 19:06:10', 'user13@example.com', 'User13', 'password13',
        'Name13', 'WOMAN', 'USER', 'user13', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-20 09:33:59', '2023-12-18 09:33:59', 'user14@example.com', 'User14', 'password14',
        'Name14', 'MAN', 'ROOT', 'user14', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-26 03:38:08', '2024-04-26 03:38:08', 'user15@example.com', 'User15', 'password15',
        'Name15', 'WOMAN', 'USER', 'user15', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-03 19:22:08', '2024-06-03 19:22:08', 'user16@example.com', 'User16', 'password16',
        'Name16', 'WOMAN', 'ROOT', 'user16', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-04 18:24:36', '2024-07-17 18:24:36', 'user17@example.com', 'User17', 'password17',
        'Name17', 'MAN', 'ROOT', 'user17', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-05-27 11:36:08', '2024-06-09 11:36:08', 'user18@example.com', 'User18', 'password18',
        'Name18', 'MAN', 'USER', 'user18', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-03-15 21:45:26', '2024-04-08 21:45:26', 'user19@example.com', 'User19', 'password19',
        'Name19', 'WOMAN', 'ROOT', 'user19', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-02-10 14:42:21', '2024-02-15 14:42:21', 'user20@example.com', 'User20', 'password20',
        'Name20', 'WOMAN', 'USER', 'user20', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-14 02:23:38', '2024-07-12 02:23:38', 'user21@example.com', 'User21', 'password21',
        'Name21', 'WOMAN', 'ROOT', 'user21', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-10 09:54:37', '2023-11-24 09:54:37', 'user22@example.com', 'User22', 'password22',
        'Name22', 'WOMAN', 'USER', 'user22', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-19 03:28:19', '2024-07-16 03:28:19', 'user23@example.com', 'User23', 'password23',
        'Name23', 'MAN', 'ROOT', 'user23', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-12-26 11:23:03', '2024-01-08 11:23:03', 'user24@example.com', 'User24', 'password24',
        'Name24', 'MAN', 'ROOT', 'user24', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-08 04:58:38', '2023-10-10 04:58:38', 'user25@example.com', 'User25', 'password25',
        'Name25', 'MAN', 'USER', 'user25', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-21 06:38:16', '2024-07-23 06:38:16', 'user26@example.com', 'User26', 'password26',
        'Name26', 'WOMAN', 'ROOT', 'user26', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-06 06:13:01', '2024-01-14 06:13:01', 'user27@example.com', 'User27', 'password27',
        'Name27', 'WOMAN', 'ROOT', 'user27', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-07 21:56:15', '2024-06-07 21:56:15', 'user28@example.com', 'User28', 'password28',
        'Name28', 'MAN', 'ROOT', 'user28', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-09-30 09:47:25', '2023-10-29 09:47:25', 'user29@example.com', 'User29', 'password29',
        'Name29', 'MAN', 'USER', 'user29', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-28 12:05:07', '2023-12-19 12:05:07', 'user30@example.com', 'User30', 'password30',
        'Name30', 'MAN', 'ROOT', 'user30', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-09 00:59:48', '2024-01-18 00:59:48', 'user31@example.com', 'User31', 'password31',
        'Name31', 'WOMAN', 'ROOT', 'user31', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-03-10 07:28:36', '2024-03-16 07:28:36', 'user32@example.com', 'User32', 'password32',
        'Name32', 'WOMAN', 'ROOT', 'user32', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-30 12:24:22', '2024-01-31 12:24:22', 'user33@example.com', 'User33', 'password33',
        'Name33', 'MAN', 'ROOT', 'user33', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-05-20 23:57:06', '2024-06-15 23:57:06', 'user34@example.com', 'User34', 'password34',
        'Name34', 'WOMAN', 'USER', 'user34', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-30 04:48:43', '2023-09-27 04:48:43', 'user35@example.com', 'User35', 'password35',
        'Name35', 'WOMAN', 'USER', 'user35', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-23 23:12:55', '2023-09-11 23:12:55', 'user36@example.com', 'User36', 'password36',
        'Name36', 'MAN', 'USER', 'user36', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-01 17:38:48', '2024-06-15 17:38:48', 'user37@example.com', 'User37', 'password37',
        'Name37', 'WOMAN', 'ROOT', 'user37', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-06 21:46:38', '2023-10-26 21:46:38', 'user38@example.com', 'User38', 'password38',
        'Name38', 'MAN', 'ROOT', 'user38', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-23 17:41:55', '2023-12-19 17:41:55', 'user39@example.com', 'User39', 'password39',
        'Name39', 'WOMAN', 'USER', 'user39', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-04 02:40:35', '2024-04-16 02:40:35', 'user40@example.com', 'User40', 'password40',
        'Name40', 'WOMAN', 'ROOT', 'user40', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-12-09 03:36:34', '2023-12-20 03:36:34', 'user41@example.com', 'User41', 'password41',
        'Name41', 'MAN', 'USER', 'user41', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-07 15:07:20', '2023-10-11 15:07:20', 'user42@example.com', 'User42', 'password42',
        'Name42', 'MAN', 'USER', 'user42', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-21 12:07:53', '2024-08-19 12:07:53', 'user43@example.com', 'User43', 'password43',
        'Name43', 'WOMAN', 'ROOT', 'user43', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-02-28 23:40:36', '2024-03-15 23:40:36', 'user44@example.com', 'User44', 'password44',
        'Name44', 'WOMAN', 'ROOT', 'user44', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-09 14:14:52', '2024-06-13 14:14:52', 'user45@example.com', 'User45', 'password45',
        'Name45', 'WOMAN', 'ROOT', 'user45', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-24 12:43:03', '2024-05-11 12:43:03', 'user46@example.com', 'User46', 'password46',
        'Name46', 'MAN', 'USER', 'user46', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-11 03:43:18', '2023-12-05 03:43:18', 'user47@example.com', 'User47', 'password47',
        'Name47', 'WOMAN', 'USER', 'user47', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-11 12:38:40', '2023-11-19 12:38:40', 'user48@example.com', 'User48', 'password48',
        'Name48', 'MAN', 'ROOT', 'user48', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-12-18 01:36:34', '2024-01-05 01:36:34', 'user49@example.com', 'User49', 'password49',
        'Name49', 'MAN', 'ROOT', 'user49', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-19 23:13:28', '2024-08-07 23:13:28', 'user50@example.com', 'User50', 'password50',
        'Name50', 'MAN', 'ROOT', 'user50', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-23 19:04:34', '2024-01-23 19:04:34', 'user51@example.com', 'User51', 'password51',
        'Name51', 'WOMAN', 'USER', 'user51', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-20 06:50:33', '2023-09-06 06:50:33', 'user52@example.com', 'User52', 'password52',
        'Name52', 'MAN', 'USER', 'user52', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-19 08:50:39', '2023-09-08 08:50:39', 'user53@example.com', 'User53', 'password53',
        'Name53', 'MAN', 'USER', 'user53', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-02-06 06:59:07', '2024-03-03 06:59:07', 'user54@example.com', 'User54', 'password54',
        'Name54', 'WOMAN', 'ROOT', 'user54', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-22 07:02:15', '2023-11-15 07:02:15', 'user55@example.com', 'User55', 'password55',
        'Name55', 'WOMAN', 'ROOT', 'user55', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-04 20:40:01', '2024-04-28 20:40:01', 'user56@example.com', 'User56', 'password56',
        'Name56', 'WOMAN', 'USER', 'user56', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-03-05 05:01:29', '2024-03-05 05:01:29', 'user57@example.com', 'User57', 'password57',
        'Name57', 'MAN', 'USER', 'user57', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-14 09:39:55', '2024-07-26 09:39:55', 'user58@example.com', 'User58', 'password58',
        'Name58', 'WOMAN', 'ROOT', 'user58', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-20 22:33:54', '2024-05-17 22:33:54', 'user59@example.com', 'User59', 'password59',
        'Name59', 'MAN', 'ROOT', 'user59', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-25 10:03:15', '2023-12-20 10:03:15', 'user60@example.com', 'User60', 'password60',
        'Name60', 'WOMAN', 'ROOT', 'user60', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-05-31 12:01:51', '2024-06-13 12:01:51', 'user61@example.com', 'User61', 'password61',
        'Name61', 'MAN', 'USER', 'user61', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-09-26 12:41:24', '2023-09-30 12:41:24', 'user62@example.com', 'User62', 'password62',
        'Name62', 'WOMAN', 'USER', 'user62', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-01 14:57:47', '2023-10-26 14:57:47', 'user63@example.com', 'User63', 'password63',
        'Name63', 'WOMAN', 'ROOT', 'user63', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-03-10 12:39:01', '2024-04-01 12:39:01', 'user64@example.com', 'User64', 'password64',
        'Name64', 'MAN', 'USER', 'user64', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-03-03 15:25:48', '2024-03-20 15:25:48', 'user65@example.com', 'User65', 'password65',
        'Name65', 'WOMAN', 'USER', 'user65', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-02 17:00:22', '2024-04-27 17:00:22', 'user66@example.com', 'User66', 'password66',
        'Name66', 'MAN', 'ROOT', 'user66', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-18 00:40:35', '2024-02-01 00:40:35', 'user67@example.com', 'User67', 'password67',
        'Name67', 'WOMAN', 'ROOT', 'user67', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-02 17:45:38', '2024-06-14 17:45:38', 'user68@example.com', 'User68', 'password68',
        'Name68', 'MAN', 'ROOT', 'user68', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-18 10:52:51', '2023-11-07 10:52:51', 'user69@example.com', 'User69', 'password69',
        'Name69', 'MAN', 'USER', 'user69', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-01 21:13:26', '2024-07-16 21:13:26', 'user70@example.com', 'User70', 'password70',
        'Name70', 'WOMAN', 'USER', 'user70', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-02-28 20:46:29', '2024-03-13 20:46:29', 'user71@example.com', 'User71', 'password71',
        'Name71', 'MAN', 'USER', 'user71', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-29 20:06:21', '2023-09-23 20:06:21', 'user72@example.com', 'User72', 'password72',
        'Name72', 'WOMAN', 'ROOT', 'user72', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-05 16:56:04', '2024-04-29 16:56:04', 'user73@example.com', 'User73', 'password73',
        'Name73', 'MAN', 'ROOT', 'user73', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-09-13 11:42:26', '2023-09-17 11:42:26', 'user74@example.com', 'User74', 'password74',
        'Name74', 'MAN', 'ROOT', 'user74', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-15 22:25:13', '2024-04-18 22:25:13', 'user75@example.com', 'User75', 'password75',
        'Name75', 'WOMAN', 'ROOT', 'user75', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-03-06 01:31:52', '2024-03-29 01:31:52', 'user76@example.com', 'User76', 'password76',
        'Name76', 'MAN', 'USER', 'user76', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-09 20:18:31', '2024-06-18 20:18:31', 'user77@example.com', 'User77', 'password77',
        'Name77', 'WOMAN', 'USER', 'user77', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-15 00:40:58', '2024-04-24 00:40:58', 'user78@example.com', 'User78', 'password78',
        'Name78', 'WOMAN', 'USER', 'user78', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-05-01 02:08:52', '2024-05-08 02:08:52', 'user79@example.com', 'User79', 'password79',
        'Name79', 'WOMAN', 'USER', 'user79', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-12-31 01:48:27', '2024-01-09 01:48:27', 'user80@example.com', 'User80', 'password80',
        'Name80', 'MAN', 'ROOT', 'user80', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-05-01 05:19:04', '2024-05-26 05:19:04', 'user81@example.com', 'User81', 'password81',
        'Name81', 'WOMAN', 'USER', 'user81', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-08-16 19:32:24', '2023-08-30 19:32:24', 'user82@example.com', 'User82', 'password82',
        'Name82', 'MAN', 'USER', 'user82', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-31 13:53:35', '2024-02-16 13:53:35', 'user83@example.com', 'User83', 'password83',
        'Name83', 'WOMAN', 'USER', 'user83', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-31 02:21:39', '2024-02-27 02:21:39', 'user84@example.com', 'User84', 'password84',
        'Name84', 'WOMAN', 'ROOT', 'user84', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-10 13:07:25', '2024-04-21 13:07:25', 'user85@example.com', 'User85', 'password85',
        'Name85', 'MAN', 'USER', 'user85', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-05 18:10:47', '2024-07-03 18:10:47', 'user86@example.com', 'User86', 'password86',
        'Name86', 'MAN', 'USER', 'user86', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-02-22 13:24:46', '2024-03-21 13:24:46', 'user87@example.com', 'User87', 'password87',
        'Name87', 'WOMAN', 'ROOT', 'user87', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-22 16:46:32', '2023-12-18 16:46:32', 'user88@example.com', 'User88', 'password88',
        'Name88', 'MAN', 'USER', 'user88', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-13 23:38:06', '2024-02-02 23:38:06', 'user89@example.com', 'User89', 'password89',
        'Name89', 'WOMAN', 'USER', 'user89', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-10-25 05:36:03', '2023-11-16 05:36:03', 'user90@example.com', 'User90', 'password90',
        'Name90', 'WOMAN', 'USER', 'user90', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-28 20:32:20', '2024-05-16 20:32:20', 'user91@example.com', 'User91', 'password91',
        'Name91', 'WOMAN', 'USER', 'user91', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-09-19 14:45:51', '2023-10-14 14:45:51', 'user92@example.com', 'User92', 'password92',
        'Name92', 'WOMAN', 'ROOT', 'user92', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-06-03 13:55:36', '2024-06-20 13:55:36', 'user93@example.com', 'User93', 'password93',
        'Name93', 'WOMAN', 'ROOT', 'user93', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-09-14 06:05:18', '2023-10-04 06:05:18', 'user94@example.com', 'User94', 'password94',
        'Name94', 'MAN', 'ROOT', 'user94', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-30 09:54:51', '2023-11-30 09:54:51', 'user95@example.com', 'User95', 'password95',
        'Name95', 'WOMAN', 'USER', 'user95', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2023-11-27 17:53:57', '2023-12-20 17:53:57', 'user96@example.com', 'User96', 'password96',
        'Name96', 'WOMAN', 'USER', 'user96', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-01-24 18:06:01', '2024-02-02 18:06:01', 'user97@example.com', 'User97', 'password97',
        'Name97', 'WOMAN', 'ROOT', 'user97', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-25 01:05:01', '2024-08-11 01:05:01', 'user98@example.com', 'User98', 'password98',
        'Name98', 'MAN', 'USER', 'user98', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-04-18 14:19:21', '2024-04-19 14:19:21', 'user99@example.com', 'User99', 'password99',
        'Name99', 'WOMAN', 'USER', 'user99', NULL);
INSERT INTO user (created_at, modified_at, email, nickname, password, name, gender, user_role,
                  username, profile_id)
VALUES ('2024-07-24 23:46:24', '2024-07-30 23:46:24', 'user100@example.com', 'User100',
        'password100', 'Name100', 'WOMAN', 'ROOT', 'user100', NULL);


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent1', '1989-09-14', '충청남도', 'ESFJ', 'SPORTS_SPECTATOR',
        'NOPE', 'UNIV', 'CONFIDENT', 'PUBLIC', 'CAREFUL',
        'EXPRESS', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'ETC', 'NONE', 1,
        'https://example.com/photo1.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent2', '2002-07-06', '대전광역시', 'ENFP', 'EXHIBITION',
        'NOPE', 'ETC', 'HANDSOME_BEAUTIFUL', 'PREPARE', 'Enthusiastic',
        'BRAIN', 'FRIENDSHIP_OR_DATING_OK', 'Buddhism', 'NONE', 2,
        'https://example.com/photo2.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent3', '1989-07-28', '대전광역시', 'ISFP', 'HIKING',
        'ALWAYS', 'UNIV_ON', 'NO_PRETENSE', 'PUBLIC', 'OPTIMISTIC',
        'EXPRESS', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Buddhism', 'QUITTING', 3,
        'https://example.com/photo3.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent4', '2000-06-23', '경기도', 'ENFJ', 'SPORTS_SPECTATOR',
        'ALWAYS', 'ETC', 'RELIABLE', 'SELF', 'CAREFUL',
        'MONEY', 'FINDING_FRIEND', 'WonBuddhism', 'QUITTING', 4, 'https://example.com/photo4.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent5', '1989-05-17', '경상남도', 'INFJ', 'PETS_PLANTS',
        'NOPE', 'HIGH', 'PASSIONATE', 'SOLDIER', 'PRIM',
        'EXPRESS', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Christian', 'CIGAR', 5,
        'https://example.com/photo5.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent6', '1974-10-07', '강원도', 'ENTJ', 'FITNESS',
        'NOPE', 'UNIV_ON', 'QUIRKY', 'OFFICE', 'BOLD',
        'BRAIN', 'FINDING_FRIEND', 'None', 'CIGAR', 6, 'https://example.com/photo6.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent7', '1978-09-11', '제주특별자치도', 'ESFJ', 'TV_SHOW',
        'ALWAYS', 'HIGH', 'COMMUNICATIVE', 'ATHLETIC', 'OPTIMISTIC',
        'DETAILED', 'UNDECIDED', 'WonBuddhism', 'ELECTRIC', 7, 'https://example.com/photo7.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent8', '1983-05-07', '서울특별시', 'ESFP', 'PETS_PLANTS',
        'SOMETIMES', 'ETC', 'CULTURED', 'BUSINESS', 'PRIM',
        'MONEY', 'FRIENDSHIP_OR_DATING_OK', 'WonBuddhism', 'ELECTRIC', 8,
        'https://example.com/photo8.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent9', '1997-02-14', '경기도', 'NONE', 'BILLIARDS',
        'SOMETIMES', 'ETC', 'CONFIDENT', 'EDUCATION', 'POSITIVE',
        'EMPATHY', 'UNDECIDED', 'Catholic', 'NONE', 9, 'https://example.com/photo9.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent10', '1986-09-02', '경기도', 'NONE', 'OMAKASE',
        'NOPE', 'ETC', 'CUTE', 'MEDICAL', 'HUMOR',
        'EXPRESS', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Catholic', 'QUITTING', 10,
        'https://example.com/photo10.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent11', '2000-11-23', '인천광역시', 'ISTJ', 'COOKING',
        'ALWAYS', 'UNIV_ON', 'SHY', 'REST', 'Enthusiastic',
        'MONEY', 'UNDECIDED', 'Catholic', 'CIGAR', 11, 'https://example.com/photo11.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent12', '1968-10-16', '인천광역시', 'INTJ', 'COSPLAY',
        'NOPE', 'HIGH', 'CLOSE_DISTANCE', 'MUSICIAN', 'LOGICAL',
        'EXPRESS', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Buddhism', 'ELECTRIC', 12,
        'https://example.com/photo12.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent13', '1972-09-10', '충청북도', 'INTJ', 'STUDY_CAFE',
        'ALWAYS', 'HIGH', 'UNIQUE', 'ETC', 'HOT',
        'MONEY', 'FRIENDSHIP_OR_DATING_OK', 'Christian', 'ELECTRIC', 13,
        'https://example.com/photo13.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent14', '2004-11-08', '대전광역시', 'ISFJ', 'WEBTOON',
        'SOMETIMES', 'HIGH', 'PASSIONATE', 'ETC', 'KIND',
        'BRAIN', 'UNDECIDED', 'WonBuddhism', 'CIGAR', 14, 'https://example.com/photo14.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent15', '1975-11-20', '경상북도', 'ENTJ', 'CAFE',
        'ALWAYS', 'GRADUATE_ON', 'ENERGETIC', 'ATHLETIC', 'POSITIVE',
        'EMPATHY', 'UNDECIDED', 'Catholic', 'CIGAR', 15, 'https://example.com/photo15.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent16', '1974-10-17', '대구광역시', 'INFP', 'DRIVE',
        'ALWAYS', 'GRADUATE_ON', 'CLOSE_DISTANCE', 'PRO', 'CREATIVE',
        'MONEY', 'UNDECIDED', 'Buddhism', 'NONE', 16, 'https://example.com/photo16.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent17', '1989-02-25', '대전광역시', 'ENFJ', 'NETFLIX',
        'NOPE', 'UNIV_ON', 'OLDER', 'MEDICAL', 'CUTE',
        'DETAILED', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Christian', 'ELECTRIC', 17,
        'https://example.com/photo17.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent18', '2005-07-08', '전라북도', 'ENFJ', 'BILLIARDS',
        'SOMETIMES', 'GRADUATE_ON', 'RELIABLE', 'OFFICE', 'UPSTAGE',
        'EMPATHY', 'UNDECIDED', 'Christian', 'CIGAR', 18, 'https://example.com/photo18.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent19', '1975-07-05', '충청남도', 'ESTJ', 'NETFLIX',
        'NOPE', 'HIGH', 'PETITE', 'EDUCATION', 'SENTIMENTAL',
        'EXPRESS', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Catholic', 'NONE', 19,
        'https://example.com/photo19.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent20', '1975-08-26', '울산광역시', 'INTJ', 'SPORTS_SPECTATOR',
        'SOMETIMES', 'HIGH', 'CULTURED', 'PREPARE', 'OPTIMISTIC',
        'FOREIGN', 'FRIENDSHIP_OR_DATING_OK', 'WonBuddhism', 'QUITTING', 20,
        'https://example.com/photo20.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent21', '1985-10-29', '경상북도', 'ISFP', 'BAR',
        'NOPE', 'GRADUATE_ON', 'PASSIONATE', 'OFFICE', 'KIND',
        'FASHION', 'UNDECIDED', 'Buddhism', 'ELECTRIC', 21, 'https://example.com/photo21.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent22', '1992-01-09', '강원도', 'ESTP', 'BOWLING',
        'ALWAYS', 'GRADUATE_ON', 'SMILEY', 'MUSICIAN', 'CALM',
        'DETAILED', 'FINDING_FRIEND', 'Christian', 'QUITTING', 22,
        'https://example.com/photo22.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent23', '2005-01-03', '광주광역시', 'ESFP', 'BAR',
        'ALWAYS', 'GRADUATE_ON', 'STYLISH', 'ATHLETIC', 'CALM',
        'DETAILED', 'FINDING_FRIEND', 'WonBuddhism', 'CIGAR', 23,
        'https://example.com/photo23.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent24', '1996-07-23', '세종특별자치시', 'ISFP', 'GOLF',
        'ALWAYS', 'HIGH', 'POLITE', 'ARTIST', 'CAREFUL',
        'FOREIGN', 'FRIENDSHIP_OR_DATING_OK', 'Catholic', 'NONE', 24,
        'https://example.com/photo24.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent25', '1988-09-01', '경기도', 'ENFP', 'DRIVE',
        'SOMETIMES', 'UNIV', 'SENSITIVE', 'FINANCE', 'CUTE',
        'MONEY', 'FINDING_FRIEND', 'Buddhism', 'NONE', 25, 'https://example.com/photo25.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent26', '1988-01-30', '경상북도', 'INTJ', 'SHOPPING',
        'SOMETIMES', 'UNIV_ON', 'ENERGETIC', 'OFFICE', 'CREATIVE',
        'CAREER', 'UNDECIDED', 'ETC', 'QUITTING', 26, 'https://example.com/photo26.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent27', '1988-01-19', '대구광역시', 'ISTP', 'FITNESS',
        'SOMETIMES', 'GRADUATE_ON', 'SENSITIVE', 'REST', 'HUMOR',
        'DETAILED', 'FINDING_FRIEND', 'Catholic', 'CIGAR', 27, 'https://example.com/photo27.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent28', '1990-10-30', '경상북도', 'INTP', 'DOING_NOTHING',
        'ALWAYS', 'UNIV', 'POLITE', 'EDUCATION', 'POSITIVE',
        'MONEY', 'FINDING_FRIEND', 'WonBuddhism', 'QUITTING', 28,
        'https://example.com/photo28.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent29', '1987-02-05', '서울특별시', 'ENFJ', 'PHOTOGRAPHY',
        'ALWAYS', 'GRADUATE_ON', 'COOL', 'SELF', 'KIND',
        'FASHION', 'FINDING_FRIEND', 'Buddhism', 'QUITTING', 29, 'https://example.com/photo29.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent30', '1999-08-15', '경상북도', 'ESTJ', 'TENNIS',
        'SOMETIMES', 'HIGH', 'QUIRKY', 'EDUCATION', 'CAREFUL',
        'FOREIGN', 'UNDECIDED', 'None', 'QUITTING', 30, 'https://example.com/photo30.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent31', '1967-03-17', '인천광역시', 'ESTP', 'PILATES_YOGA',
        'NOPE', 'GRADUATE_ON', 'YOUNGER', 'RESEARCH', 'BOLD',
        'FASHION', 'FINDING_FRIEND', 'Christian', 'NONE', 31, 'https://example.com/photo31.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent32', '2006-06-04', '강원도', 'NONE', 'CERTIFICATION',
        'NOPE', 'GRADUATE_ON', 'GOOD_LISTENER', 'SELF', 'PRIM',
        'DETAILED', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'WonBuddhism', 'ELECTRIC', 32,
        'https://example.com/photo32.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent33', '1968-02-17', '대전광역시', 'INFP', 'DRIVE',
        'SOMETIMES', 'ETC', 'CLOSE_DISTANCE', 'PRO', 'SMOOTH',
        'DETAILED', 'FRIENDSHIP_OR_DATING_OK', 'Catholic', 'NONE', 33,
        'https://example.com/photo33.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent34', '2004-04-25', '세종특별자치시', 'INTP', 'READING',
        'ALWAYS', 'UNIV_ON', 'CULTURED', 'OFFICE', 'POSITIVE',
        'BRAIN', 'UNDECIDED', 'Buddhism', 'QUITTING', 34, 'https://example.com/photo34.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent35', '2003-03-06', '세종특별자치시', 'ISFP', 'WALKING',
        'SOMETIMES', 'HIGH', 'PLEASANT_VOICE', 'ETC', 'CUTE',
        'FOREIGN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'WonBuddhism', 'NONE', 35,
        'https://example.com/photo35.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent36', '1968-09-10', '충청남도', 'INFP', 'PETS_PLANTS',
        'NOPE', 'HIGH', 'HUMOROUS', 'OFFICE', 'CUTE',
        'MONEY', 'UNDECIDED', 'WonBuddhism', 'ELECTRIC', 36, 'https://example.com/photo36.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent37', '1987-07-12', '전라남도', 'ESFP', 'WEBTOON',
        'ALWAYS', 'GRADUATE_ON', 'NO_SWEARING', 'PART_TIME', 'CUTE',
        'EMPATHY', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Buddhism', 'ELECTRIC', 37,
        'https://example.com/photo37.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent38', '1983-05-27', '대구광역시', 'INFP', 'GAMING',
        'NOPE', 'ETC', 'CAPABLE', 'STUDENT', 'CARING',
        'EXPRESS', 'FINDING_FRIEND', 'Christian', 'QUITTING', 38,
        'https://example.com/photo38.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent39', '1993-07-12', '대구광역시', 'INFP', 'WINE',
        'NOPE', 'UNIV', 'CALM', 'FREE_LANCER', 'PRIM',
        'BRAIN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'ETC', 'NONE', 39,
        'https://example.com/photo39.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent40', '1977-06-17', '제주특별자치도', 'ESTP', 'TV_SHOW',
        'NOPE', 'ETC', 'SAME_HOBBIES', 'RESEARCH', 'POSITIVE',
        'MONEY', 'UNDECIDED', 'ETC', 'NONE', 40, 'https://example.com/photo40.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent41', '2003-04-02', '충청남도', 'ISTP', 'CLIMBING',
        'ALWAYS', 'UNIV_ON', 'YOUNGER', 'FREE_LANCER', 'HOT',
        'FASHION', 'FINDING_FRIEND', 'Buddhism', 'NONE', 41, 'https://example.com/photo41.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent42', '2002-11-26', '대전광역시', 'ISFJ', 'NETFLIX',
        'NOPE', 'UNIV_ON', 'RELIABLE', 'FINANCE', 'CAREFUL',
        'EXPRESS', 'FINDING_FRIEND', 'None', 'ELECTRIC', 42, 'https://example.com/photo42.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent43', '1994-05-15', '경상남도', 'INTJ', 'ONE_DAY_CLASS',
        'SOMETIMES', 'UNIV', 'PASSIONATE', 'YOUTUBER', 'CAREFUL',
        'CAREER', 'FINDING_FRIEND', 'WonBuddhism', 'ELECTRIC', 43,
        'https://example.com/photo43.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent44', '1964-09-09', '전라북도', 'ESTJ', 'BAR',
        'ALWAYS', 'UNIV', 'STYLISH', 'RESEARCH', 'HOT',
        'FASHION', 'FINDING_FRIEND', 'Christian', 'ELECTRIC', 44,
        'https://example.com/photo44.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent45', '1987-09-28', '세종특별자치시', 'ESFJ', 'FOOD_TRAVEL',
        'ALWAYS', 'UNIV_ON', 'CAPABLE', 'ETC', 'KIND',
        'CAREER', 'FINDING_FRIEND', 'Catholic', 'CIGAR', 45, 'https://example.com/photo45.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent46', '1977-09-08', '서울특별시', 'ISTJ', 'DOING_NOTHING',
        'ALWAYS', 'UNIV_ON', 'NO_SWEARING', 'FREE_LANCER', 'PRIM',
        'FASHION', 'FRIENDSHIP_OR_DATING_OK', 'ETC', 'ELECTRIC', 46,
        'https://example.com/photo46.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent47', '1972-10-02', '제주특별자치도', 'INFJ', 'VOLUNTEER',
        'NOPE', 'HIGH', 'PASSIONATE', 'BUSINESS', 'CARING',
        'FASHION', 'FRIENDSHIP_OR_DATING_OK', 'WonBuddhism', 'CIGAR', 47,
        'https://example.com/photo47.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent48', '1968-04-05', '경상남도', 'ESTP', 'BILLIARDS',
        'NOPE', 'UNIV_ON', 'SAME_HOBBIES', 'BUSINESS', 'CAREFUL',
        'FASHION', 'FINDING_FRIEND', 'None', 'ELECTRIC', 48, 'https://example.com/photo48.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent49', '1990-05-22', '제주특별자치도', 'ISTJ', 'HIKING',
        'NOPE', 'HIGH', 'ENERGETIC', 'SOLDIER', 'BOLD',
        'FOREIGN', 'UNDECIDED', 'None', 'NONE', 49, 'https://example.com/photo49.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent50', '2000-04-08', '전라북도', 'ISFJ', 'SPORTS_SPECTATOR',
        'SOMETIMES', 'ETC', 'SMILEY', 'RESEARCH', 'SMOOTH',
        'BRAIN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'WonBuddhism', 'CIGAR', 50,
        'https://example.com/photo50.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent51', '1992-04-20', '광주광역시', 'ENFJ', 'DRAMA',
        'NOPE', 'GRADUATE_ON', 'CLOSE_DISTANCE', 'YOUTUBER', 'HOT',
        'EMPATHY', 'FRIENDSHIP_OR_DATING_OK', 'None', 'NONE', 51,
        'https://example.com/photo51.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent52', '2006-04-19', '울산광역시', 'ENFP', 'FINANCIAL_PLANNING',
        'SOMETIMES', 'ETC', 'QUIRKY', 'SOLDIER', 'LOGICAL',
        'FASHION', 'FRIENDSHIP_OR_DATING_OK', 'Catholic', 'CIGAR', 52,
        'https://example.com/photo52.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent53', '1975-06-29', '전라북도', 'NONE', 'CYCLING',
        'ALWAYS', 'GRADUATE_ON', 'POLITE', 'EDUCATION', 'UPSTAGE',
        'CAREER', 'FINDING_FRIEND', 'WonBuddhism', 'QUITTING', 53,
        'https://example.com/photo53.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent54', '1998-12-23', '광주광역시', 'ENFP', 'HIKING',
        'NOPE', 'GRADUATE_ON', 'PRUDENT', 'MEDICAL', 'KIND',
        'EMPATHY', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'ETC', 'CIGAR', 54,
        'https://example.com/photo54.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent55', '1993-03-25', '강원도', 'ISFJ', 'SPORTS_SPECTATOR',
        'NOPE', 'ETC', 'PETITE', 'FREE_LANCER', 'HOT',
        'FOREIGN', 'FRIENDSHIP_OR_DATING_OK', 'WonBuddhism', 'CIGAR', 55,
        'https://example.com/photo55.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent56', '1988-04-08', '부산광역시', 'INTJ', 'READING',
        'NOPE', 'GRADUATE_ON', 'POLITE', 'OFFICE', 'HOT',
        'EXPRESS', 'UNDECIDED', 'Buddhism', 'NONE', 56, 'https://example.com/photo56.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent57', '1983-11-30', '경상남도', 'INTP', 'CAMPING',
        'NOPE', 'UNIV_ON', 'CUTE_APPEARANCE', 'BUSINESS', 'HUMOR',
        'CAREER', 'FINDING_FRIEND', 'Christian', 'ELECTRIC', 57, 'https://example.com/photo57.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent58', '1987-07-31', '울산광역시', 'ENFP', 'CONCERT',
        'NOPE', 'UNIV', 'PLEASANT_VOICE', 'SELF', 'CALM',
        'DETAILED', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Catholic', 'ELECTRIC', 58,
        'https://example.com/photo58.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent59', '1965-10-23', '세종특별자치시', 'ISTP', 'INSTAGRAM',
        'NOPE', 'HIGH', 'SENSITIVE', 'MUSICIAN', 'CALM',
        'MONEY', 'FINDING_FRIEND', 'WonBuddhism', 'ELECTRIC', 59,
        'https://example.com/photo59.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent60', '1992-06-15', '충청북도', 'INTP', 'ONE_DAY_CLASS',
        'NOPE', 'HIGH', 'PASSIONATE', 'PREPARE', 'POSITIVE',
        'FOREIGN', 'FINDING_FRIEND', 'WonBuddhism', 'ELECTRIC', 60,
        'https://example.com/photo60.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent61', '1986-01-19', '충청북도', 'ENTP', 'LISTENING_MUSIC',
        'ALWAYS', 'GRADUATE_ON', 'PASSIONATE', 'ETC', 'HUMOR',
        'FOREIGN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Buddhism', 'NONE', 61,
        'https://example.com/photo61.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent62', '1994-02-21', '광주광역시', 'INTP', 'WINE',
        'ALWAYS', 'ETC', 'YOUNGER', 'MUSICIAN', 'CREATIVE',
        'EMPATHY', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Catholic', 'QUITTING', 62,
        'https://example.com/photo62.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent63', '1983-09-16', '대구광역시', 'ISFP', 'BOARD_GAME',
        'SOMETIMES', 'UNIV_ON', 'POLITE', 'PREPARE', 'CUTE',
        'EXPRESS', 'FRIENDSHIP_OR_DATING_OK', 'Christian', 'QUITTING', 63,
        'https://example.com/photo63.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent64', '1995-02-21', '광주광역시', 'ENFP', 'FINANCIAL_PLANNING',
        'SOMETIMES', 'UNIV', 'STYLISH', 'PRO', 'KIND',
        'FASHION', 'FINDING_FRIEND', 'Catholic', 'CIGAR', 64, 'https://example.com/photo64.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent65', '1970-05-15', '충청남도', 'ISFP', 'BADMINTON',
        'NOPE', 'GRADUATE_ON', 'HUMOROUS', 'INFLUENCER', 'CREATIVE',
        'MONEY', 'FINDING_FRIEND', 'ETC', 'CIGAR', 65, 'https://example.com/photo65.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent66', '1979-12-02', '대전광역시', 'ESFP', 'VOLUNTEER',
        'NOPE', 'UNIV_ON', 'NO_SWEARING', 'PREPARE', 'SOCIABLE',
        'CAREER', 'FINDING_FRIEND', 'Buddhism', 'NONE', 66, 'https://example.com/photo66.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent67', '1978-02-08', '충청남도', 'ESFJ', 'ESCAPE_ROOM',
        'SOMETIMES', 'HIGH', 'CULTURED', 'PART_TIME', 'SENTIMENTAL',
        'DETAILED', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Christian', 'QUITTING', 67,
        'https://example.com/photo67.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent68', '1986-07-19', '전라남도', 'ENFJ', 'FITNESS',
        'NOPE', 'HIGH', 'COMMUNICATIVE', 'REST', 'PRIM',
        'MONEY', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'ETC', 'NONE', 68,
        'https://example.com/photo68.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent69', '1977-12-19', '전라남도', 'ISFJ', 'TATTOO',
        'NOPE', 'ETC', 'SMILEY', 'ARTIST', 'CAREFUL',
        'DETAILED', 'FRIENDSHIP_OR_DATING_OK', 'WonBuddhism', 'CIGAR', 69,
        'https://example.com/photo69.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent70', '1979-12-20', '경기도', 'ENTP', 'COMIC_CAFE',
        'SOMETIMES', 'UNIV', 'CULTURED', 'RESEARCH', 'HOT',
        'EXPRESS', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'None', 'QUITTING', 70,
        'https://example.com/photo70.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent71', '1998-08-25', '대구광역시', 'ISTP', 'TENNIS',
        'NOPE', 'ETC', 'GOOD_LISTENER', 'RESEARCH', 'CALM',
        'BRAIN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Catholic', 'CIGAR', 71,
        'https://example.com/photo71.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent72', '1974-02-26', '울산광역시', 'INFJ', 'DOING_NOTHING',
        'ALWAYS', 'UNIV', 'MATURE', 'STUDENT', 'HOT',
        'FOREIGN', 'UNDECIDED', 'ETC', 'ELECTRIC', 72, 'https://example.com/photo72.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent73', '2004-04-26', '광주광역시', 'INFP', 'WALKING',
        'ALWAYS', 'HIGH', 'PRUDENT', 'ETC', 'LOGICAL',
        'MONEY', 'UNDECIDED', 'None', 'QUITTING', 73, 'https://example.com/photo73.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent74', '1973-05-04', '제주특별자치도', 'ISFJ', 'DANCE',
        'ALWAYS', 'HIGH', 'ENERGETIC', 'RESEARCH', 'UPSTAGE',
        'FOREIGN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'WonBuddhism', 'QUITTING', 74,
        'https://example.com/photo74.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent75', '1987-05-26', '전라북도', 'ISTP', 'FITNESS',
        'ALWAYS', 'UNIV_ON', 'SAME_AGE', 'PRO', 'OPTIMISTIC',
        'DETAILED', 'FINDING_FRIEND', 'Buddhism', 'CIGAR', 75, 'https://example.com/photo75.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent76', '1991-05-17', '울산광역시', 'ESFJ', 'DOING_NOTHING',
        'NOPE', 'UNIV_ON', 'SHY', 'ETC', 'POSITIVE',
        'CAREER', 'FRIENDSHIP_OR_DATING_OK', 'Catholic', 'NONE', 76,
        'https://example.com/photo76.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent77', '1968-04-20', '인천광역시', 'ENTP', 'CYCLING',
        'NOPE', 'UNIV', 'PASSIONATE', 'FINANCE', 'CAREFUL',
        'FOREIGN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'WonBuddhism', 'QUITTING', 77,
        'https://example.com/photo77.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent78', '2001-05-26', '세종특별자치시', 'INTP', 'SELF_IMPROVEMENT',
        'NOPE', 'ETC', 'COMMUNICATIVE', 'EDUCATION', 'HUMOR',
        'MONEY', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Christian', 'QUITTING', 78,
        'https://example.com/photo78.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent79', '1974-03-30', '충청남도', 'INFJ', 'BOARD_GAME',
        'NOPE', 'UNIV_ON', 'TALL', 'RESEARCH', 'PRIM',
        'EMPATHY', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'ETC', 'QUITTING', 79,
        'https://example.com/photo79.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent80', '1981-08-21', '울산광역시', 'ENTP', 'GOLF',
        'ALWAYS', 'UNIV_ON', 'QUIRKY', 'PRO', 'CREATIVE',
        'FOREIGN', 'UNDECIDED', 'Buddhism', 'QUITTING', 80, 'https://example.com/photo80.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent81', '1993-06-23', '전라북도', 'INFJ', 'BOWLING',
        'NOPE', 'HIGH', 'QUIET', 'MUSICIAN', 'FAITHFUL',
        'BRAIN', 'UNDECIDED', 'Buddhism', 'NONE', 81, 'https://example.com/photo81.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent82', '1974-04-27', '전라북도', 'INTP', 'MOVIE',
        'NOPE', 'GRADUATE_ON', 'CUTE', 'ETC', 'PRIM',
        'FASHION', 'FINDING_FRIEND', 'WonBuddhism', 'CIGAR', 82, 'https://example.com/photo82.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent83', '1986-02-13', '경상북도', 'INTP', 'STUDY_CAFE',
        'ALWAYS', 'ETC', 'SAME_AGE', 'MUSICIAN', 'POSITIVE',
        'DETAILED', 'FINDING_FRIEND', 'Catholic', 'QUITTING', 83,
        'https://example.com/photo83.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent84', '1977-03-22', '경기도', 'ESTJ', 'COOKING',
        'NOPE', 'GRADUATE_ON', 'IMPULSIVE', 'PRO', 'CALM',
        'FOREIGN', 'UNDECIDED', 'WonBuddhism', 'NONE', 84, 'https://example.com/photo84.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent85', '1987-01-15', '경기도', 'INTP', 'FITNESS',
        'ALWAYS', 'ETC', 'SAME_AGE', 'PUBLIC', 'CARING',
        'CAREER', 'FINDING_FRIEND', 'Christian', 'NONE', 85, 'https://example.com/photo85.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent86', '1981-12-01', '부산광역시', 'INFJ', 'PILATES_YOGA',
        'SOMETIMES', 'UNIV', 'CALM', 'FREE_LANCER', 'PRIM',
        'FOREIGN', 'FINDING_FRIEND', 'None', 'NONE', 86, 'https://example.com/photo86.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent87', '1980-10-25', '전라남도', 'ISFP', 'COMIC_CAFE',
        'SOMETIMES', 'ETC', 'SHY', 'FREE_LANCER', 'CALM',
        'MONEY', 'FINDING_FRIEND', 'WonBuddhism', 'CIGAR', 87, 'https://example.com/photo87.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent88', '1992-07-29', '경상북도', 'ISFJ', 'DRAMA',
        'ALWAYS', 'GRADUATE_ON', 'PRUDENT', 'PRO', 'SOCIABLE',
        'BRAIN', 'FINDING_FRIEND', 'ETC', 'NONE', 88, 'https://example.com/photo88.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent89', '2001-09-28', '부산광역시', 'INFJ', 'WINE',
        'ALWAYS', 'HIGH', 'UNIQUE', 'FINANCE', 'FAITHFUL',
        'FASHION', 'UNDECIDED', 'None', 'QUITTING', 89, 'https://example.com/photo89.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent90', '1975-12-15', '경상남도', 'ISTP', 'BEER',
        'ALWAYS', 'UNIV', 'NO_SWEARING', 'BUSINESS', 'CALM',
        'EXPRESS', 'UNDECIDED', 'Buddhism', 'NONE', 90, 'https://example.com/photo90.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent91', '1979-02-17', '제주특별자치도', 'INFJ', 'OMAKASE',
        'NOPE', 'HIGH', 'NO_PRETENSE', 'MEDICAL', 'LOGICAL',
        'FOREIGN', 'FRIENDSHIP_OR_DATING_OK', 'Christian', 'NONE', 91,
        'https://example.com/photo91.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent92', '1992-10-28', '경상북도', 'ESTJ', 'FITNESS',
        'SOMETIMES', 'UNIV', 'TALL', 'RESEARCH', 'Enthusiastic',
        'FOREIGN', 'UNDECIDED', 'Buddhism', 'CIGAR', 92, 'https://example.com/photo92.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent93', '1971-08-05', '광주광역시', 'ISFJ', 'BAKING',
        'NOPE', 'UNIV', 'RELIABLE', 'SELF', 'SOCIABLE',
        'BRAIN', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'Christian', 'QUITTING', 93,
        'https://example.com/photo93.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent94', '1982-10-05', '전라북도', 'INFP', 'TRAVEL',
        'ALWAYS', 'ETC', 'SMILEY', 'PART_TIME', 'CREATIVE',
        'DETAILED', 'FINDING_FRIEND', 'Christian', 'ELECTRIC', 94,
        'https://example.com/photo94.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent95', '2001-01-14', '충청북도', 'ISFJ', 'WALKING',
        'NOPE', 'GRADUATE_ON', 'MATURE', 'STUDENT', 'FAITHFUL',
        'CAREER', 'UNDECIDED', 'Catholic', 'NONE', 95, 'https://example.com/photo95.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent96', '1968-02-13', '경기도', 'ISTJ', 'NETFLIX',
        'NOPE', 'UNIV', 'SAME_AGE', 'STUDENT', 'CREATIVE',
        'FOREIGN', 'UNDECIDED', 'Catholic', 'CIGAR', 96, 'https://example.com/photo96.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent97', '1994-02-25', '충청남도', 'ISFJ', 'WALKING',
        'ALWAYS', 'GRADUATE_ON', 'SAME_HOBBIES', 'ATHLETIC', 'HOT',
        'MONEY', 'LOOKING_FOR_SERIOUS_RELATIONSHIP', 'None', 'ELECTRIC', 97,
        'https://example.com/photo97.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent98', '1992-04-25', '전라남도', 'ESTJ', 'COIN_KARAOKE',
        'NOPE', 'UNIV', 'UNIQUE', 'EDUCATION', 'HOT',
        'EXPRESS', 'FRIENDSHIP_OR_DATING_OK', 'Catholic', 'QUITTING', 98,
        'https://example.com/photo98.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent99', '1991-07-20', '제주특별자치도', 'ENFJ', 'TATTOO',
        'ALWAYS', 'UNIV', 'SAME_HOBBIES', 'PUBLIC', 'CREATIVE',
        'BRAIN', 'UNDECIDED', 'ETC', 'ELECTRIC', 99, 'https://example.com/photo99.jpg');


INSERT INTO profile (content, birth, position, mbti, interest, alcohol, education, ideal, jobs,
                     personality, pros, relationship, religion, smoking, user_id, photo)
VALUES ('ProfileContent100', '1973-04-17', '경상남도', 'ISFP', 'DOING_NOTHING',
        'SOMETIMES', 'HIGH', 'PLEASANT_VOICE', 'INFLUENCER', 'HOT',
        'CAREER', 'FRIENDSHIP_OR_DATING_OK', 'None', 'ELECTRIC', 100,
        'https://example.com/photo100.jpg');

-- 커밋
COMMIT;