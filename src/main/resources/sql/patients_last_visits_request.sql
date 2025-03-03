WITH RequestedPatients AS (
    SELECT p.id, p.first_name, p.last_name FROM patients AS p
    WHERE (p.first_name LIKE :firstName OR :firstName IS NULL)
    ORDER BY p.first_name
    LIMIT :pageSize OFFSET :pageNo
    ),
ranked_visits AS (
    SELECT v.patient_id, v.doctor_id, v.start, v.end,
           ROW_NUMBER() OVER (PARTITION BY v.patient_id, v.doctor_id ORDER BY v.start DESC) AS rn
    FROM visits v
    )
SELECT
    p.id AS patientId,
    p.first_name AS patientFirstName,
    p.last_name AS patientLastName,
    d.first_name AS doctorFirstName,
    d.last_name AS doctorLastName,
    d.total_patients as totalPatients,
    rv.start,
    rv.end, doctor_id
FROM ranked_visits rv
         JOIN doctors d ON rv.doctor_id = d.id
         JOIN RequestedPatients p ON rv.patient_id = p.id
WHERE rv.rn = 1
  AND d.id IN (:doctorIds)
ORDER BY p.first_name;

























