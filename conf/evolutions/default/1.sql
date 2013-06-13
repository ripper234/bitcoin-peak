# Peak schema
 
# --- !Ups
 
CREATE TABLE Peaks (
    price decimal (20) NOT NULL,
    time timestamp NOT NULL DEFAULT current_timestamp
);

INSERT INTO Peaks(price) VALUES (266); -- April 2013
CREATE INDEX IX_time
ON Peaks(price)

# --- !Downs
 
DROP TABLE Peaks;