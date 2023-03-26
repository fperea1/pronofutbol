FROM maven AS maven_builder
WORKDIR /app
ADD pom.xml /app
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]
ADD . /app
RUN ["mvn", "clean", "package", "-Ppro", "-DskipTests"]

FROM tomcat:latest
RUN addgroup --system -gid 1000 tomcat && adduser --system -uid 1000 -gid 1000 tomcat
RUN chown -R tomcat:tomcat /usr/local/tomcat
USER tomcat
COPY --from=maven_builder /app/target/pronofutbol.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]