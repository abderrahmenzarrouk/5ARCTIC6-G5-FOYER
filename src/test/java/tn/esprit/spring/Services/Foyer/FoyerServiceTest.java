package tn.esprit.spring.Services.Foyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.Services.Foyer.FoyerService;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerServiceTest {

    @InjectMocks
    private FoyerService foyerService;

    @Mock
    private FoyerRepository foyerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addOrUpdate_ShouldSaveFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100);

        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer savedFoyer = foyerService.addOrUpdate(foyer);

        assertNotNull(savedFoyer);
        assertEquals("Foyer A", savedFoyer.getNomFoyer());
        assertEquals(100, savedFoyer.getCapaciteFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void findAll_ShouldReturnAllFoyers() {
        Foyer foyer1 = new Foyer();
        foyer1.setNomFoyer("Foyer A");
        Foyer foyer2 = new Foyer();
        foyer2.setNomFoyer("Foyer B");

        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer1, foyer2));

        List<Foyer> foyers = foyerService.findAll();

        assertEquals(2, foyers.size());
        assertEquals("Foyer A", foyers.get(0).getNomFoyer());
        assertEquals("Foyer B", foyers.get(1).getNomFoyer());
    }

    @Test
    void findById_ShouldReturnFoyer_WhenExists() {
        long id = 1;
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");

        when(foyerRepository.findById(id)).thenReturn(Optional.of(foyer));

        Foyer foundFoyer = foyerService.findById(id);

        assertNotNull(foundFoyer);
        assertEquals("Foyer A", foundFoyer.getNomFoyer());
    }

    @Test
    void deleteById_ShouldCallRepositoryDelete() {
        long id = 1;

        foyerService.deleteById(id);

        verify(foyerRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");

        foyerService.delete(foyer);

        verify(foyerRepository, times(1)).delete(foyer);
    }
}
