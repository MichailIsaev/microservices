ALTER SEQUENCE id_session_seq RESTART WITH 1;

TRUNCATE session , booking CASCADE;

-- Session content
CREATE OR REPLACE FUNCTION sessionGenerate() RETURNS VOID AS
  '
  BEGIN
  FOR i IN 1..5 LOOP
    FOR j IN 1..5 LOOP
      INSERT INTO session (film_id, hall_id, time) VALUES(j , j, (SELECT now() :: date + INTERVAL ''1 day'' +  INTERVAL ''1'' HOUR * (10 + i * 2) :: REAL));
    END LOOP;
  END LOOP;
  END;
  '
  LANGUAGE plpgsql;


SELECT sessionGenerate();

-- Booking content

INSERT INTO booking (user_id, session_id, tickets_amount)
VALUES
  (1, 1, 4),
  (2, 1, 3),
  (3, 2, 10),
  (1, 2, 3);