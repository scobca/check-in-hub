-- V1__create_competitions.sql
CREATE TABLE competitions (
    id          BIGINT          NOT NULL,
    name        VARCHAR(255)    NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_competitions PRIMARY KEY (id)
);

-- Index for search by name
CREATE INDEX idx_competitions_name ON competitions (name);