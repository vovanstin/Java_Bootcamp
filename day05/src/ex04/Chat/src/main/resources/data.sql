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

INSERT INTO chat.messages (author, chatroom, text, dateTime) VALUES
	(1, 1, 'kek', (SELECT NOW()::timestamp)),
	(2, 3, 'news about New Year', (SELECT NOW()::timestamp)),
	(5, 5, 'news about deadlines', (SELECT NOW()::timestamp)),
	(4, 4, 'pls, new deadline', (SELECT NOW()::timestamp)),
	(5, 5, 'no working', (SELECT NOW()::timestamp));