# Ticketsystem

Ein kleines Ticketsystem als Konsolenanwendung in Java, mit automatisierten Tests.

Das Projekt zeigt, wie ich Software so baue, dass sie sich zuverlässig prüfen lässt: klare Trennung von Logik und Bedienung, Eingabeprüfung und Tests für Normalfälle und Fehlerfälle. Die Tests laufen bei jedem Upload automatisch über GitHub Actions.

## Funktionen

- Tickets mit Titel, Beschreibung und Priorität (niedrig, mittel, hoch) anlegen
- Status ändern: offen, in Bearbeitung, erledigt
- Tickets nach Status filtern
- Tickets nach Stichwort in Titel und Beschreibung suchen (ohne Groß-/Kleinschreibung)
- Offene Tickets nach Priorität sortiert anzeigen
- Regel: Ein erledigtes Ticket muss erst wieder geöffnet werden, bevor es auf "in Bearbeitung" gesetzt werden kann

## Aufbau

| Klasse | Aufgabe |
|---|---|
| `Ticket` | Ein einzelnes Ticket mit Prüfung der Eingaben und der Statusregel |
| `Status`, `Prioritaet` | Aufzählungen mit Einlesen aus Nutzereingaben |
| `TicketService` | Fachlogik: anlegen, finden, ändern, filtern, suchen, sortieren |
| `Main` | Konsolenmenü, enthält keine Fachlogik |

Die Fachlogik liegt bewusst getrennt von der Bedienung. Dadurch lässt sie sich ohne Eingabe über die Konsole testen.

## Tests

Die Tests mit JUnit 5 prüfen unter anderem:

- neue Tickets sind offen, leere Titel werden abgelehnt
- die Statusregel für erledigte Tickets
- fortlaufende Ticketnummern, Suche, Filter und Sortierung
- Fehlerfälle: unbekannte Ticketnummer, ungültiger Status, leere Suche

Tests ausführen:

```
mvn test
```

## Starten

Voraussetzung: Java 17 oder neuer und Maven.

```
mvn package
java -jar target/ticketsystem-1.0.0.jar
```

Unter Windows können Umlaute in der Konsole falsch dargestellt werden. Dann hilft vor dem Start `chcp 65001`.

## Automatische Tests

Die Datei `.github/workflows/tests.yml` führt bei jedem Push und jedem Pull Request alle Tests aus. Ob sie bestanden sind, zeigt der Reiter "Actions" auf GitHub.

## Mögliche Erweiterungen

- Tickets in einer Datei speichern und beim Start wieder laden
- Bearbeiter zuweisen und Kommentare ergänzen
- Tests zusätzlich mit Playwright oder Selenium für eine Weboberfläche

## Autor

Marco Elster · Fachinformatiker Softwareentwicklung (IHK) · [Dein GitHub-Profil]
