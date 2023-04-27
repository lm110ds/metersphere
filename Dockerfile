FROM metersphere/fabric8-java-alpine-openjdk8-jre

LABEL maintainer="hanlei32@faw.com.cn"

ARG MS_VERSION=dev
ARG DEPENDENCY=backend/target/dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /opt/lib
COPY ${DEPENDENCY}/META-INF /opt/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /opt

ENV JAVA_CLASSPATH=/opt:/opt/lib/ms-jmeter-core.jar:/opt/lib/*
ENV JAVA_MAIN_CLASS=io.metersphere.Application
ENV AB_OFF=true
ENV MS_VERSION=${MS_VERSION}
ENV JAVA_OPTIONS="-Dfile.encoding=utf-8 -Djava.awt.headless=true"

RUN mv /opt/lib/ms-jmeter-core*.jar /opt/lib/ms-jmeter-core.jar

EXPOSE 8080

CMD ["/deployments/run-java.sh"]
