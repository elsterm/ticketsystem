package de.elster.ticketsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketServiceTest {

    private TicketService service;

    @BeforeEach
    void vorbereiten() {
        service = new TicketService();
    }

    @Test
    void nummernWerdenFortlaufendVergeben() {
        Ticket a = service.anlegen("Erstes", "", Prioritaet.NIEDRIG);
        Ticket b = service.anlegen("Zweites", "", Prioritaet.NIEDRIG);
        assertEquals(1, a.getId());
        assertEquals(2, b.getId());
    }

    @Test
    void ticketWirdAnhandDerNummerGefunden() {
        Ticket t = service.anlegen("Netzwerk", "WLAN weg", Prioritaet.HOCH);
        assertEquals(t, service.finden(t.getId()).orElseThrow());
        assertTrue(service.finden(99).isEmpty());
    }

    @Test
    void statusAendernBeiUnbekannterNummerWirftFehler() {
        assertThrows(IllegalArgumentException.class, () -> service.statusAendern(42, Status.ERLEDIGT));
    }

    @Test
    void statusAendernFunktioniert() {
        Ticket t = service.anlegen("Mail", "", Prioritaet.MITTEL);
        service.statusAendern(t.getId(), Status.IN_BEARBEITUNG);
        assertEquals(Status.IN_BEARBEITUNG, t.getStatus());
    }

    @Test
    void prioritaetAendernFunktioniert() {
        Ticket t = service.anlegen("Mail", "", Prioritaet.NIEDRIG);
        service.prioritaetAendern(t.getId(), Prioritaet.HOCH);
        assertEquals(Prioritaet.HOCH, t.getPrioritaet());
    }

    @Test
    void nachStatusFiltertRichtig() {
        Ticket a = service.anlegen("A", "", Prioritaet.NIEDRIG);
        service.anlegen("B", "", Prioritaet.NIEDRIG);
        service.statusAendern(a.getId(), Status.ERLEDIGT);
        assertEquals(1, service.nachStatus(Status.ERLEDIGT).size());
        assertEquals(1, service.nachStatus(Status.OFFEN).size());
        assertTrue(service.nachStatus(Status.IN_BEARBEITUNG).isEmpty());
    }

    @Test
    void sucheFindetTitelUndBeschreibungOhneGrossKleinschreibung() {
        service.anlegen("Drucker defekt", "Toner leer", Prioritaet.MITTEL);
        service.anlegen("Passwort vergessen", "Kunde kommt nicht rein", Prioritaet.HOCH);
        assertEquals(1, service.suchen("DRUCKER").size());
        assertEquals(1, service.suchen("kunde").size());
        assertTrue(service.suchen("xyz").isEmpty());
    }

    @Test
    void leereSucheLiefertNichts() {
        service.anlegen("Irgendwas", "", Prioritaet.NIEDRIG);
        assertTrue(service.suchen("  ").isEmpty());
        assertTrue(service.suchen(null).isEmpty());
    }

    @Test
    void offeneTicketsSindNachPrioritaetSortiertUndOhneErledigte() {
        Ticket niedrig = service.anlegen("niedrig", "", Prioritaet.NIEDRIG);
        Ticket hoch = service.anlegen("hoch", "", Prioritaet.HOCH);
        Ticket mittel = service.anlegen("mittel", "", Prioritaet.MITTEL);
        Ticket fertig = service.anlegen("fertig", "", Prioritaet.HOCH);
        service.statusAendern(fertig.getId(), Status.ERLEDIGT);

        List<Ticket> ergebnis = service.offeneNachPrioritaet();
        assertEquals(List.of(hoch, mittel, niedrig), ergebnis);
    }

    @Test
    void alleLiefertUnveraenderlicheKopie() {
        service.anlegen("A", "", Prioritaet.NIEDRIG);
        List<Ticket> liste = service.alle();
        assertThrows(UnsupportedOperationException.class, () -> liste.add(null));
    }
}
