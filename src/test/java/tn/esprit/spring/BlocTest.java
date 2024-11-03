package tn.esprit.spring;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;

import static org.junit.jupiter.api.Assertions.*;


class BlocTest {

    private Bloc bloc;

    @BeforeEach
    void setUp() {
        bloc = Bloc.builder()
                .nomBloc("Bloc A")
                .capaciteBloc(100)
                .build();
    }

    @Test
    void testBlocCreation() {
        assertNotNull(bloc);
        assertEquals("Bloc A", bloc.getNomBloc());
        assertEquals(100, bloc.getCapaciteBloc());
        assertEquals(0, bloc.getChambres().size());
    }

    @Test
    void testAddChambre() {
        Chambre chambre = new Chambre();
        chambre.setBloc(bloc);
        bloc.getChambres().add(chambre);

        assertEquals(1, bloc.getChambres().size());
        assertTrue(bloc.getChambres().contains(chambre));
    }

    @Test
    void testRemoveChambre() {
        Chambre chambre = new Chambre();
        chambre.setBloc(bloc);
        bloc.getChambres().add(chambre);
        assertEquals(1, bloc.getChambres().size());

        bloc.getChambres().remove(chambre);
        assertEquals(0, bloc.getChambres().size());
        assertFalse(bloc.getChambres().contains(chambre));
    }

    @Test
    void testFoyerRelationship() {
        Foyer foyer = new Foyer();
        bloc.setFoyer(foyer);

        assertEquals(foyer, bloc.getFoyer());
    }

    @Test
    void testBlocIdGeneration() {

        assertEquals(0, bloc.getIdBloc());
    }
}

