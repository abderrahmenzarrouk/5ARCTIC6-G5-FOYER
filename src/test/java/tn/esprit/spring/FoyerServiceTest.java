package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
import tn.esprit.spring.Services.Foyer.FoyerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerServiceTest {
    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private FoyerService foyerService;

    private Foyer foyer;
    private Universite universite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foyer = Foyer.builder().idFoyer(1L).nomFoyer("Foyer A").capaciteFoyer(100).build();
        universite = Universite.builder().idUniversite(1L).nomUniversite("University A").build();
    }

    @Test
    void testAddOrUpdate() {
        when(foyerRepository.save(foyer)).thenReturn(foyer);
        Foyer result = foyerService.addOrUpdate(foyer);
        assertEquals(foyer, result);
    }

    @Test
    void testFindAll() {
        when(foyerRepository.findAll()).thenReturn(Collections.singletonList(foyer));
        assertEquals(1, foyerService.findAll().size());
    }

    @Test
    void testFindById() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        Foyer result = foyerService.findById(1L);
        assertEquals(foyer, result);
    }

    @Test
    void testFindById_NotFound() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> foyerService.findById(1L));
    }

    @Test
    void testDeleteById() {
        doNothing().when(foyerRepository).deleteById(1L);
        foyerService.deleteById(1L);
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAffecterFoyerAUniversite() {
        // Prepare the blocs list before the test
        foyer.setBlocs(new ArrayList<>()); // Initialize blocs in the test

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite("University A")).thenReturn(universite);

        // Ensure the universite is saved correctly
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = foyerService.affecterFoyerAUniversite(1L, "University A");

        // Check that the expected values are set correctly
        assertEquals(universite, result);
        assertEquals(foyer, result.getFoyer()); // This should not be null if the association was set
    }

    @Test
    void testDesaffecterFoyerAUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(universite)).thenReturn(universite);
        Universite result = foyerService.desaffecterFoyerAUniversite(1L);
        assertNull(result.getFoyer());
    }



    @Test
    void testAjoutFoyerEtBlocs() {
        foyer.setBlocs(new ArrayList<>());
        when(foyerRepository.save(foyer)).thenReturn(foyer);
        Foyer result = foyerService.ajoutFoyerEtBlocs(foyer);
        assertEquals(foyer, result);
    }
}
