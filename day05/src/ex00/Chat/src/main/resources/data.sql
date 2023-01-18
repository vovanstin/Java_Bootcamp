INSERT INTO chat.users (login, password) VALUES
	('Bob', '123'),
	('Billy', '456'),
	('Logan', '789'),
	('Gachi', 'qwerty'),
	('Andrew', '123zxcv');

INSERT INTO chat.rooms (name, owner) VALUES
	('random', 1),
	('general', 2),
	('announcements', 3),
	('pedago_21', 4),
	('report', 5);

INSERT INTO chat.messages (author, room, text, timestamp) VALUES
	(1, 1, 'kek', '2022-12-26 08:23:45'),
	(2, 3, 'news about New Year', '2022-12-26 12:01:35'),
	(5, 3, 'news about deadlines', '2022-12-26 12:43:56'),
	(4, 4, 'pls, new deadline', '2022-12-26 16:05:06'),
	(5, 5, 'no working', '2022-12-26 18:12:01');