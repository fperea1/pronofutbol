FROM tomcat:9.0-jre11
RUN addgroup --system -gid 1000 tomcat && adduser --system -uid 1000 -gid 1000 tomcat
RUN chown -R tomcat:tomcat /usr/local/tomcat
USER tomcat
COPY ./target/baseRest.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]