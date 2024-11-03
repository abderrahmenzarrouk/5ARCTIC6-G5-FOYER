package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceTest {
    @Mock
    BlocRepository blocRepository;

    @Mock
    ChambreRepository chambreRepository;

    @Mock
    FoyerRepository foyerRepository;

    @InjectMocks
    BlocService blocService;

    List<Chambre> chambres;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chambres = new ArrayList<>();
        chambres.add(new Chambre());
        chambres.add(new Chambre());
    }

    @Test
    void testAddOrUpdate() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc A").chambres(chambres).build();
        when(blocRepository.save(bloc)).thenReturn(bloc);
        when(chambreRepository.save(any())).thenReturn(new Chambre());

        Bloc result = blocService.addOrUpdate(bloc);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        verify(blocRepository).save(bloc);
        for (Chambre chambre : chambres) {
            verify(chambreRepository).save(chambre);
        }
    }

    @Test
    void testFindAll() {
        when(blocRepository.findAll()).thenReturn(Arrays.asList(new Bloc(), new Bloc()));

        List<Bloc> result = blocService.findAll();

        assertEquals(2, result.size());
        verify(blocRepository).findAll();
    }

    @Test
    void testFindById() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc A").build();
        when(blocRepository.findById(1L)).thenReturn(java.util.Optional.of(bloc));

        Bloc result = blocService.findById(1L);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        verify(blocRepository).findById(1L);
    }

    @Test
    void testDeleteById() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.deleteById(1L);

        verify(blocRepository).deleteById(1L);
    }

    @Test
    void testDelete() {
        Bloc bloc = Bloc.builder().chambres(chambres).build();
        doNothing().when(blocRepository).delete(bloc);
        doNothing().when(chambreRepository).delete(any());

        blocService.delete(bloc);

        for (Chambre chambre : chambres) {
            verify(chambreRepository).delete(chambre);
        }
        verify(blocRepository).delete(bloc);
    }

    @Test
    void testAffecterChambresABloc() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc A").build();
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(chambreRepository.findByNumeroChambre(1L)).thenReturn(new Chambre());

        Bloc result = blocService.affecterChambresABloc(Arrays.asList(1L, 2L), "Bloc A");

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        verify(chambreRepository, times(2)).findByNumeroChambre(any());
    }

    @Test
    void testAffecterBlocAFoyer() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc A").build();
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);

        blocService.affecterBlocAFoyer("Bloc A", "Foyer 1");

        verify(blocRepository).save(bloc);
    }
}
