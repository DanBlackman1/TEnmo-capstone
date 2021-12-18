BEGIN TRANSACTION;

DROP TABLE IF EXISTS accounts, users, transfers CASCADE;

CREATE TABLE users (
   user_id int NOT NULL,
   username varchar(50) NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE accounts (
    account_id int NOT NULL,
    user_id int NOT NULL,
    balance NUMERIC(13,2),
    CONSTRAINT pk_accounts PRIMARY KEY (account_id),
    CONSTRAINT fk_accounts_users FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transfers (
    transfer_id int NOT NULL,
    transfer_type_id int NOT NULL,
    transfer_status_id int NOT NULL,
    account_from int NOT NULL,
    account_to int NOT NULL,
    amount NUMERIC(13,2),
    CONSTRAINT pk_transfers PRIMARY KEY (transfer_id),
    CONSTRAINT fk_transfers_accounts FOREIGN KEY (account_from) REFERENCES accounts(account_id),
    CONSTRAINT fk_transfers_accounts FOREIGN KEY (account_to) REFERENCES accounts(account_id)
);

INSERT INTO users (user_id, username)
VALUES (1, 'rob'),
       (2, 'Pikachu'),
       (3, 'Hitmontop');

INSERT INTO accounts (account_id, user_id, balance)
VALUES (10, 1, 140.00),
       (20, 2, 25.50),
       (30, 3, 3000.42);

INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
Values (1, 2, 2, 1, 2, 20.00),
       (2, 2, 2, 2, 3, 20.00),
       (3, 2, 2, 3, 1, 20.00);

COMMIT;


