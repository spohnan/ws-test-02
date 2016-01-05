# Web Service Artifact with JRE 8

FROM spohnan/alpine-jre8
MAINTAINER Andy@AndySpohn.com

COPY target/ws-test-02.jar /opt/ws-test-02.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/ws-test-02.jar"]
