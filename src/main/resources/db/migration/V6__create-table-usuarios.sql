CREATE TABLE USUARIOS(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    LOGIN VARCHAR(100) NOT NULL UNIQUE,
    SENHA VARCHAR(100) NOT NULL,

    PRIMARY KEY(ID)
);