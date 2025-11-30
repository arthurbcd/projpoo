# Dossier du Projet — Gestion de Formations

Date: 30/11/2025
Équipe: (à compléter)
Contributions: (à compléter)

## 1. Introduction
Ce projet implémente un système minimal de gestion des formations, des sessions, des formateurs et des apprenants, avec inscriptions et liste d’attente. L’objectif est de répondre aux exigences essentielles: catalogue, sessions avec états, inscription conditionnelle, notification automatique.

## 2. Architecture (MVC)
- **Modèle**: `Course`, `Session`, `Student`, `Teacher`, `Inscription`, `Category`.
- **Contrôleur / Service**: `CourseManager` et `SessionManager` (Singletons) gèrent le cycle de vie et les actions.
- **Vue**: `App` (JavaFX en code, sans FXML) affiche les listes et boutons d’action.

## 3. Structure de Classes
```
com.proj.model
  ├─ Course(title, durationHours, category, sessions)
  ├─ Session(course, startAt, endAt, maxPlaces, teacher, waitlist)
  ├─ Student(id, name, email)
  ├─ Teacher(id, name, email, specialties)
  ├─ Inscription(student, session, completed)
  └─ Category (enum)

com.proj.state (State Pattern)
  ├─ SessionState (interface)
  ├─ OpenSessionState
  ├─ FullSessionState
  ├─ StartedSessionState
  ├─ CanceledSessionState
  └─ EndedSessionState

com.proj.observer (Observer Pattern)
  ├─ Observable
  └─ Observer

com.proj.manager (Singleton)
  ├─ CourseManager
  └─ SessionManager
```

## 4. Fonctionnalités Implémentées
- **Catalogue de formations**: création, liste et suppression (basique) via `CourseManager`.
- **Sessions de formation**: associées à une formation et à un formateur; gestion des dates et des places.
- **États de session**: ouverte, complète, démarrée, annulée, terminée (State).
- **Acteurs**: formateur avec spécialités; apprenant avec inscriptions et statut terminé.
- **Inscriptions**:
  - Si la session n’est pas complète: inscription immédiate.
  - Si complète: ajout en **liste d’attente**.
  - **Promotion automatique** de la liste d’attente lorsqu’une place se libère.
- **Notifications** (Observer): formateur et apprenants sont notifiés lors des changements d’état et des événements de capacité (session complète, promotion d’attente).

## 5. Design Patterns Utilisés
- **State**: encapsule les règles de transition d’état des sessions.
- **Observer**: `Session` notifie `Teacher` et `Student` sur les changements.
- **Singleton**: `CourseManager`, `SessionManager` garantissent une instance unique et simplifient le CRUD en mémoire.

## 6. Choix de Structures de Données
- **Stockage en mémoire** (conforme aux contraintes):
  - `Map<String, Course>` pour le catalogue (`CourseManager`).
  - `Map<String, Session>` pour les sessions (`SessionManager`).
  - `Map<String, Student>` dans `Session` pour les inscrits.
  - `Queue<Student>` (LinkedList) pour la **liste d’attente**.
  - `Map<String, Inscription>` pour l’historique d’inscriptions par session.
- Justification: simplicité, conformité aux bibliothèques standard de Java et caractère minimal de la livraison.

## 7. Conformité aux Contraintes Techniques
- **Sans FXML**: interface JavaFX construite entièrement en **code**; fichiers FXML supprimés.
- **Sans FXCollections**: utilisation de collections standard (`List`, `Map`, `Queue`).

## 8. Scénarios d’Utilisation (Résumé)
1. Ajout de la formation "Java pour débutants".
2. Planification d’une session (dates + formateur).
3. Inscriptions: à la saturation, les nouvelles demandes sont placées en liste d’attente.
4. Libération de place: promotion du premier en attente.
5. Fin du mois: possibilité de rapport (non implémenté dans cette livraison minimale).

## 9. Limitations et Extensions Futures
- **Non implémenté**: pré-requis de formations, reporting détaillé, types de sessions (présentiel/online/hybride), Decorator/Factory.
- **Évolutions**: ajout de validation des pré-requis, vues séparées MVC, export de rapports.

## 10. Qualité et Lisibilité du Code
- Code structuré par packages et patterns.
- Interface simple, claire, et entièrement en JavaFX (sans FXML).
- Compilation vérifiée via Maven (`mvn compile`).

## 11. Instructions de Compilation / Exécution
```bash
mvn compile
# Exécution (si plugin JavaFX configuré)
mvn javafx:run
```

## 12. Annexe — Diagramme ASCII
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
                                   |State| (Session states)
                                   +-----+
```
