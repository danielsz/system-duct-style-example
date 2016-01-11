-- name: create-tables!
-- create the tables if they don't exist
CREATE TABLE IF NOT EXISTS DIRECTORS(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255));
CREATE TABLE IF NOT EXISTS AUTHORS(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255));

-- name: directors
-- Return all directors.
SELECT *
FROM DIRECTORS

-- name: count-directors
-- Count all the directors
SELECT count(*) AS count
FROM DIRECTORS

-- name: save-director<!
-- Insert a director
INSERT INTO DIRECTORS ( name ) VALUES ( :name )

-- name: delete-director!
-- Deletes a director
DELETE FROM DIRECTORS WHERE NAME=:name

-- name: authors
-- Return all authors.
SELECT *
FROM AUTHORS

-- name: count-authors
-- Count all the authors
SELECT count(*) AS count
FROM AUTHORS

-- name: save-author<!
-- Insert a author
INSERT INTO AUTHORS ( name ) VALUES ( :name )

-- name: delete-author!
-- Deletes an author
DELETE FROM AUTHORS WHERE NAME=:name
