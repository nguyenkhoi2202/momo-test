-- INSERT INTO users (id, name, balance)
-- VALUES (1, 'Khoi ne', 0)
--     ON CONFLICT (id) DO UPDATE
--                             SET name = EXCLUDED.name,
--                             balance = EXCLUDED.balance;
--
-- INSERT INTO bill (id, type, amount, due_date, state, provider, users_id)
-- VALUES
--     (1, 'ELECTRIC', 200000, '2025-10-25', 'NOT_PAID', 'EVN HCMC', 1),
--     (2, 'WATER', 175000, '2025-10-30', 'NOT_PAID', 'SAVACO HCMC', 1),
--     (3, 'INTERNET', 800000, '2025-11-30', 'NOT_PAID', 'VNPT', 1)
--     ON CONFLICT (id) DO UPDATE
--                             SET type = EXCLUDED.type,
--                             amount = EXCLUDED.amount,
--                             due_date = EXCLUDED.due_date,
--                             state = EXCLUDED.state,
--                             provider = EXCLUDED.provider,
--                             users_id = EXCLUDED.users_id;



MERGE INTO users (id, name, balance) KEY(id)
    VALUES (1, 'Khoi ne', 0);

MERGE INTO bill (id, type, amount, due_date, state, provider, users_id) KEY(id)
    VALUES
    (1, 'ELECTRIC', 200000, '2025-10-25', 'NOT_PAID', 'EVN HCMC', 1),
    (2, 'WATER', 175000, '2025-10-30', 'NOT_PAID', 'SAVACO HCMC', 1),
    (3, 'INTERNET', 800000, '2025-11-30', 'NOT_PAID', 'VNPT', 1);
