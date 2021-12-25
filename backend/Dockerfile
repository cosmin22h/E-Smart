FROM maven:3.6.3-jdk-11 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/backend-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/backend-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre

ENV TZ=UTC
ENV DB_IP=_
ENV DB_PORT=5432
ENV DB_USER=_
ENV DB_PASSWORD=_
ENV DB_DBNAME=_
ENV RABBIT_URI=_
ENV RABBIT_QNAME=measurements
ENV RABBIT_QDURABLE=false
ENV RABBIT_QEXCLUSIVE=false
ENV RABBIT_QAUT0_DELETE=false

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]
