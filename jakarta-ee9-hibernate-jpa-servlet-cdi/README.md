## Version
- java 18+
- java version "20.0.1" 2023-04-18
- apache-tomcat-10.1.10
- maven-3.8.701.20230209-1606
- mysql-8.0.33

# CONFIGURE TOMCAT
```
	// Adding user admin
	$ vi $TOMCAT_HOME/conf/tomcat-users.xml
		<user username="admin" password="admin" roles="admin,manager-gui,manager-script"/>

	// Change Port to 9000
	$  vi $TOMCAT_HOME/conf/server.xml
		<Connector port="9000" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
               maxParameterCount="1000"
               />

	// Access out of localhost
	$ vi $TOMCAT_HOME/webapps/manager/META-INF/context.xml
		//Replace
			allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1"
		//with
			allow=".*"
			
	// To watch logs TOMCAT
	$ tail -100f $TOMCAT_HOME/logs/catalina.out
	
	// To watch logs TOMCAT Listeners
	$ tail -100f $TOMCAT_HOME/localhost.2023-07-17.log
```

# CONFIGURE TOMCAT WITH MySQL
```
	// From Maven we get data to download MySQL Java Connector
	<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>8.0.33</version>
	</dependency>

	// Download MySQL Java Conneector
	$ mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get \
	-DremoteRepositories=http://download.java.net/maven/2 \
	-Dartifact=mysql:mysql-connector-java:8.0.33 \
	-Ddest=./mysql-connector-java.jar
	
	$ cp ./mysql-connector-java.jar $TOMCAT_HOME/lib
```

# CONFIGURE TOMCAT WITH DATASOURCE MySQL
```
	$ mkdir jakarta-ee9-webapp-cdi/src/main/webapp/META-INF/context.xml
		<Resource name="jdbc/MySQLDB" auth="Container"
			type="javax.sql.DataSource" maxTotal="100" maxIdle="30"
			maxWaitMillis="10000" username="root" password="123456"
			driverClassName="com.mysql.cj.jdbc.Driver"
			url="jdbc:mysql://localhost:3306/enterprise?serverTimezone=Europe/Madrid" />
	$ mkdir jakarta-ee9-webapp-cdi/src/main/webapp/WEB-INF/web.xml
		<resource-ref>
			<description>DB Connection</description>
			<res-ref-name>jdbc/MySQLDB</res-ref-name>
			<res-type>javax.sql.DataSource</res-type>
			<res-auth>Container</res-auth>
		</resource-ref>
```

# START/STOP TOMCAT
```
	$ sh $TOMCAT_HOME/bin/startup.sh
	$ sh $TOMCAT_HOME/bin/shutdown.sh
```

# CONFIGURE MAVEN to execute on console 
```
    $ vi $MAVEN_HOME/conf/settings.xml
        <pluginGroups>
            <pluginGroup>org.apache.tomcat.maven</pluginGroup>
        </pluginGroups>
```

# CONFIGURE INTELLIJ IDEA 
```
    Watch How To Run in IntelliJ: "Run - IntelliJ.png"
```

# Configure Project in Eclipse
```
	$ mvn eclipse:eclipse
```

# RUN 
```
    $ mvn tomcat7:redeploy
```

# DEPLOY
```
	$ curl http://localhost:8080/manager
	
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/login.html
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/logout
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/products.html		"redirect to list.jsp"
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/cart/add
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/cart/view 			"redirect to cart.jsp"

	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/form.jsp    		"error first invoke /product/form the next link"
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/product/form		"Securized with LoginFilter"
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/product/form?id=1	"Securized with LoginFilter"
	$ curl http://kevin.cx:9000/jakarta-ee9-webapp-cdi/product/delete?id=1
```

# DEBUG REMOTELY TOMCAT
```
	1) Start Tomcat in debug mode
		$ su // log as root
		$ vi $TOMCAT_HOME/bin/catalina.sh jpda start

		   By default JPDA_ADDRESS is defined as "localhost:8000" in catalina.sh
		   Change to a different port as need or localhost to 0.0.0.0 for remote debug
		   
			if [ -z "$JPDA_ADDRESS" ]; then
				JPDA_ADDRESS="0.0.0.0:8000"
		  	fi
		  	
	  	$ sh $TOMCAT_HOME/bin/catalina.sh jpda start

	2.1) In IntelliJ 
		Click Run > Edit Configurations
		Click + icon on the top-left toolbar
		Click Remote
		Enter a name you want in Name input box
		Enter 8000 in Port input box under Settings section and the Host
		Click Apply, then OK
		Run > Debug..., Click the configuration you just created

	2.2) In Eclipse
		Watch How To Configure in Eclipse: Debug - Eclipse.png
```

