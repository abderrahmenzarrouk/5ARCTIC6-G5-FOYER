package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {

    @Mock
    EtudiantRepository etudiantRepository;

    @InjectMocks
    EtudiantService etudiantService;

    Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiant = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("Ali")
                .prenomEt("Ben")
                .cin(12345678L)
                .ecole("Esprit")
                .dateNaissance(LocalDate.of(2000, 5, 20))
                .build();
    }

    @Test
    void testAddOrUpdate() {
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addOrUpdate(etudiant);

        assertNotNull(result);
        assertEquals(1L, result.getIdEtudiant());
        assertEquals("Ali", result.getNomEt());
    }

    @Test
    void testFindAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.findAll();


        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdEtudiant());
        assertEquals("Ali", result.getNomEt());
    }

    @Test
    void testDeleteById() {

        etudiantService.deleteById(1L);

        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {

        etudiantService.delete(etudiant);

        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testFindById_NotFound() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            etudiantService.findById(1L);
        });

        assertEquals("etudiant not found with id: 1", thrown.getMessage());
    }

    @Test
    void testDeleteById_NotFound() {
        doThrow(new EmptyResultDataAccessException(1)).when(etudiantRepository).deleteById(1L);

        assertThrows(EmptyResultDataAccessException.class, () -> {
            etudiantService.deleteById(1L);
        });
    }

    @Test
    void testAddOrUpdate_WithNullFields() {
        Etudiant nullEtudiant = new Etudiant(); // Assuming other fields are null

        assertThrows(NullPointerException.class, () -> {
            etudiantService.addOrUpdate(nullEtudiant);
        });
    }

    @Test
    void testAddOrUpdate_InvalidCin() {
        Etudiant invalidEtudiant = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("Ali")
                .prenomEt("Ben")
                .cin(-12345678L) // Invalid CIN
                .ecole("Esprit")
                .dateNaissance(LocalDate.of(2000, 5, 20))
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.addOrUpdate(invalidEtudiant);
        });
    }
    @Test
    void testAddOrUpdate_NullEtudiant() {
        assertThrows(NullPointerException.class, () -> {
            etudiantService.addOrUpdate(null);
        });
    }
    @Test
    void testAddOrUpdate_WithTransaction() {
        when(etudiantRepository.save(any(Etudiant.class))).thenThrow(new RuntimeException("Database Error"));

        assertThrows(RuntimeException.class, () -> {
            etudiantService.addOrUpdate(etudiant);
        });
    }

    @Test
    void testAddMultipleEtudiants() {
        Etudiant etudiant2 = Etudiant.builder()
                .idEtudiant(2L)
                .nomEt("Sam")
                .prenomEt("Smith")
                .cin(87654321L)
                .ecole("Esprit")
                .dateNaissance(LocalDate.of(2001, 6, 15))
                .build();

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(etudiantRepository.save(etudiant2)).thenReturn(etudiant2);

        Etudiant result1 = etudiantService.addOrUpdate(etudiant);
        Etudiant result2 = etudiantService.addOrUpdate(etudiant2);

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(1L, result1.getIdEtudiant());
        assertEquals(2L, result2.getIdEtudiant());
    }
    @Test
    void testFindAllEmptyList() {
        when(etudiantRepository.findAll()).thenReturn(new ArrayList<>());

        List<Etudiant> result = etudiantService.findAll();

        assertTrue(result.isEmpty());
    }
    @Test
    void testAddOrUpdate_RepositoryThrowsException() {
        when(etudiantRepository.save(any(Etudiant.class))).thenThrow(new RuntimeException("Error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            etudiantService.addOrUpdate(etudiant);
        });

        assertEquals("Error", thrown.getMessage());
    }





}
