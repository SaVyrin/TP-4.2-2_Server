FROM openjdk:8
ADD ./tp4_2-2_server.jar tp4_2-2_server.jar
ENTRYPOINT ["java", "-jar", "/tp4_2-2_server.jar"]