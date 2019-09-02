

DELIMITER //

CREATE TRIGGER sales_before_insert
    BEFORE INSERT
    ON sales FOR EACH ROW

BEGIN

    DECLARE _code INT;

    -- Find username of person performing INSERT into table
    SET _code = (SELECT max(code) from sales where account_id = NEW.account_id);

    IF _code is null THEN
        SET _code = 0;
    end if;

    -- Update create_date field to current system date
    SET NEW.code = _code + 1;
    -- Update created_by field to the username of the person performing the INSERT


END; //

DELIMITER ;



DELIMITER //

CREATE TRIGGER products_before_insert
    BEFORE INSERT
    ON products FOR EACH ROW

BEGIN

    DECLARE _code INT;

    -- Find username of person performing INSERT into table
    SET _code = (SELECT max(code) from products where account_id = NEW.account_id);

    IF _code is null THEN
        SET _code = 0;
    end if;

    -- Update create_date field to current system date
    SET NEW.code = _code + 1;

    -- Update created_by field to the username of the person performing the INSERT


END; //

DELIMITER ;

DELIMITER //

CREATE TRIGGER products_categories_before_insert
    BEFORE INSERT
    ON products_categories FOR EACH ROW

BEGIN

    DECLARE _code INT;

    -- Find username of person performing INSERT into table
    SET _code = (SELECT max(code) from products_categories where account_id = NEW.account_id);

    IF _code is null THEN
        SET _code = 0;
    end if;

    -- Update create_date field to current system date
    SET NEW.code = _code + 1;

    -- Update created_by field to the username of the person performing the INSERT


END; //

DELIMITER ;
