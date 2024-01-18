#################################################
###
### Author: Tyrien Gilpin
### License: MIT
### Date: January 17th, 2024
### Version: 1.0
###
###########################################

/*
* --- My Coding Protocols ---
* Use pascal_case
* Table names are plural
* Specify id fields, eg: 'user_id' not 'id'
* No ambiguous column names
* Foreign key column name should be the same as in reference table
* Use caps fro all sql queries
*/

CREATE SCHEMA IF NOT EXISTS securecapita;

SET NAMES 'UTF8MB4';

SET
    TIME_ZONE = '-05:00';

USE
    securecapita;


DROP TABLE IF EXISTS TwoFactorVerification;
DROP TABLE IF EXISTS AccountVerifications;
DROP TABLE IF EXISTS UserEvents;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS UserRoles;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(50)     NOT NULL,
    last_name    VARCHAR(50)     NOT NULL,
    email        VARCHAR(100)    NOT NULL,
    password     VARCHAR(255)    NOT NULL,
    address      VARCHAR(255)    DEFAULT NULL,
    phone        VARCHAR(30)     DEFAULT NULL,
    title        VARCHAR(50)     DEFAULT NULL,
    bio          VARCHAR(50)  DEFAULT NULL,
    enabled      BOOLEAN      DEFAULT FALSE,
    non_locked   BOOLEAN      DEFAULT TRUE,
    using_mfa    BOOLEAN      DEFAULT FALSE,
    date_created DATETIME     DEFAULT CURRENT_TIMESTAMP,
    image_url    VARCHAR(255) DEFAULT 'https://www.flaticon.com/free-icons/user',
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);


CREATE TABLE Roles
(
    role_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50)     NOT NULL,
    permission VARCHAR(255)    NOT NULL, -- user:read, user:delete, customer:read
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);



CREATE TABLE UserRoles
(
    user_role_id BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT UNSIGNED NOT NULL,
    role_id      BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles (role_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);


CREATE TABLE Events
(
    event_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50)     NOT NULL CHECK ( type IN
                                                 ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS',
                                                  'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE',
                                                  'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE') ),
    description VARCHAR(255)    NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);


CREATE TABLE UserEvents
(
    user_event_id BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT UNSIGNED NOT NULL,
    event_id      BIGINT UNSIGNED NOT NULL,
    device        VARCHAR(100) DEFAULT NULL,
    iP_address     VARCHAR(100) DEFAULT NULL,
    date_created  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events (event_id) ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE AccountVerifications
(
    acc_ver_id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT UNSIGNED NOT NULL,
    url               VARCHAR(255) NOT NULL,
    reset_url         VARCHAR(255) DEFAULT NULL,
    reset_expiry_date DATETIME        NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerification_Users_Id UNIQUE (user_id),
    CONSTRAINT UQ_AccountVerification_Url UNIQUE (url),
    CONSTRAINT UQ_AccountVerification_ResetUrl UNIQUE (reset_url)
);


CREATE TABLE TwoFactorVerification
(
    tfa_id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT UNSIGNED NOT NULL,
    code            VARCHAR(10)  NOT NULL,
    expiration_date DATETIME        NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_TwoFactorVerification_Users_Id UNIQUE (user_id),
    CONSTRAINT UQ_TwoFactorVerification_Code UNIQUE (code)
);



