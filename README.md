# Course Management System (Minimal Requirements)

Este projeto implementa os requisitos mínimos solicitados:

## Requisitos Cobertos
- Catálogo de cursos (CRUD básico via `CourseManager` Singleton).
- Sessões associadas a cursos e a um formador (`Session` contém `Teacher`).
- Estados de sessão: aberta, cheia, iniciada, cancelada, encerrada (State Pattern).
- Inscrição de alunos com fila de espera (waitlist) quando sessão cheia.
- Notificação automática (Observer Pattern) para alunos e professor em mudanças de estado e eventos de capacidade.
- Marcação de inscrições como concluídas ao encerrar sessão.
- Singleton Managers (`CourseManager`, `SessionManager`).
- Remoção de FXML (interface construída integralmente em código JavaFX).

## Diagrama (ASCII Simplificado)
```
				+-----------------+              +-----------------+
				|  CourseManager  |<>----------->|     Course      |
				+--------+--------+              +--------+--------+
							|                               | 1..*
							| cria                           | contém
							v                               v
				+-----------------+              +-----------------+
				| SessionManager  |<>----------->|    Session      |
				+--------+--------+              +--------+--------+
							|                               | 1..*
							| cria                           | inscritos / waitlist
							v                               v
				+-----------------+    observes   +-----------------+
				|    Teacher      |<--------------|    Student      |
				+-----------------+   notifica    +-----------------+
								 ^                          ^
								 | observer                 |
								 +------------+-------------+
												  |
											  +--v--+
											  |State| (Strategy via State pattern)
											  +-----+
```
Padrões: State (transições Session), Observer (Session -> Teacher/Students), Singleton (Managers).

## Padrões de Projeto
- State: classes em `com.proj.state.*` controlam transições de estado da sessão.
- Observer: `Session` estende `Observable`; alunos e professor recebem notificações.
- Singleton: `CourseManager` e `SessionManager` controlam ciclo de vida de cursos e sessões.

## Alterações Principais
1. Remoção de arquivos FXML e controllers (`primary.fxml`, `secondary.fxml`, `PrimaryController`, `SecondaryController`).
2. Inclusão de `teacher` e `waitlist` em `Session` com promoção automática ao liberar vaga.
3. Notificação ao professor quando sessão fica cheia.
4. Managers Singletons para operações básicas em memória.
5. Marcação de inscrições como completas em `Session.end()`.

## Como Executar
```bash
mvn compile
mvn javafx:run   # se plugin configurado, caso contrário executar classe App
```

## Extensões Futuras (Não Implementadas)
- Pré-requisitos de cursos.
- Decorator para adicionar opções (certificação, suporte extra).
- Factory para tipos de sessão (presencial, online, híbrida).
- Relatórios de ocupação e histórico avançado.

---
Projeto reduzido ao mínimo para atender os requisitos essenciais.
