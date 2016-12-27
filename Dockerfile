FROM neaftw/nea-scalatra:1.0

WORKDIR /home/docker

COPY src/ /home/docker/tipz/src
COPY project/build.properties /home/docker/tipz/project/
COPY project/plugins.sbt /home/docker/tipz/project/
COPY build.sbt /home/docker/tipz/
COPY sbt /home/docker/tipz/

RUN cd /home/docker/tipz && ./sbt package
RUN mv /home/docker/tipz/target/scala-2.11/tipz_2.11-0.1.0-SNAPSHOT.war /home/docker/apache-tomcat-8.5.9/webapps
RUN cp /home/docker/apache-tomcat-8.5.9/webapps/tipz_2.11-0.1.0-SNAPSHOT.war /home/docker/apache-tomcat-8.5.9/webapps/tipz.war

ENTRYPOINT ["/home/docker/apache-tomcat-8.5.9/bin/catalina.sh", "run"]

EXPOSE 80 8080 3306
