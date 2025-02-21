
WITH RankedVisits AS (
    SELECT
        v.id,
        v.patient_id,
        v.doctor_id,
        v.start AS visit_start,
        v.end AS visit_end,
        ROW_NUMBER() OVER (PARTITION BY v.patient_id, v.doctor_id ORDER BY v.start DESC) AS rn
    FROM visits v JOIN patients p ON v.patient_id = p.id
    WHERE (p.first_name LIKE :firstName OR :firstName IS NULL)
      AND (p.last_name LIKE :lastName OR :lastName IS NULL)
      AND (v.doctor_id IN ())
    ORDER BY v.start
),
     UniquePatients AS (
         SELECT DISTINCT patient_id, visit_start
         FROM RankedVisits
         ORDER BY visit_start
         LIMIT :pageSize OFFSET :pageNo
    )
SELECT
    p.id AS patientId,
    p.first_name AS patientFirstName,
    p.last_name AS patientLastName,
    d.first_name AS doctorFirstName,
    d.last_name AS doctorLastName,
    d.total_patients as totalPatients,
    v.visit_start,
    v.visit_end
FROM RankedVisits v
         JOIN UniquePatients u ON v.patient_id = u.patient_id
         JOIN patients p ON v.patient_id = p.id
         JOIN doctors d ON v.doctor_id = d.id
WHERE v.rn = 1
ORDER BY patientFirstName;