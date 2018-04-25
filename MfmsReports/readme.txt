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

config using log4j @Tomcat
==========================
1. copy log4j.properties to $CATALINA_HOME\conf
2. edit log4j.properties to specify the logging location
	log4j.appender.file.File=/mfms.log
3. @Project/extra_libs/libs, copy (original name) tomcat-extras-juli-8.5.2.jar to (replace) $CATALINA_HOME/bin/tomcat-juli.jar (target name)
4. @Project/extra_libs/libs, copy all files including tomcat-extras-juli-adapters-8.5.2.jar to $CATALINA_HOME/lib

5. add line to tomcat start script:
	-Dlog4j.configuration="file:d:\apache-tomcat-8.5.27\conf\log4j.properties"
	(if for eclipse, stop tomcat server, double click Tomcat v8.5 Server at localhost, @Open launch configuration, click Arguments tag and append line to the argument

remark:
=======
for some git hub client, it will have problem, solved it by:
In Eclipse Help -> Install New Software Work with: http://download.eclipse.org/mpc/releases/1.5.1a Select the "EPP Marketplace Client" Proceed with defaults
