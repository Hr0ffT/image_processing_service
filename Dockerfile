FROM openjdk:latest


COPY target/image_processing_service-1.0-SNAPSHOT-jar-with-dependencies.jar image_processing_service-1.0-SNAPSHOT-jar-with-dependencies.jar


ENTRYPOINT ["java", "-jar", "image_processing_service-1.0-SNAPSHOT-jar-with-dependencies.jar", "-Xmx300m", "-Xms300m"]


