package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.dao.entities.Foyer;
import tn.esprit.spring.dao.repositories.FoyerRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FoyerServiceIntegrationTest {

    @Autowired
    private FoyerRepository foyerRepository;

    @BeforeEach
    void setUp() {
        foyerRepository.deleteAll();
    }

    @Test
    void testAddOrUpdateIntegration() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer Test");
        foyer.setCapaciteFoyer(100);

        Foyer savedFoyer = foyerRepository.save(foyer);

        assertNotNull(savedFoyer);
        assertNotNull(savedFoyer.getIdFoyer());
        assertEquals("Foyer Test", savedFoyer.getNomFoyer());
    }
}
