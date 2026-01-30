-- 기존 테이블의 id 컬럼을 BIGINT로 변경하는 마이그레이션 스크립트

-- 1. 기존 시퀀스 백업 및 새 시퀀스 생성
CREATE SEQUENCE IF NOT EXISTS users_id_seq_new AS BIGINT;

-- 2. 현재 시퀀스 값 가져오기
SELECT setval('users_id_seq_new', (SELECT COALESCE(MAX(id), 1) FROM users));

-- 3. id 컬럼을 BIGINT로 변경
ALTER TABLE users ALTER COLUMN id TYPE BIGINT;

-- 4. 시퀀스 연결 변경
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq_new');
ALTER SEQUENCE users_id_seq_new OWNED BY users.id;

-- 5. 기존 시퀀스 삭제 (선택사항)
-- DROP SEQUENCE IF EXISTS users_id_seq;
