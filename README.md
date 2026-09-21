# 📦 WMS Joinville

> Sistema de Gestão de Armazém (Warehouse Management System) construído em **30 dias** de commits diários, com foco nas dores reais do polo logístico de **Joinville/SC**.

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![H2](https://img.shields.io/badge/H2-Database-1021FF?style=for-the-badge&logo=databricks&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Status](https://img.shields.io/badge/status-em_desenvolvimento-yellow?style=for-the-badge)
![Desafio](https://img.shields.io/badge/desafio-30_dias-blueviolet?style=for-the-badge)
![Licença](https://img.shields.io/badge/licença-MIT-blue?style=for-the-badge)

---

## 📑 Sumário

- [Contexto e problema](#-contexto-e-problema)
- [Pilares do sistema](#-pilares-do-sistema)
- [Stack tecnológica](#-stack-tecnológica)
- [Arquitetura](#-arquitetura)
- [Como executar](#-como-executar)
- [Roadmap de 30 dias](#-roadmap-de-30-dias)
- [Convenções de commit](#-convenções-de-commit)
- [Licença](#-licença)

---

## 🏭 Contexto e problema

Joinville concentra um dos maiores parques industriais e logísticos do Sul do Brasil: metal-mecânico, autopeças, eletroeletrônicos, plásticos, linha branca, têxtil e um crescente número de centros de distribuição instalados ao longo da BR-101 e da BR-280, com acesso ao Porto de Itapoá e ao Porto de São Francisco do Sul.

Esse ambiente cria desafios operacionais recorrentes para armazéns de pequeno e médio porte, que muitas vezes ainda operam com planilhas:

| Dor operacional | Consequência |
|---|---|
| Endereçamento fixo e manual | Vagas subutilizadas, produtos "perdidos" e picking lento |
| Falta de controle PEPS/FIFO | Lotes antigos ficam parados até vencer ou ficar obsoletos |
| Produtos com validade sem monitoramento | Perdas financeiras e risco sanitário/regulatório |
| Mistura de cargas incompatíveis (inflamáveis, frágeis, refrigerados) | Risco de acidentes e não conformidade |
| Sem histórico de movimentação | Divergências de inventário sem rastreabilidade |

O **WMS Joinville** nasce para resolver essas dores com uma base sólida, testável e evolutiva.

---

## 🎯 Pilares do sistema

### 1. Endereçamento dinâmico
Em vez de posições fixas por produto, o sistema **sugere a melhor vaga disponível** no momento do recebimento, considerando:

- Categoria do produto e zona de estocagem compatível (seco, refrigerado, inflamável, frágil, etc.)
- Status da vaga (`LIVRE`, `OCUPADA`, `RESERVADA`, `BLOQUEADA`, `EM_MANUTENCAO`)
- Proximidade com a doca e giro do item
- Capacidade de peso e volume da vaga

Endereço lógico no formato **Rua-Prédio-Nível-Apartamento** (ex.: `A-03-02-04`).

### 2. PEPS / FIFO (Primeiro que Entra, Primeiro que Sai)
A baixa de estoque respeita a ordem cronológica de entrada dos lotes. Para itens perecíveis, o critério evolui para **FEFO** (First Expired, First Out), priorizando a menor data de validade.

### 3. Alertas automáticos com `@Scheduled`
Rotinas agendadas que monitoram o armazém sem intervenção humana:

- ⏰ Lotes próximos do vencimento
- 📉 Produtos abaixo do estoque mínimo
- 🔒 Vagas bloqueadas por tempo excessivo

---

## 🛠 Stack tecnológica

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.2 |
| API REST | Spring Web |
| Persistência | Spring Data JPA + Hibernate |
| Banco de dados | H2 (em memória, desenvolvimento) |
| Validação | Jakarta Bean Validation |
| Visão | Thymeleaf |
| Produtividade | Lombok |
| Build | Maven |
| Testes | JUnit 5 + Spring Boot Test |

---

## 🧱 Arquitetura

Arquitetura em camadas, com separação clara de responsabilidades:

```
com.logistica.wms
├── config         # Configurações (beans, OpenAPI, agendamentos)
├── controller     # Endpoints REST e controllers MVC
├── domain
│   ├── enums      # CategoriaProduto, StatusVaga, ...
│   └── model      # Entidades JPA
├── dto            # Objetos de entrada/saída da API
├── exception      # Exceções de negócio e handler global
├── repository     # Interfaces Spring Data JPA
├── scheduler      # Tarefas @Scheduled
└── service        # Regras de negócio
```

---

## 🚀 Como executar

**Pré-requisitos:** JDK 17+ e Maven 3.9+.

```bash
# Clonar o repositório
git clone https://github.com/SEU_USUARIO/wms-joinville.git
cd wms-joinville

# Executar os testes
mvn test

# Subir a aplicação
mvn spring-boot:run
```

Após iniciar:

- Aplicação: http://localhost:8080
- Console do H2: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:wmsdb`
  - Usuário: `sa`
  - Senha: *(vazia)*

---

## 🗓 Roadmap de 30 dias

### Semana 1 — Fundação do domínio
- [x] **Dia 01** — Setup do projeto, configuração e enums de domínio
- [ ] **Dia 02** — Entidade `Produto` e `ProdutoRepository`
- [ ] **Dia 03** — Entidade `Vaga` com endereço Rua-Prédio-Nível-Apartamento
- [ ] **Dia 04** — Entidade `Lote` (validade, quantidade, data de entrada)
- [ ] **Dia 05** — DTOs e Bean Validation
- [ ] **Dia 06** — CRUD de Produto (Service + Controller REST)
- [ ] **Dia 07** — Refatoração, testes unitários e revisão da semana

### Semana 2 — Endereçamento e recebimento
- [ ] **Dia 08** — CRUD de Vagas e geração em massa de endereços
- [ ] **Dia 09** — Algoritmo de endereçamento dinâmico (sugestão de vaga por categoria)
- [ ] **Dia 10** — Recebimento de mercadoria (entrada)
- [ ] **Dia 11** — Armazenagem (put-away) com atualização de status da vaga
- [ ] **Dia 12** — Histórico de movimentações (kardex)
- [ ] **Dia 13** — Tratamento global de exceções (`@RestControllerAdvice`)
- [ ] **Dia 14** — Testes de integração e revisão da semana

### Semana 3 — Saída, PEPS/FIFO e picking
- [ ] **Dia 15** — Seleção de lotes por PEPS/FIFO
- [ ] **Dia 16** — Baixa de estoque respeitando PEPS
- [ ] **Dia 17** — FEFO para produtos perecíveis
- [ ] **Dia 18** — Pedido de saída e expedição
- [ ] **Dia 19** — Geração de lista de picking
- [ ] **Dia 20** — Separação e conferência
- [ ] **Dia 21** — Refatoração, cobertura de testes e revisão da semana

### Semana 4 — Automação, interface e entrega
- [ ] **Dia 22** — `@Scheduled`: alerta de lotes próximos do vencimento
- [ ] **Dia 23** — `@Scheduled`: alerta de estoque mínimo
- [ ] **Dia 24** — Inventário cíclico
- [ ] **Dia 25** — Dashboard com Thymeleaf
- [ ] **Dia 26** — Telas de cadastro e consulta com Thymeleaf
- [ ] **Dia 27** — Relatório de ocupação do armazém
- [ ] **Dia 28** — Documentação da API com OpenAPI/Swagger
- [ ] **Dia 29** — Dockerfile, docker-compose e perfil PostgreSQL
- [ ] **Dia 30** — Documentação final, revisão e release `v1.0.0`

---

## ✍️ Convenções de commit

Seguimos o padrão [Conventional Commits](https://www.conventionalcommits.org/pt-br/):

| Prefixo | Uso |
|---|---|
| `feat:` | Nova funcionalidade |
| `fix:` | Correção de bug |
| `refactor:` | Refatoração sem mudança de comportamento |
| `test:` | Criação ou ajuste de testes |
| `docs:` | Documentação |
| `chore:` | Configuração, build e manutenção |

---

## 📄 Licença

Distribuído sob a licença MIT. Consulte o arquivo `LICENSE` para mais informações.
