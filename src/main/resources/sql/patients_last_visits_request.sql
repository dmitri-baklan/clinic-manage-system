WITH FilteredPatients AS (
        SELECT DISTINCT p.id,
                        p.first_name,
                        p.last_name,
                        COUNT(*) OVER () AS total_rows
        FROM patients AS p
                 JOIN visits AS v ON p.id = v.patient_id AND doctor_id IN (:doctorIds)
        WHERE (:firstName IS NULL OR p.first_name LIKE :firstName)
        ORDER BY first_name, last_name
    ),
RequestedPatients AS (
        SELECT * FROM FilteredPatients
        LIMIT :pageSize OFFSET :pageNo
    ),
RankedVisits AS (
    SELECT v.patient_id,
           v.doctor_id,
           v.start,
           v.end,
           rp.id AS patientId,
           rp.first_name,
           rp.last_name,
           rp.total_rows,
           ROW_NUMBER() OVER (PARTITION BY v.patient_id, v.doctor_id ORDER BY v.start DESC) AS rn
    FROM visits v JOIN RequestedPatients rp ON v.patient_id = rp.id AND doctor_id IN (:doctorIds)
    )

SELECT
    rv.patientId AS patientId,
    rv.first_name AS patientFirstName,
    rv.last_name AS patientLastName,
    d.first_name AS doctorFirstName,
    d.last_name AS doctorLastName,
    d.total_patients as totalPatients,
    rv.start,
    rv.end,
    rv.total_rows
FROM RankedVisits rv
        JOIN doctors d ON rv.doctor_id = d.id
WHERE rv.rn = 1
ORDER BY rv.first_name, rv.last_name;


