FROM bellsoft/liberica-openjdk-alpine:15.0.2
EXPOSE 8080
COPY target/instaweb-0.0.1.jar .
ENTRYPOINT ["java", "-jar", "/instaweb-0.0.1.jar"]

