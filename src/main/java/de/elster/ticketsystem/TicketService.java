package de.elster.ticketsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Verwaltet alle Tickets im Arbeitsspeicher. */
public class TicketService {

    private final List<Ticket> tickets = new ArrayList<>();
    private int naechsteId = 1;

    /** Legt ein neues Ticket an und gibt es zurück. */
    public Ticket anlegen(String titel, String beschreibung, Prioritaet prioritaet) {
        Ticket ticket = new Ticket(naechsteId, titel, beschreibung, prioritaet);
        tickets.add(ticket);
        naechsteId++;
        return ticket;
    }

    /** Sucht ein Ticket anhand seiner Nummer. */
    public Optional<Ticket> finden(int id) {
        return tickets.stream().filter(t -> t.getId() == id).findFirst();
    }

    /** Ändert den Status eines Tickets. Wirft eine Ausnahme, wenn es die Nummer nicht gibt. */
    public Ticket statusAendern(int id, Status neuerStatus) {
        Ticket ticket = finden(id)
                .orElseThrow(() -> new IllegalArgumentException("Kein Ticket mit der Nummer " + id));
        ticket.setStatus(neuerStatus);
        return ticket;
    }

    /** Ändert die Priorität eines Tickets. */
    public Ticket prioritaetAendern(int id, Prioritaet neuePrioritaet) {
        Ticket ticket = finden(id)
                .orElseThrow(() -> new IllegalArgumentException("Kein Ticket mit der Nummer " + id));
        ticket.setPrioritaet(neuePrioritaet);
        return ticket;
    }

    /** Alle Tickets in der Reihenfolge, in der sie angelegt wurden. */
    public List<Ticket> alle() {
        return List.copyOf(tickets);
    }

    /** Alle Tickets mit einem bestimmten Status. */
    public List<Ticket> nachStatus(Status status) {
        return tickets.stream().filter(t -> t.getStatus() == status).toList();
    }

    /** Alle Tickets, deren Titel oder Beschreibung den Suchbegriff enthält (ohne Groß-/Kleinschreibung). */
    public List<Ticket> suchen(String begriff) {
        if (begriff == null || begriff.isBlank()) {
            return List.of();
        }
        String klein = begriff.trim().toLowerCase();
        return tickets.stream()
                .filter(t -> t.getTitel().toLowerCase().contains(klein)
                        || t.getBeschreibung().toLowerCase().contains(klein))
                .toList();
    }

    /** Alle nicht erledigten Tickets, die wichtigsten zuerst. */
    public List<Ticket> offeneNachPrioritaet() {
        return tickets.stream()
                .filter(t -> t.getStatus() != Status.ERLEDIGT)
                .sorted(Comparator.comparing(Ticket::getPrioritaet).reversed()
                        .thenComparing(Ticket::getId))
                .toList();
    }
}
