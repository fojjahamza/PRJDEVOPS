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
import tn.esprit.spring.services.IContratService;
import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratTest {

	private static final Logger logger = LogManager.getLogger(EntrepriseTest.class);
	private Contrat contrat1;
	private Contrat contrat2;
	
	@Autowired
	IContratService iContratService ;
    
	@MockBean
	ContratRepository contratRepo ;
    @Before
    public void setUp()
    {
    	Date dateDebut=new Date();

		 contrat1 = new Contrat(dateDebut,"CDI",(float) 2.7); 
		 contrat2 = new Contrat(dateDebut,"CDD",(float) 3); 
		
    }
    @Test
    public void tests() throws ParseException {
    	getAllContrats();
    }
	
	public void getAllContrats() {

		logger.info("Début methode getAllContrats");

        doReturn(Arrays.asList(contrat1, contrat2)).when(contratRepo).findAll();

        List<Contrat> contrats = iContratService.getAllContrats();
      
        Assertions.assertEquals(2, contrats.size(), "il faut avoir 2 contrats");
        logger.info("Terminer methode getAllContrats");
	}	
	
}