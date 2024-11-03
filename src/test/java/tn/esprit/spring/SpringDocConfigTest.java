package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;
import tn.esprit.spring.Config.SpringDocConfig;

import static org.junit.jupiter.api.Assertions.*;

class SpringDocConfigTest {
    private SpringDocConfig springDocConfig;

    @BeforeEach
    void setUp() {
        springDocConfig = new SpringDocConfig();
    }

    @Test
    void testSpringShopOpenAPI() {
        assertNotNull(springDocConfig.springShopOpenAPI());
        assertEquals("Gestion d'un foyer", springDocConfig.springShopOpenAPI().getInfo().getTitle());
    }

    @Test
    void testInfoAPI() {
        assertNotNull(springDocConfig.infoAPI());
        assertEquals("TP étude de cas 2023-2024", springDocConfig.infoAPI().getDescription());
        assertEquals("Sirine NAIFAR", springDocConfig.infoAPI().getContact().getName());
    }

    @Test
    void testContactAPI() {
        assertNotNull(springDocConfig.contactAPI());
        assertEquals("sirine.naifer@esprit.tn", springDocConfig.contactAPI().getEmail());
        assertEquals("https://www.linkedin.com/in/sirinenaifar/", springDocConfig.contactAPI().getUrl());
    }

    @Test
    void testAllPublicApi() {
        GroupedOpenApi allPublicApi = springDocConfig.allPublicApi();
        assertNotNull(allPublicApi);
        assertEquals("All Management API", allPublicApi.getGroup());
        assertTrue(allPublicApi.getPathsToMatch().contains("/**"));
    }

    @Test
    void testFoyerPublicApi() {
        GroupedOpenApi foyerPublicApi = springDocConfig.foyerPublicApi();
        assertNotNull(foyerPublicApi);
        assertEquals("Only Foyer Management API", foyerPublicApi.getGroup());
        assertTrue(foyerPublicApi.getPathsToMatch().contains("/foyer/**"));
    }
}
