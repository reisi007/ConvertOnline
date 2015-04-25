Convert Project setup
=====================

-   Tomcat 8 + Java 8

-   MySQL Server

Context parameters are in the web.xml deployment descriptor

Job Server
----------

-   Just deploy the war file to Tomcat

Worker
------

### Context Parameters

+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.soffice      | Path to the soffice of Libre/OpenOffice  |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.threads      | The number of conversions possible       |
|                                          | simultaneously                           |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.rmi.server          | The host of the Job server               |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.jdbc.url     | The JDBC connection URL to the MySQL     |
|                                          | server                                   |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.jdbc.un      | The user name for MySQL                  |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.jdbc.pw      | The password for MySQL                   |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.officevendor | The vendor of the office suite           |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.from          | The email address the converted          |
|                                          | documents get sent from                  |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.smtp          | The SMTP server for the mail             |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.pw            | The password for the SMTP server         |
+------------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.usessl        | The boolean value, if SSL/TLS should be  |
|                                          | used                                     |
+------------------------------------------+------------------------------------------+

### Configuration notes

Please keep in mind, that the CA certificate has to be accepted by the Java VM
(especially for self signed certificates) and that there is a default timeout
(180 sec) which limits the number of possible conversions possible
simultaneously.

UI
--

### Context Parameters

+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.jdbc.url | The JDBC connection URL to the MySQL     |
|                                      | server                                   |
+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.jdbc.un  | The user name for MySQL                  |
+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.worker.jdbc.pw  | The password for MySQL                   |
+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.from      | The email address the converted          |
|                                      | documents get sent from                  |
+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.smtp      | The SMTP server for the mail             |
+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.pw        | The password for the SMTP server         |
+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.email.usessl    | The boolean value, if SSL/TLS should be  |
|                                      | used                                     |
+--------------------------------------+------------------------------------------+
| at.reisisoft.convert.rmi.server      | The host of the Job server               |
+--------------------------------------+------------------------------------------+
| at.reisisoft.converter.mail.devmail  | The mail address detzailed messages      |
|                                      | should be sent to                        |
+--------------------------------------+------------------------------------------+
| at.reisisoft.worker.upload.unit      | The unit for maximum file size (MB,KB,B) |
+--------------------------------------+------------------------------------------+
| at.reisisoft.worker.upload.maxsize   | An Integer for the maximum size          |
+--------------------------------------+------------------------------------------+

 
