package tn.esprit.spring;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;


class ReservationTest {
    @InjectMocks
    private Reservation reservation;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = Reservation.builder()
                .idReservation("R001")
                .anneeUniversitaire(LocalDate.of(2024, 9, 1))
                .estValide(true)
                .etudiants(new ArrayList<>())
                .build();

        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("John Doe");
    }

    @Test
    void testAddEtudiant() {
        reservation.getEtudiants().add(etudiant);
        assertEquals(1, reservation.getEtudiants().size());
        assertTrue(reservation.getEtudiants().contains(etudiant));
    }

    @Test
    void testRemoveEtudiant() {
        reservation.getEtudiants().add(etudiant);
        reservation.getEtudiants().remove(etudiant);
        assertEquals(0, reservation.getEtudiants().size());
        assertFalse(reservation.getEtudiants().contains(etudiant));
    }

    @Test
    void testIsEstValide() {
        assertTrue(reservation.isEstValide());
        reservation.setEstValide(false);
        assertFalse(reservation.isEstValide());
    }

    @Test
    void testGetAnneeUniversitaire() {
        assertEquals(LocalDate.of(2024, 9, 1), reservation.getAnneeUniversitaire());
    }

    @Test
    void testIdReservation() {
        assertEquals("R001", reservation.getIdReservation());
    }
}

