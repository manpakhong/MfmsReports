apache-tomcat 7.0.85
====================
a.
rename original file at $CATALINA_HOME\bin\tomcat-juli.jar to $CATALINA_HOME\bin\tomcat-juli.jar.bak
copy from jdk1.7_tomcat7_libs\tomcat-juli.jar, overwrite $CATALINA_HOME\bin\tomcat-juli.jar
b.
copy from dk1.7_tomcat7_libs\libs\*.* to $CATALINA_HOME\libs
c.
copy from dk1.7_tomcat7_libs\log4j.properties to $CATALINA_HOME\conf\
MfmsReports Project
===================
make sure WebContent\META-INF\context.xml exists, or copy from jdk1.7_tomcat7_libs\context.xml

make sure context.xml the database connection parameters are correct.

mysql:
======
At epatrol database repository
grant all on epatrol.* to 'root'@'127.0.0.1' identified by 'password';

