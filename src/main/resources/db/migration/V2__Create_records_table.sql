-- V2__Create_records_table.sql
CREATE TABLE records
(
    id             BIGSERIAL PRIMARY KEY,
    username       VARCHAR(255) NOT NULL,
    flow           VARCHAR(50)  NOT NULL,
    competition_id BIGINT       NOT NULL,
    age_category   VARCHAR(20)  NOT NULL,
    result         VARCHAR(20)  NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE,
    updated_at     TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_records_competition
        FOREIGN KEY (competition_id)
            REFERENCES competitions (id)
            ON DELETE RESTRICT
);

CREATE INDEX idx_records_username ON records (username);
CREATE INDEX idx_records_flow ON records (flow);
CREATE INDEX idx_records_competition_id ON records (competition_id);
CREATE INDEX idx_records_created_at ON records (created_at);
