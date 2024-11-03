package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.Services.Chambre.ChambreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChambreServiceTest {
    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;
    private Bloc bloc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101)
                .typeC(TypeChambre.SIMPLE)
                .bloc(bloc)
                .build();
    }

    @Test
    void testAddOrUpdate() {
        when(chambreRepository.save(chambre)).thenReturn(chambre);
        Chambre result = chambreService.addOrUpdate(chambre);
        assertEquals(chambre, result);
    }

    @Test
    void testFindAll() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        when(chambreRepository.findAll()).thenReturn(chambres);
        List<Chambre> result = chambreService.findAll();
        assertEquals(1, result.size());
        assertEquals(chambre, result.get(0));
    }

    @Test
    void testFindById() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));
        Chambre result = chambreService.findById(1L);
        assertEquals(chambre, result);
    }

    @Test
    void testDeleteById() {
        chambreService.deleteById(1L);
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        chambreService.delete(chambre);
        verify(chambreRepository, times(1)).delete(chambre);
    }

    @Test
    void testGetChambresParNomBloc() {
        when(chambreRepository.findByBlocNomBloc("Bloc A")).thenReturn(List.of(chambre));
        List<Chambre> result = chambreService.getChambresParNomBloc("Bloc A");
        assertEquals(1, result.size());
    }


}
