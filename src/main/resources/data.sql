--CREATE TABLE users (
--    name VARCHAR(100) NOT NULL,
--    email VARCHAR(255) NOT NULL
--);

INSERT INTO user (name) VALUES
('佐藤太郎'),
('鈴木花子'),
('田中一郎'),
('山田次郎' ),
('高橋美咲');

INSERT INTO message (title, message, user_id, created_at) VALUES
('タイトル', 'テスト投稿 佐藤太郎に紐づけています', 1, '2021-05-30T15:47:13.395703');

--INSERT INTO reply (reply_id, reply, user_id, created_at, message_id) VALUES
--('1', 'テスト投稿 佐藤太郎に紐づけていますの返信', 1, '2024-12-30T12:27:13.111111',1);
--INSERT INTO reply (reply_id, reply, user_id, created_at, message_id) VALUES
--('2', 'テスト投稿 佐藤太郎に紐づけていますの返信の返信', 1, '2024-12-31T12:27:13.111111',1);

