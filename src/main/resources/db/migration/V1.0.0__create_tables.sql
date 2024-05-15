CREATE TABLE IF NOT EXISTS user
(
    id         bigint AUTO_INCREMENT NOT NULL,
    name       varchar(32)           NOT NULL,
    balance    decimal(10, 2)        NOT NULL DEFAULT 0.00,
    updated_at timestamp                      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
