# mv-backend
Projeto Backend para MV - Feito em Spring

## Requisitos

- [Java JDK 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Maven 3](https://maven.apache.org/download.cgi)
- [Oracle - 12c Standard Edition](https://www.oracle.com/database/technologies/oracle-database-software-downloads.html#12c)

O Driver Oracle [ojdbc8.jar](https://www.oracle.com/database/technologies/jdbc-ucp-122-downloads.html) pode ser instalado com o seguinte comando:
>mvn install:install-file -Dfile=<Path_to_file>\ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.1.0.2 -Dpackaging=jar

Foi utilizado um Schema de nome MV para este projeto, o mesmo pode ser criado executando os comandos:

```
alter session set "_oracle_script"=TRUE;

CREATE TABLESPACE tbs_perm_mv_01 DATAFILE 'tbs_perm_mv_01.dat' SIZE 20M;

CREATE TEMPORARY TABLESPACE tbs_temp_mv_01 TEMPFILE 'tbs_temp_mv_01.dbf' SIZE 5M;

CREATE USER mv
  IDENTIFIED BY passwrdmv
  DEFAULT TABLESPACE tbs_perm_mv_01
  TEMPORARY TABLESPACE tbs_temp_mv_01
  QUOTA 20M on tbs_perm_mv_01;

GRANT CREATE SESSION TO mv;
GRANT CREATE TABLE TO mv;
GRANT CREATE VIEW TO mv;
GRANT CREATE ANY trigger TO mv;
GRANT CREATE ANY procedure TO mv;
GRANT CREATE SEQUENCE TO mv;
GRANT CREATE SYNONYM TO mv;
```

O usuário de login na base oracle (assim como SID) pode ser modificado no application.properties do projeto):

```
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:<sid>
spring.datasource.username=<username>
spring.datasource.password=<password>
```
## Executando a aplicação

1. A aplicação pode ser executada utilizando, na pasta da aplicação) o comando :
   - > mvn spring-boot:run
   
2. Uma outra maneira é executando o método `main` presente na classe MvBackendApplication.java
