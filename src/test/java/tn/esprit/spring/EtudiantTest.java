package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Etudiant;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    @Test
    void testEtudiantCreationUsingBuilder() {
        Etudiant etudiant = Etudiant.builder()
                .nomEt("Ali")
                .prenomEt("Ben")
                .cin(12345678L)
                .ecole("Esprit")
                .dateNaissance(LocalDate.of(2000, 5, 20))
                .build();

        assertNotNull(etudiant);
        assertEquals("Ali", etudiant.getNomEt());
        assertEquals("Ben", etudiant.getPrenomEt());
        assertEquals(12345678L, etudiant.getCin());
        assertEquals("Esprit", etudiant.getEcole());
        assertEquals(LocalDate.of(2000, 5, 20), etudiant.getDateNaissance());
    }

    @Test
    void testEtudiantDefaultConstructor() {
        Etudiant etudiant = new Etudiant();
        assertNotNull(etudiant);
        assertTrue(etudiant.getReservations().isEmpty()); // Should be empty by default
    }

    @Test
    void testEtudiantSettersAndGetters() {
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEt("Ali");
        etudiant.setPrenomEt("Ben");
        etudiant.setCin(12345678L);
        etudiant.setEcole("Esprit");
        etudiant.setDateNaissance(LocalDate.of(2000, 5, 20));

        assertEquals("Ali", etudiant.getNomEt());
        assertEquals("Ben", etudiant.getPrenomEt());
        assertEquals(12345678L, etudiant.getCin());
        assertEquals("Esprit", etudiant.getEcole());
        assertEquals(LocalDate.of(2000, 5, 20), etudiant.getDateNaissance());
    }





}
