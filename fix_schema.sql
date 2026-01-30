-- 기존 users 테이블의 id 컬럼을 BIGINT로 변경하는 스크립트
-- 이 스크립트는 기존 데이터베이스에 직접 실행하세요

-- 시퀀스 값 백업
DO $$
DECLARE
    max_id BIGINT;
BEGIN
    SELECT COALESCE(MAX(id), 0) INTO max_id FROM users;
    
    -- 기존 시퀀스 삭제 (있다면)
    DROP SEQUENCE IF EXISTS users_id_seq;
    
    -- 새 BIGSERIAL 시퀀스 생성
    CREATE SEQUENCE users_id_seq AS BIGINT;
    SELECT setval('users_id_seq', max_id);
    
    -- id 컬럼을 BIGINT로 변경
    ALTER TABLE users ALTER COLUMN id TYPE BIGINT USING id::BIGINT;
    
    -- 시퀀스를 기본값으로 설정
    ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq');
    ALTER SEQUENCE users_id_seq OWNED BY users.id;
END $$;
