package tn.esprit.spring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class EntrepriseTest {
	private static final Logger LOG = LogManager.getLogger(EntrepriseTest.class);
	 @Autowired
	 EntrepriseServiceImpl entrepriseService;
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
    protected ApplicationContext applicationContext;

	private Entreprise entreprise;
	
@Before 
    public void setUp(){
	this.entreprise = new Entreprise();
	this.entreprise.setName("aymen");
	this.entreprise.setRaisonSocial("fjfjj");
}

	@Test
	public void tests() {
		ajouterEntrepriseTest();
		getEntrepriseByIdTest();
		//deleteEntrepriseByIdTest();
	}
	
	public void ajouterEntrepriseTest() {

		LOG.info("Debut ajouterEntrepriseTest Message methode test");
		LOG.info(this.entreprise);
        entrepriseService.ajouterEntreprise(this.entreprise);
        Entreprise entrepriseAjouté = entrepriseService.getEntrepriseById(this.entreprise.getId());
        assertNotNull(entrepriseAjouté);
		LOG.info(this.entreprise);
		LOG.info("Entreprise id" + this.entreprise.getId());
		LOG.info("fin ajouterEntreprise methode test");
	}

	public void getEntrepriseByIdTest() {
		LOG.info("Debut getEntrepriseById methode test");
		LOG.info("Entreprise id" + this.entreprise.getId());
		assertNotNull(entrepriseService.getEntrepriseById(this.entreprise.getId()));
		LOG.info("fin getEntrepriseById methode test");

	}

	public void deleteEntrepriseByIdTest() {
		LOG.info("Debut deleteEntrepriseById methode test");
		LOG.info("Entreprise id" + this.entreprise.getId());
		
		  try {
			  entrepriseService.deleteEntrepriseById(this.entreprise.getId());
			  } catch(Exception e) {
		      fail("N'aurait dû lancer aucune exception");
		   }
	}

}
