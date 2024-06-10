CREATE TABLE users (
    username varchar(45),
    userpassword varchar(45),
    securityQuestion varchar(100),
    securityAnswer varchar(100),
    income decimal(10,2),
    income_source varchar(25)
);

CREATE TABLE expenses (
    username varchar(45),
    itemBought varchar(45),
    price decimal(10, 2),
    quantity varchar(100),
    shopName varchar(45),
    date DATESTAMP DEFAULT CURRENT_DATE varchar(25)
);
