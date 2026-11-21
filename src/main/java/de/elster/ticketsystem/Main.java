package de.elster.ticketsystem;

import java.util.List;
import java.util.Scanner;

/** Einfache Konsolenanwendung für das Ticketsystem. */
public class Main {

    public static void main(String[] args) {
        TicketService service = new TicketService();
        try (Scanner eingabe = new Scanner(System.in)) {
            boolean laeuft = true;
            while (laeuft) {
                menueAnzeigen();
                String wahl = eingabe.nextLine().trim();
                try {
                    switch (wahl) {
                        case "1" -> ticketAnlegen(service, eingabe);
                        case "2" -> liste("Alle Tickets", service.alle());
                        case "3" -> statusAendern(service, eingabe);
                        case "4" -> nachStatusAnzeigen(service, eingabe);
                        case "5" -> suchen(service, eingabe);
                        case "6" -> liste("Offene Tickets nach Priorität", service.offeneNachPrioritaet());
                        case "0" -> laeuft = false;
                        default -> System.out.println("Bitte eine Zahl aus dem Menü eingeben.");
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Fehler: " + e.getMessage());
                }
            }
        }
        System.out.println("Auf Wiedersehen!");
    }

    private static void menueAnzeigen() {
        System.out.println();
        System.out.println("=== Ticketsystem ===");
        System.out.println("1 Ticket anlegen");
        System.out.println("2 Alle Tickets anzeigen");
        System.out.println("3 Status ändern");
        System.out.println("4 Tickets nach Status anzeigen");
        System.out.println("5 Tickets suchen");
        System.out.println("6 Offene Tickets nach Priorität");
        System.out.println("0 Beenden");
        System.out.print("Auswahl: ");
    }

    private static void ticketAnlegen(TicketService service, Scanner eingabe) {
        System.out.print("Titel: ");
        String titel = eingabe.nextLine();
        System.out.print("Beschreibung: ");
        String beschreibung = eingabe.nextLine();
        System.out.print("Priorität (niedrig/mittel/hoch): ");
        Prioritaet prioritaet = Prioritaet.ausText(eingabe.nextLine());
        Ticket t = service.anlegen(titel, beschreibung, prioritaet);
        System.out.println("Angelegt: " + t);
    }

    private static void statusAendern(TicketService service, Scanner eingabe) {
        System.out.print("Ticketnummer: ");
        int id = zahlLesen(eingabe.nextLine());
        System.out.print("Neuer Status (offen/in bearbeitung/erledigt): ");
        Status status = Status.ausText(eingabe.nextLine());
        System.out.println("Geändert: " + service.statusAendern(id, status));
    }

    private static void nachStatusAnzeigen(TicketService service, Scanner eingabe) {
        System.out.print("Status (offen/in bearbeitung/erledigt): ");
        Status status = Status.ausText(eingabe.nextLine());
        liste("Tickets mit Status " + status.getAnzeige(), service.nachStatus(status));
    }

    private static void suchen(TicketService service, Scanner eingabe) {
        System.out.print("Suchbegriff: ");
        liste("Suchergebnis", service.suchen(eingabe.nextLine()));
    }

    private static void liste(String titel, List<Ticket> tickets) {
        System.out.println("--- " + titel + " ---");
        if (tickets.isEmpty()) {
            System.out.println("(keine Tickets)");
        }
        tickets.forEach(System.out::println);
    }

    private static int zahlLesen(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Bitte eine ganze Zahl eingeben");
        }
    }
}
