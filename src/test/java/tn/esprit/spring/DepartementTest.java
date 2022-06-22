package tn.esprit.spring;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.IContratService;
import tn.esprit.spring.services.IDepartementService;

import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartementTest {

	private static final Logger logger = LogManager.getLogger(EntrepriseTest.class);
	private Departement dep1;
	private Departement dep2;
	private Departement dep3;
	
	@Autowired
	IDepartementService iDepartementService;
    
	@MockBean
	DepartementRepository departRepo ;
    @Before
    public void setUp()
    {
		 dep1 = new Departement("dep1");
		 dep2 = new Departement("dep2");
		 dep2 = new Departement("dep3");
		
    }
    @Test
    public void tests() throws ParseException {
    	getAllDepartements();
    }
	
	public void getAllDepartements() {

		logger.info("Début methode getAllDepartements");

        doReturn(Arrays.asList(dep1, dep2, dep3)).when(departRepo).findAll();

        List<Departement> deps = iDepartementService.getAllDepartements();
      
        Assertions.assertEquals(3, deps.size(), "il faut avoir 3 departements");
        logger.info("Terminer methode getAllDepartements");
	}	
	
}