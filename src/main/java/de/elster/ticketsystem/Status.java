package de.elster.ticketsystem;

/** Bearbeitungsstand eines Tickets. */
public enum Status {
    OFFEN("offen"),
    IN_BEARBEITUNG("in Bearbeitung"),
    ERLEDIGT("erledigt");

    private final String anzeige;

    Status(String anzeige) {
        this.anzeige = anzeige;
    }

    public String getAnzeige() {
        return anzeige;
    }

    /** Liest einen Status aus Nutzereingaben wie "offen", "in bearbeitung" oder "erledigt". */
    public static Status ausText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Status darf nicht leer sein");
        }
        String normalisiert = text.trim().toUpperCase().replace(' ', '_');
        for (Status s : values()) {
            if (s.name().equals(normalisiert)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unbekannter Status: " + text);
    }
}
