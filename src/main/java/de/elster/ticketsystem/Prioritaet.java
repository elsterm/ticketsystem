package de.elster.ticketsystem;

/** Dringlichkeit eines Tickets. */
public enum Prioritaet {
    NIEDRIG("niedrig"),
    MITTEL("mittel"),
    HOCH("hoch");

    private final String anzeige;

    Prioritaet(String anzeige) {
        this.anzeige = anzeige;
    }

    public String getAnzeige() {
        return anzeige;
    }

    /** Liest eine Priorität aus Nutzereingaben wie "niedrig", "mittel" oder "hoch". */
    public static Prioritaet ausText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Priorität darf nicht leer sein");
        }
        String normalisiert = text.trim().toUpperCase();
        for (Prioritaet p : values()) {
            if (p.name().equals(normalisiert)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unbekannte Priorität: " + text);
    }
}
