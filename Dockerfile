FROM openjdk:17-oracle
EXPOSE 8080
ADD target/spring-openai.jar spring-openai.jar
ENTRYPOINT ["java","-jar","/spring-openai.jar"]