CREATE TABLE course (
                        id BIGINT PRIMARY KEY,
                        course_code VARCHAR(255),
                        credits INTEGER,
                        lecturer VARCHAR(255),
                        name VARCHAR(255)
);

CREATE TABLE enrollment (
                            id BIGINT PRIMARY KEY,
                            enrollment_date TIMESTAMP(6),
                            status VARCHAR(255),
                            course_id BIGINT,
                            student_id BIGINT
);

CREATE TABLE enrollment (
                            id BIGINT PRIMARY KEY,
                            enrollment_date TIMESTAMP(6),
                            status VARCHAR(255),
                            course_id BIGINT,
                            student_id BIGINT
);
