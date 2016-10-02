Readme file for RPC style of web service client

To build the project - 
Firstly, install below weblogic jars to local (or global) maven repository

mvn install:install-file -Dfile=jaxrpc-api-1.1.jar -DgroupId=javax.xml.rpc -DartifactId=api -Dpackaging=maven-plugin -Dversion=1.1 -DgeneratePom=true

mvn install:install-file -Dfile=com.oracle.webservices.wls.wls-soap-stack-impl_12.1.3.jar -DgroupId=com.oracle.webservices.wl -DartifactId=wls-soap-stack-impl -Dpackaging=maven-plugin -Dversion=12.1.3 -DgeneratePom=true

Secondly, add below dependencies in your pom file which belong to the maven sub module that calls payment service

        <dependency>
                <groupId>javax.xml.rpc</groupId>
                <artifactId>api</artifactId>
                <version>1.1</version>
    </dependency>
        <dependency>
                <groupId>com.oracle.webservices.wl</groupId>
                <artifactId>wls-soap-stack-impl</artifactId>
                <version>12.1.3</version>
    </dependency> 


    <dependency>
                <groupId>com.comcast.billing.payment</groupId>
                <artifactId>PaymentService-Client</artifactId>
                <version>16.13-SNAPSHOT</version>
    </dependency>

Last, incorporate the sample code in App.java into your sub module which calls payment service 

Good luck!
