create table PERSON
(
    AMOUNT_BALANCE NUMERIC(38, 2)         not null,
    BIRTHDATE      DATE,
    ID             UUID                   not null
        primary key,
    EMAIL          CHARACTER VARYING(255) not null
        unique,
    FIRSTNAME      CHARACTER VARYING(255),
    LASTNAME       CHARACTER VARYING(255),
    PASSWORD       CHARACTER VARYING(255) not null
);

create table PERSON_CONNECTIONS_LIST
(
    CONNECTIONS_LIST_ID UUID not null,
    PERSON_ID           UUID not null,
    constraint FKBAMG7OXOVMI5A5WK4ACFQ6U8X
        foreign key (PERSON_ID) references PERSON,
    constraint FKTF8BUA9G7ENIIX0P8NQT78ODH
        foreign key (CONNECTIONS_LIST_ID) references PERSON
);

create table TRANSACTION
(
    TAX_AMOUNT      NUMERIC(38, 2),
    TRANSFER_AMOUNT NUMERIC(38, 2),
    OPERATION_DATE  TIMESTAMP,
    CREDITOR        UUID,
    DEBTOR          UUID,
    ID              UUID not null
        primary key,
    DESCRIPTION     CHARACTER VARYING(255),
    constraint FKDLN7GM9ECYUJYM8L0CL9LHF8J
        foreign key (CREDITOR) references PERSON,
    constraint FKMQRVIX4QU9B0A8T9YTYLP8NLT
        foreign key (DEBTOR) references PERSON
);


