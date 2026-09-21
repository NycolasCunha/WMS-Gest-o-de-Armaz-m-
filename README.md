# WMS Joinville - Sistema de Gestão de Armazém

> Sistema de Gestão de Armazém (Warehouse Management System) desenvolvido em **Java 17** e **Spring Boot 3** durante um desafio de **30 dias de commits diários**, inspirado nos cenários e regras operacionais do polo industrial e logístico de Joinville/SC.

![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3.2](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-Database-1021FF?style=for-the-badge&logo=databricks&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Status](https://img.shields.io/badge/Status-Em_Desenvolvimento-yellow?style=for-the-badge)

---

## Sumário

- [Contexto e Problema](#contexto-e-problema)
- [Pilares do Sistema](#pilares-do-sistema)
- [Stack Tecnológica](#stack-tecnológica)
- [Arquitetura de Pastas](#arquitetura-de-pastas)
- [Como Executar](#como-executar)
- [Roadmap de 30 Dias](#roadmap-de-30-dias)
- [Convenções de Commit](#convenções-de-commit)
- [Licença](#licença)

---

## Contexto e Problema

Joinville abriga um dos maiores ecossistemas industriais e logísticos do Sul do país, englobando setores metal-mecânico, autopeças, químicos e plásticos. Com o fluxo intenso ligado à BR-101, BR-280 e proximidade com os portos de Itapoá e São Francisco do Sul, a eficiência operacional de estocagem é um fator crítico.

Este projeto simula e resolve gargalos comuns em armazéns que dependem de processos manuais:

| Desafio Operacional | Impacto Negativo | Solução no WMS |
|---|---|---|
| Endereçamento manual fixo | Vagas subutilizadas e perda de tempo na busca | Algoritmo de sugestão de vaga por categoria |
| Ausência de controle de lote | Retenção de produtos antigos e perdas por validade | Regras estritas de saída PEPS/FIFO e FEFO |
| Monitoramento manual de itens | Falhas humanas em alertas de estoque e vencimento | Agendamento automatizado via `@Scheduled` |
| Mistura de cargas incompatíveis | Risco operacional e descumprimento de normas | Mapeamento por zonas de estocagem segregadas |

---

## Pilares do Sistema

### 1. Endereçamento Dinâmico (Putaway)
O sistema analisa as características do item no momento do recebimento e sugere a posição ideal considerando:
* Categoria do produto e zonas compatíveis (Secos, Refrigerados, Inflamáveis, Frágeis, etc.).
* Capacidade e status da vaga (`LIVRE`, `OCUPADA`, `RESERVADA`, `BLOQUEADA`).
* Estrutura visual padronizada de endereçamento: **Rua - Prédio - Nível - Apartamento** (ex: `A-03-02-04`).

### 2. Controle de Saída PEPS / FIFO e FEFO
* **PEPS / FIFO (First In, First Out):** Prioriza a saída dos lotes com data de entrada mais antiga para produtos gerais.
* **FEFO (First Expired, First Out):** Aplica prioridade máxima aos lotes com menor prazo de validade para itens perecíveis e farmacêuticos.

### 3. Processos Automatizados (`@Scheduled`)
Tarefas em segundo plano executadas periodicamente para auditoria de estoque:
* Varredura diária de produtos próximos do vencimento.
* Notificação de itens operando abaixo do estoque mínimo de segurança.
* Liberação ou alerta de vagas bloqueadas há muito tempo.

---

## Stack Tecnológica

| Camada | Tecnologia |
|---|---|
| **Linguagem** | Java 17 |
| **Framework** | Spring Boot 3.2+ |
| **Persistência** | Spring Data JPA + Hibernate |
| **Banco de Dados** | H2 Database (Desenvolvimento em memória) |
| **Validação** | Jakarta Bean Validation |
| **Camada Visão** | Thymeleaf + Bootstrap 5 |
| **Produtividade** | Lombok |
| **Build & Testes** | Maven 3.9+ / JUnit 5 |

---

## Arquitetura de Pastas

Organização em camadas separadas por responsabilidades do domínio Spring:

com.logistica.wms
├── config         # Configurações de beans e agendamentos
├── controller     # Endpoints REST e controllers Thymeleaf
├── domain
│   ├── enums      # Domínios fixos (CategoriaProduto, StatusVaga)
│   └── model      # Entidades do banco de dados (JPA)
├── dto            # Data Transfer Objects
├── exception      # Exceções customizadas e RestControllerAdvice
├── repository     # Interfaces Spring Data JPA
├── scheduler      # Rotinas de auditoria agendadas
└── service        # Lógica e regras de negócio


---

## Como Executar

### Pré-requisitos
* **JDK 17** ou superior instalado.
* **Maven 3.9+** configurado (ou via wrapper).

```bash
# 1. Clonar o repositório
git clone https://github.com/NycolasCunha/WMS-Gest-o-de-Armaz-m-.git

# 2. Entrar na pasta do projeto
cd WMS-Gest-o-de-Armaz-m-

# 3. Executar a bateria de testes unitários
mvn test

# 4. Executar a aplicação
mvn spring-boot:run
Após a inicialização:

Aplicação Web: http://localhost:8080

Console Banco H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:wmsdb

Usuário: sa

Senha: (deixar em branco)
```
## Roadmap de 30 Dias

Semana 1 — Estrutura e Domínio

[x] Dia 01 — Configuração inicial, estrutura Maven e Enums de domínio.

[ ] Dia 02 — Criação da entidade Produto e ProdutoRepository.

[ ] Dia 03 — Criação da entidade Vaga (endereçamento lógico).

[ ] Dia 04 — Entidade Lote (controle de validade e saldo).

[ ] Dia 05 — Mapeamento DTOs e regras de Bean Validation.

[ ] Dia 06 — Implementação do Service e Controller REST de Produtos.

[ ] Dia 07 — Testes unitários do domínio e revisão semanal.

Semana 2 — Endereçamento e Recebimento
[ ] Dia 08 — Gerenciamento de vagas e geração de endereços em massa.

[ ] Dia 09 — Algoritmo de sugestão de vaga por categoria.

[ ] Dia 10 — Fluxo de entrada e recebimento de mercadorias.

[ ] Dia 11 — Processo de armazenagem (Putaway) e atualização de status.

[ ] Dia 12 — Histórico de movimentações (Kardex).

[ ] Dia 13 — Handler global de tratamento de exceções.

[ ] Dia 14 — Testes de integração do fluxo de recebimento.

Semana 3 — Regras PEPS/FEFO e Separação
[ ] Dia 15 — Algoritmo de seleção de lotes por PEPS/FIFO.

[ ] Dia 16 — Baixa de estoque automatizada por ordem de entrada.

[ ] Dia 17 — Implementação da priorização FEFO para perecíveis.

[ ] Dia 18 — Fluxo de ordens de saída e expedição.

[ ] Dia 19 — Geração de listas de separação (Picking List).

[ ] Dia 20 — Etapa de conferência e baixa física.

[ ] Dia 21 — Refatoração e expansão de suíte de testes.

Semana 4 — Alertas, Interface e Finalização
[ ] Dia 22 — Tarefa agendada (@Scheduled) para alerta de vencimento.

[ ] Dia 23 — Tarefa agendada para auditoria de estoque mínimo.

[ ] Dia 24 — Módulo de inventário cíclico.

[ ] Dia 25 — Dashboard visual do armazém com Thymeleaf.

[ ] Dia 26 — Interface web para consulta e operações.

[ ] Dia 27 — Relatórios operacionais e taxa de ocupação.

[ ] Dia 28 — Documentação da API com OpenAPI/Swagger.

[ ] Dia 29 — Dockerfile e preparação do ambiente de produção.

[ ] Dia 30 — Revisão final, documentação e Release v1.0.0.
###
## Convenções de Commit

Este repositório adota o padrão Conventional Commits:

feat: Novas funcionalidades.

fix: Correção de bugs.

refactor: Alterações de código sem modificação de comportamento.

test: Adição ou ajuste de testes unitários/integração.

docs: Atualizações na documentação.

chore: Tarefas de manutenção, dependências e configurações do projeto.
###
Licença
Este projeto está sob a licença MIT.
