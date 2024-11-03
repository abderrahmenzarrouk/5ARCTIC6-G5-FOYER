package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantRepositoryTest {

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
                .build();
    }

    @Test
    void testFindByCin() {
        when(etudiantRepository.findByCin(12345678L)).thenReturn(etudiant);

        Etudiant result = etudiantRepository.findByCin(12345678L);

        assertNotNull(result);
        assertEquals("Ali", result.getNomEt());
        verify(etudiantRepository, times(1)).findByCin(12345678L);
    }

    @Test
    void testFindByNomEtLike() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantRepository.findByNomEtLike("Ali")).thenReturn(etudiants);

        List<Etudiant> result = etudiantRepository.findByNomEtLike("Ali");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Ali", result.get(0).getNomEt());
    }

    @Test
    void testFindByNomEtContains() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantRepository.findByNomEtContains("Ali")).thenReturn(etudiants);

        List<Etudiant> result = etudiantRepository.findByNomEtContains("Ali");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Ali", result.get(0).getNomEt());
    }

    @Test
    void testFindByNomEtContaining() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantRepository.findByNomEtContaining("Ali")).thenReturn(etudiants);

        List<Etudiant> result = etudiantRepository.findByNomEtContaining("Ali");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Ali", result.get(0).getNomEt());
    }

    @Test
    void testE5erMethodeSQL() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantRepository.e5erMethodeSQL(true)).thenReturn(etudiants);

        List<Etudiant> result = etudiantRepository.e5erMethodeSQL(true);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Ali", result.get(0).getNomEt());
    }
}

