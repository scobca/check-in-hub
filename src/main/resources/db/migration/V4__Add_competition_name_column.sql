-- Add column
ALTER TABLE records
    ADD COLUMN competition_name VARCHAR(255) NOT NULL DEFAULT '';

CREATE INDEX idx_records_competition_name ON records (competition_name);

-- Update function for competition_name
CREATE OR REPLACE FUNCTION update_competition_name()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.competition_name = (
        SELECT name FROM competitions
        WHERE id = NEW.competition_id
    );
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- INSERT & UPDATE trigger
CREATE TRIGGER trg_update_competition_name
    BEFORE INSERT OR UPDATE OF competition_id
    ON records
    FOR EACH ROW
EXECUTE FUNCTION update_competition_name();
