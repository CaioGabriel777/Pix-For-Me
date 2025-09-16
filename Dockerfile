# Estágio 1: Build da Aplicação com Maven e JDK
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia o pom.xml e baixa as dependências (cache inteligente)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn package -DskipTests

# Estágio 2: Execução com JRE
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o jar da build
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
