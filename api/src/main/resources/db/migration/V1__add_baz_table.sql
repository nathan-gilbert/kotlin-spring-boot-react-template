create table BAZ
(
    id         BIGINT PRIMARY KEY,
    boo        TEXT      null,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, now()),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, now()),
    deleted_at TIMESTAMP null
);