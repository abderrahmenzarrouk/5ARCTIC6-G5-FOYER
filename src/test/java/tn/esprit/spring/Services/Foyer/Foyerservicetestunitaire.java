package tn.esprit.spring.Services.Foyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.dao.entities.Foyer;
import tn.esprit.spring.dao.repositories.FoyerRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Utiliser H2 en mémoire
@ActiveProfiles("test") // Assurez-vous que votre application.properties a une configuration 'test' si nécessaire
 class Foyerservicetestunitaire {

    @Autowired
    private FoyerService foyerService;

    @Autowired
    private FoyerRepository foyerRepository;

    @BeforeEach
    void setUp() {
        foyerRepository.deleteAll(); // S'assurer que la base est vide avant chaque test
    }

    @Test
    void test_addOrUpdate_ShouldSaveFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(150);

        Foyer savedFoyer = foyerService.addOrUpdate(foyer);

        assertNotNull(savedFoyer.getIdFoyer()); // Assurez-vous que l'ID est généré
        assertEquals("Foyer A", savedFoyer.getNomFoyer());
        assertEquals(150, savedFoyer.getCapaciteFoyer());
    }

    @Test
    void test_findAll_ShouldReturnAllFoyers() {
        Foyer foyer1 = new Foyer();
        foyer1.setNomFoyer("Foyer A");
        foyer1.setCapaciteFoyer(100);
        foyerRepository.save(foyer1);

        Foyer foyer2 = new Foyer();
        foyer2.setNomFoyer("Foyer B");
        foyer2.setCapaciteFoyer(200);
        foyerRepository.save(foyer2);

        List<Foyer> foyers = foyerService.findAll();

        assertEquals(2, foyers.size());
        assertEquals("Foyer A", foyers.get(0).getNomFoyer());
        assertEquals("Foyer B", foyers.get(1).getNomFoyer());
    }

    @Test
    void test_findById_ShouldReturnFoyer_WhenExists() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(150);
        foyerRepository.save(foyer);

        long id = foyer.getIdFoyer();
        Foyer foundFoyer = foyerService.findById(id);

        assertNotNull(foundFoyer);
        assertEquals("Foyer A", foundFoyer.getNomFoyer());
        assertEquals(150, foundFoyer.getCapaciteFoyer());
    }

    @Test
    void test_deleteById_ShouldDeleteFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer = foyerRepository.save(foyer);

        long id = foyer.getIdFoyer();
        foyerService.deleteById(id);

        assertFalse(foyerRepository.findById(id).isPresent()); // Vérifiez que le foyer n'est plus présent
    }

    @Test
    void test_delete_ShouldDeleteFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer = foyerRepository.save(foyer);

        foyerService.delete(foyer);

        assertFalse(foyerRepository.findById(foyer.getIdFoyer()).isPresent()); // Vérifiez que le foyer n'est plus présent
    }
}
