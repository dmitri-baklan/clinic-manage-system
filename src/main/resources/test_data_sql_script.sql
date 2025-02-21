-- Insert 100,000 random doctors
INSERT INTO appointment_system.doctors (first_name, last_name, time_zone)
SELECT
    CONCAT('Doctor', FLOOR(1 + (RAND() * 50000))) AS first_name,
    CONCAT('Surname', FLOOR(1 + (RAND() * 50000))) AS last_name,
    NOW() AS time_zone
FROM INFORMATION_SCHEMA.tables t1, INFORMATION_SCHEMA.tables t2
LIMIT 50000;

-- Insert 100,000 random patients
INSERT INTO appointment_system.patients (first_name, last_name)
SELECT
    CONCAT('Patient', FLOOR(1 + (RAND() * 50000))) AS first_name,
    CONCAT('Surname', FLOOR(1 + (RAND() * 50000))) AS last_name
FROM INFORMATION_SCHEMA.tables t1, INFORMATION_SCHEMA.tables t2
LIMIT 50000;

-- Insert 200,000 visits ensuring valid time range and unique doctor-patient pairs
-- Step 1: Pre-generate a list of random doctor and patient IDs
DROP TEMPORARY TABLE IF EXISTS temp_doctors;
DROP TEMPORARY TABLE IF EXISTS temp_patients;

-- Step 1: Create temporary tables with a unique row number
CREATE TEMPORARY TABLE temp_doctors AS
SELECT id, ROW_NUMBER() OVER () AS rn FROM appointment_system.doctors ORDER BY RAND() LIMIT 200000;

CREATE TEMPORARY TABLE temp_patients AS
SELECT id, ROW_NUMBER() OVER () AS rn FROM appointment_system.patients ORDER BY RAND() LIMIT 200000;

CREATE TABLE IF NOT EXISTS numbers (
                                       n INT PRIMARY KEY
);

-- Step 1: Create a number generator table with 200,000 rows
INSERT INTO numbers (n)
WITH RECURSIVE numbers_cte AS (
    SELECT 1 AS n
    UNION ALL
    SELECT n + 1 FROM numbers_cte WHERE n < 200000
)
SELECT n FROM numbers_cte;

-- Step 2: Insert 200,000 random visits
INSERT INTO appointment_system.visits (start, end, doctor_id, patient_id)
SELECT
    TIMESTAMPADD(DAY, FLOOR(RAND() * 60), NOW()) AS start_time,
    TIMESTAMPADD(HOUR, FLOOR(1 + (RAND() * 5)), TIMESTAMPADD(DAY, FLOOR(RAND() * 60), NOW())) AS end_time,
    (SELECT id FROM appointment_system.doctors ORDER BY RAND() LIMIT 1) AS doctor_id,
    (SELECT id FROM appointment_system.patients ORDER BY RAND() LIMIT 1) AS patient_id
FROM numbers
LIMIT 200000;




