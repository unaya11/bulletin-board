CREATE TABLE users (
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO users (name, email) VALUES
('佐藤太郎', 'taro.sato@example.com'),
('鈴木花子', 'hanako.suzuki@example.com'),
('田中一郎', 'ichiro.tanaka@example.com'),
('山田次郎', 'jiro.yamada@example.com'),
('高橋美咲', 'misaki.takahashi@example.com');

INSERT INTO message (title, message, user_id) VALUES
('タイトル2', 'テスト投稿2 佐藤太郎に紐づけています2', 1);