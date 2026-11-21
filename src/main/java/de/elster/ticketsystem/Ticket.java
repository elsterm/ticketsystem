package de.elster.ticketsystem;

import java.time.LocalDateTime;

/** Ein einzelnes Ticket mit Titel, Beschreibung, Status und Priorität. */
public class Ticket {

    private final int id;
    private final String titel;
    private final String beschreibung;
    private final LocalDateTime erstelltAm;
    private Prioritaet prioritaet;
    private Status status;

    public Ticket(int id, String titel, String beschreibung, Prioritaet prioritaet) {
        if (titel == null || titel.isBlank()) {
            throw new IllegalArgumentException("Der Titel darf nicht leer sein");
        }
        if (prioritaet == null) {
            throw new IllegalArgumentException("Die Priorität darf nicht fehlen");
        }
        this.id = id;
        this.titel = titel.trim();
        this.beschreibung = beschreibung == null ? "" : beschreibung.trim();
        this.prioritaet = prioritaet;
        this.status = Status.OFFEN;
        this.erstelltAm = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public LocalDateTime getErstelltAm() {
        return erstelltAm;
    }

    public Prioritaet getPrioritaet() {
        return prioritaet;
    }

    public void setPrioritaet(Prioritaet prioritaet) {
        if (prioritaet == null) {
            throw new IllegalArgumentException("Die Priorität darf nicht fehlen");
        }
        this.prioritaet = prioritaet;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Ändert den Status. Ein erledigtes Ticket kann nicht direkt wieder auf "in Bearbeitung"
     * gesetzt werden, es muss zuerst wieder geöffnet werden.
     */
    public void setStatus(Status neu) {
        if (neu == null) {
            throw new IllegalArgumentException("Der Status darf nicht fehlen");
        }
        if (status == Status.ERLEDIGT && neu == Status.IN_BEARBEITUNG) {
            throw new IllegalStateException(
                    "Ein erledigtes Ticket muss zuerst wieder geöffnet werden");
        }
        this.status = neu;
    }

    @Override
    public String toString() {
        return String.format("#%d [%s | %s] %s", id, status.getAnzeige(), prioritaet.getAnzeige(), titel);
    }
}
