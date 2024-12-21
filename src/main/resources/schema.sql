-- Creating schema for the database
CREATE TABLE IF NOT EXISTS account (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    document_number VARCHAR(20) NOT NULL,
    version BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS operation_type (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES account (id),
    operation_type_id BIGINT NOT NULL REFERENCES operation_type (id),
    amount  NUMERIC(10, 2) NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version BIGINT NOT NULL
);

-- Inserting data into the operation_type table
INSERT INTO operation_type (id, description) VALUES (1, 'Normal Purchase') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation_type (id, description) VALUES (2, 'Purchase with installments') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation_type (id, description) VALUES (3, 'Withdrawal') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation_type (id, description) VALUES (4, 'Credit Voucher') ON CONFLICT (id) DO NOTHING;