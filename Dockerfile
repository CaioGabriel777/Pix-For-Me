# Estágio 1: Build da Aplicação com Maven e JDK
# Usamos uma imagem que já contém o Maven e o JDK 17
FROM maven:3.9-eclipse-temurin-17 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml para o container e baixa as dependências.
# Isso aproveita o cache do Docker. As dependências só serão baixadas novamente se o pom.xml mudar.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código-fonte
COPY src ./src

# Compila a aplicação e gera o arquivo .jar, pulando os testes
RUN mvn package -DskipTests

# Estágio 2: Execução da Aplicação com JRE
# Usamos uma imagem enxuta, contendo apenas o Java Runtime Environment (JRE)
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build para a imagem final
# O wildcard (*) pega o jar gerado sem precisar saber o nome exato
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]