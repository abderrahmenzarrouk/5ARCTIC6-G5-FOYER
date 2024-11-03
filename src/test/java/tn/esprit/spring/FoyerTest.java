package tn.esprit.spring;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Foyer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FoyerTest {
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer A")
                .capaciteFoyer(100)
                .blocs(new ArrayList<>())
                .build();
    }

    @Test
    void testFoyerProperties() {
        assertEquals(1L, foyer.getIdFoyer());
        assertEquals("Foyer A", foyer.getNomFoyer());
        assertEquals(100, foyer.getCapaciteFoyer());
        assertTrue(foyer.getBlocs().isEmpty());
    }

    @Test
    void testSetters() {
        foyer.setNomFoyer("Foyer B");
        foyer.setCapaciteFoyer(150);
        assertEquals("Foyer B", foyer.getNomFoyer());
        assertEquals(150, foyer.getCapaciteFoyer());
    }
}

