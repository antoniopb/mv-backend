--liquibase formatted sql
--changeset antonio.eduardo:2020-07-23_23_32 endDelimiter:/

CREATE PACKAGE mv.MV_PACKAGE AS
   PROCEDURE REAJUSTAR_PRECOS (ID_PRODUTO NUMBER, REAJUSTE NUMBER);
END;
/

CREATE PACKAGE BODY mv.MV_PACKAGE AS
	PROCEDURE REAJUSTAR_PRECOS (ID_PRODUTO NUMBER, REAJUSTE NUMBER) IS
	BEGIN 
		UPDATE PRODUTOS p SET p.PRECO = (p.PRECO + p.PRECO*REAJUSTE) WHERE p.ID = ID_PRODUTO; 
	END; 
END;
/

--rollback DROP PACKAGE mv.MV_PACKAGE;