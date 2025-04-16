CREATE TABLE wallet (
                        id UUID PRIMARY KEY,
                        currency VARCHAR(10) NOT NULL,
                        balance NUMERIC(19, 4) NOT NULL,
                        created_at TIMESTAMP NOT NULL
);