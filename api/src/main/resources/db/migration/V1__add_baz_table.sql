create table BAZ
(
    id         BIGSERIAL PRIMARY KEY,
    msg        TEXT      null,
    created_at TIMESTAMP DEFAULT TIMEZONE('UTC'::TEXT, now()),
    updated_at TIMESTAMP DEFAULT TIMEZONE('UTC'::TEXT, now()),
    deleted_at TIMESTAMP null
);
