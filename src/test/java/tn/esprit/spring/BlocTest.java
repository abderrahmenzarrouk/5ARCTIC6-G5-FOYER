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
    void testSetNomBloc() {
        bloc.setNomBloc("Bloc B");
        assertEquals("Bloc B", bloc.getNomBloc());
    }

    @Test
    void testSetCapaciteBloc() {
        bloc.setCapaciteBloc(150);
        assertEquals(150, bloc.getCapaciteBloc());
    }

    @Test
    void testChambresListInitialization() {
        Bloc newBloc = new Bloc();
        assertNotNull(newBloc.getChambres());
        assertTrue(newBloc.getChambres().isEmpty());
    }

    @Test
    void testClearChambresList() {
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        bloc.getChambres().add(chambre1);
        bloc.getChambres().add(chambre2);
        assertEquals(2, bloc.getChambres().size());

        bloc.getChambres().clear();
        assertEquals(0, bloc.getChambres().size());
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
