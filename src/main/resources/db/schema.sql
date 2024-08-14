-- 이벤트 스케줄러 활성화 (필요한 경우)
SET GLOBAL event_scheduler = ON;

-- 14일 이상 된 메시지를 삭제하는 이벤트 생성
CREATE EVENT IF NOT EXISTS delete_old_chat_messages
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_TIMESTAMP + INTERVAL 1 DAY
DO
DELETE FROM chat_message WHERE created_at < DATE_SUB(NOW(), INTERVAL 14 DAY);