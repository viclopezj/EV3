FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copiamos las herramientas del Maven Wrapper del proyecto
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml .

# Damos permisos de ejecución al script del wrapper
RUN chmod +x mvnw

# Descargamos dependencias usando la caché de BuildKit y el wrapper local
RUN --mount=type=cache,id=maven-cache,target=/root/.m2,sharing=locked \
    ./mvnw dependency:go-offline -B

COPY src ./src

# Compilamos usando el wrapper local y la caché
RUN --mount=type=cache,id=maven-cache,target=/root/.m2,sharing=locked \
    ./mvnw clean package -DskipTests

# 2 EJECUCION
FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]