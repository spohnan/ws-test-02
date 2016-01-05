# Full Oracle JDK with Maven

FROM spohnan/alpine-jdk8
MAINTAINER Andy@AndySpohn.com

ENV MAVEN_VERSION 3.2.2

# Add Maven to build applications
RUN curl -jksSL http://archive.apache.org/dist/maven/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    | tar -xzf - -C /opt && ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven

# Set environment
ENV MAVEN_HOME /opt/maven
ENV PATH ${PATH}:${MAVEN_HOME}/bin
