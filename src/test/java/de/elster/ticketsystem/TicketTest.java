package de.elster.ticketsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TicketTest {

    @Test
    void neuesTicketIstOffen() {
        Ticket t = new Ticket(1, "Drucker kaputt", "Papierstau", Prioritaet.MITTEL);
        assertEquals(Status.OFFEN, t.getStatus());
    }

    @Test
    void leererTitelWirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket(1, "   ", "egal", Prioritaet.NIEDRIG));
    }

    @Test
    void titelWirdGetrimmt() {
        Ticket t = new Ticket(1, "  Login geht nicht  ", null, Prioritaet.HOCH);
        assertEquals("Login geht nicht", t.getTitel());
        assertEquals("", t.getBeschreibung());
    }

    @Test
    void erledigtesTicketKannNichtDirektInBearbeitungGehen() {
        Ticket t = new Ticket(1, "Test", "", Prioritaet.NIEDRIG);
        t.setStatus(Status.ERLEDIGT);
        assertThrows(IllegalStateException.class, () -> t.setStatus(Status.IN_BEARBEITUNG));
    }

    @Test
    void erledigtesTicketKannWiederGeoeffnetWerden() {
        Ticket t = new Ticket(1, "Test", "", Prioritaet.NIEDRIG);
        t.setStatus(Status.ERLEDIGT);
        t.setStatus(Status.OFFEN);
        assertEquals(Status.OFFEN, t.getStatus());
    }

    @Test
    void statusUndPrioritaetKoennenNichtNullSein() {
        Ticket t = new Ticket(1, "Test", "", Prioritaet.NIEDRIG);
        assertThrows(IllegalArgumentException.class, () -> t.setStatus(null));
        assertThrows(IllegalArgumentException.class, () -> t.setPrioritaet(null));
    }

    @Test
    void statusWirdAusTextGelesen() {
        assertEquals(Status.IN_BEARBEITUNG, Status.ausText(" In Bearbeitung "));
        assertThrows(IllegalArgumentException.class, () -> Status.ausText("unbekannt"));
    }

    @Test
    void prioritaetWirdAusTextGelesen() {
        assertEquals(Prioritaet.HOCH, Prioritaet.ausText("HOCH"));
        assertThrows(IllegalArgumentException.class, () -> Prioritaet.ausText("extrem"));
    }
}
