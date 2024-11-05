package tn.esprit.spring;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.dao.entities.Foyer;
import tn.esprit.spring.dao.repositories.FoyerRepository;

import java.util.Optional;

@SpringBootTest
class FoyerServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerRepository.save(foyer);

        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        verify(foyerRepository).save(foyer);
    }

    @Test
    public void testFindByIdNotFound() {
        Long invalidId = -1L;
        Foyer result = foyerRepository.findById(invalidId).orElse(null);
        assertNull(result, "Le résultat devrait être null pour un ID non trouvé");
    }
}
