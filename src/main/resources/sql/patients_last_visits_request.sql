WITH RequestedPatients AS (
    SELECT p.id, p.first_name, p.last_name
    FROM patients AS p
             JOIN visits AS v ON p.id = v.patient_id
    WHERE (:firstName IS NULL OR p.first_name LIKE :firstName)
      AND doctor_id IN (:doctorIds)
    GROUP BY p.id, p.first_name, p.last_name
    ORDER BY p.first_name, p.last_name
    LIMIT :pageSize OFFSET :pageNo
    ),
RowCount AS (
    SELECT COUNT(*) AS total_rows FROM patients AS p
    JOIN visits AS v ON p.id = v.patient_id
    WHERE (:firstName IS NULL OR p.first_name LIKE :firstName)
      AND doctor_id IN (:doctorIds)
    ),
RankedVisits AS (
    SELECT v.patient_id,
           v.doctor_id,
           v.start,
           v.end,
           rp.id AS patientId,
           rp.first_name,
           rp.last_name,
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
    rc.total_rows
FROM RankedVisits rv
        JOIN doctors d ON rv.doctor_id = d.id
        JOIN RowCount rc ON 1=1
WHERE rv.rn = 1
ORDER BY rv.first_name, rv.last_name;


