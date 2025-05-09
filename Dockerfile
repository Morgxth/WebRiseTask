# Используем официальный образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar файл в контейнер
COPY target/restapi-0.0.1-SNAPSHOT.jar /app/restapi.jar

# Указываем команду для запуска приложения
CMD ["java", "-jar", "restapi.jar"]
