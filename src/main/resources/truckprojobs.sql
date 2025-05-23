CREATE TABLE users_type
(
    user_type_id   SERIAL PRIMARY KEY,
    user_type_name VARCHAR(255)
);

INSERT INTO users_type
VALUES (1, 'Recruiter'),
       (2, 'Candidate');

CREATE TABLE users
(
    user_id           SERIAL PRIMARY KEY,
    email             VARCHAR(255) UNIQUE,
    is_active         BOOLEAN      DEFAULT NULL,
    password          VARCHAR(255) DEFAULT NULL,
    registration_date TIMESTAMP    DEFAULT NULL,
    user_type_id      INT          DEFAULT NULL,
    CONSTRAINT fk_user_type FOREIGN KEY (user_type_id) REFERENCES users_type (user_type_id)
);

CREATE TABLE job_company
(
    id   SERIAL PRIMARY KEY,
    logo VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE job_location
(
    id      SERIAL PRIMARY KEY,
    city    VARCHAR(255) DEFAULT NULL,
    country VARCHAR(255) DEFAULT NULL,
    state   VARCHAR(255) DEFAULT NULL
);

CREATE TABLE candidate_profile
(
    user_account_id    INT NOT NULL,
    city               VARCHAR(255) DEFAULT NULL,
    country            VARCHAR(255) DEFAULT NULL,
    employment_type    VARCHAR(255) DEFAULT NULL,
    first_name         VARCHAR(255) DEFAULT NULL,
    last_name          VARCHAR(255) DEFAULT NULL,
    profile_photo      VARCHAR(255) DEFAULT NULL,
    resume             VARCHAR(255) DEFAULT NULL,
    state              VARCHAR(255) DEFAULT NULL,
    work_authorization VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (user_account_id),
    CONSTRAINT fk_user_account FOREIGN KEY (user_account_id) REFERENCES users (user_id)
);

CREATE TABLE recruiter_profile
(
    user_account_id INT NOT NULL,
    city            VARCHAR(255) DEFAULT NULL,
    company         VARCHAR(255) DEFAULT NULL,
    country         VARCHAR(255) DEFAULT NULL,
    first_name      VARCHAR(255) DEFAULT NULL,
    last_name       VARCHAR(255) DEFAULT NULL,
    profile_photo   VARCHAR(64)  DEFAULT NULL,
    state           VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (user_account_id),
    CONSTRAINT fk_user_account FOREIGN KEY (user_account_id) REFERENCES users (user_id)
);

CREATE TABLE job_post_activity
(
    job_post_id        SERIAL PRIMARY KEY,
    description_of_job VARCHAR(10000),
    job_title          VARCHAR(255),
    job_type           VARCHAR(255),
    posted_date        TIMESTAMP(6),
    remote             VARCHAR(255),
    salary             VARCHAR(255),
    job_company_id     INT,
    job_location_id    INT,
    posted_by_id       INT,
    CONSTRAINT FKpjpv059hollr4tk92ms09s6is FOREIGN KEY (job_company_id) REFERENCES job_company (id),
    CONSTRAINT FK44003mnvj29aiijhsc6ftsgxe FOREIGN KEY (job_location_id) REFERENCES job_location (id),
    CONSTRAINT FK62yqqbypsq2ik34ngtlw4m9k3 FOREIGN KEY (posted_by_id) REFERENCES users (user_id)
);

CREATE TABLE candidate_save
(
    id      SERIAL PRIMARY KEY,
    job     INT DEFAULT NULL,
    user_id INT DEFAULT NULL,
    UNIQUE (user_id, job),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES candidate_profile (user_account_id),
    CONSTRAINT fk_job FOREIGN KEY (job) REFERENCES job_post_activity (job_post_id)
);


CREATE TABLE candidate_apply
(
    id           SERIAL PRIMARY KEY,
    apply_date   TIMESTAMP(6),
    cover_letter VARCHAR(255),
    job          INT,
    user_id      INT,
    UNIQUE (user_id, job),
    CONSTRAINT FKmfhx9q4uclbb74vm49lv9dmf4 FOREIGN KEY (job) REFERENCES job_post_activity (job_post_id),
    CONSTRAINT FKs9fftlyxws2ak05q053vi57qv FOREIGN KEY (user_id) REFERENCES public.candidate_profile (user_account_id)
);


CREATE TABLE skills
(
    id                  SERIAL PRIMARY KEY,
    experience_level    VARCHAR(255) DEFAULT NULL,
    name                VARCHAR(255) DEFAULT NULL,
    years_of_experience VARCHAR(255) DEFAULT NULL,
    candidate_profile   INT          DEFAULT NULL,
    CONSTRAINT fks_candidate_profile FOREIGN KEY (candidate_profile) REFERENCES candidate_profile (user_account_id)
);