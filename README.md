# Pix For Me API 🚀

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring)
![Maven](https://img.shields.io/badge/Maven-4-red?style=for-the-badge&logo=apache-maven)

API REST simples e eficiente para gerar QR Codes e códigos "Copia e Cola" do PIX de forma dinâmica. Desenvolvida com Spring Boot, esta aplicação foi criada para facilitar a geração de cobranças PIX com valores definidos pelo usuário.

---

## 📍 Sumário

* [✨ Funcionalidades](#-funcionalidades)
* [🛠️ Tecnologias Utilizadas](#️-tecnologias-utilizadas)
* [🕹️ Como Usar a API](#️-como-usar-a-api)

---

## ✨ Funcionalidades

-   ✅ **Geração de QR Code PIX:** Cria uma imagem de QR Code em formato PNG com base em um valor monetário.
-   ✅ **Geração de PIX Copia e Cola:** Gera a string do BRCode (payload) que pode ser usada na funcionalidade "PIX Copia e Cola" dos aplicativos bancários.
-   ✅ **Validação de Entrada:** Garante que o valor fornecido para a transação seja válido.
-   ✅ **API RESTful:** Endpoints claros e bem definidos para uma fácil integração.

---

## 🛠️ Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias:

-   **[Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html):** Linguagem de programação principal.
-   **[Spring Boot 3](https://spring.io/projects/spring-boot):** Framework para criação de aplicações Java de forma rápida e robusta.
-   **[Spring Web](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html):** Módulo para criação de APIs RESTful.
-   **[Maven](https://maven.apache.org/):** Ferramenta para gerenciamento de dependências e build do projeto.
-   **[ZXing (Zebra Crossing)](https://github.com/zxing/zxing):** Biblioteca para geração de QR Codes.

---


## 🕹️ Como Usar a API

A API expõe dois endpoints principais para a geração de cobranças PIX.

### 1. Gerar QR Code PIX

Este endpoint retorna uma imagem PNG do QR Code.

-   **Endpoint:** `GET /api/pix/gerar-qrcode`
-   **Parâmetro:** `valor` (obrigatório) - O valor da transação.
-   **Resposta de Sucesso (200 OK):** Uma imagem PNG.

**Exemplo de uso (URL para o navegador ou cliente de API):**

### 2. Gerar PIX Copia e Cola

Este endpoint retorna a string do QRCode, pronta para ser copiada.

-   **Endpoint:** `GET /api/pix/gerar-copia-e-cola`
-   **Parâmetro:** `valor` (obrigatório) - O valor da transação.
-   **Resposta de Sucesso (200 OK):** Uma string de texto (`text/plain`).

**Exemplo de uso (URL para o navegador ou cliente de API):**
