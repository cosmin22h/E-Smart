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
ENV DB_IP=ec2-52-208-185-143.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=cqpjbzfkngyvdp
ENV DB_PASSWORD=3f3c10246835af954e357799cd5a0f0520fe533e54e1936f6fd3d8afe1048f26
ENV DB_DBNAME=dcik1k0hhk9m11
ENV RABBIT_URI=amqps://euitndrk:KsCrKJuRAn3cNSwJi4CAaXoD_kj3qFRV@kangaroo.rmq.cloudamqp.com/euitndrk
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
