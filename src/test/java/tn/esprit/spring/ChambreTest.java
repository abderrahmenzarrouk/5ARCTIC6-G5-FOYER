package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChambreTest {
    Chambre chambre;

    @BeforeEach
    void setUp() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());
        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101)
                .typeC(TypeChambre.SIMPLE)
                .reservations(reservations)
                .build();
    }

    @Test
    void testGetIdChambre() {
        assertEquals(1L, chambre.getIdChambre());
    }

    @Test
    void testGetNumeroChambre() {
        assertEquals(101, chambre.getNumeroChambre());
    }

    @Test
    void testGetTypeC() {
        assertEquals(TypeChambre.SIMPLE, chambre.getTypeC());
    }

    @Test
    void testGetReservations() {
        assertEquals(2, chambre.getReservations().size());
    }

    @Test
    void testSetNumeroChambre() {
        chambre.setNumeroChambre(102);
        assertEquals(102, chambre.getNumeroChambre());
    }

    @Test
    void testSetTypeC() {
        chambre.setTypeC(TypeChambre.DOUBLE);
        assertEquals(TypeChambre.DOUBLE, chambre.getTypeC());
    }

    @Test
    void testAddReservation() {
        Reservation reservation = new Reservation();
        chambre.getReservations().add(reservation);
        assertEquals(3, chambre.getReservations().size());
    }
}
