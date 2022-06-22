package tn.esprit.spring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.lang.*;
import java.util.Optional;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IDepartementService;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmployeServiceTest {
    private static final Logger logger = LogManager.getLogger(EntrepriseTest.class);
	@Autowired
	IEmployeService service;

    @MockBean
    EmployeRepository employeRepository;

    @Autowired
    ContratRepository contractRepository;

    @Test
    @DisplayName("Test getAllEmployes")
    public void testGetAllEmployes() {
        
        logger.info("Start Test getAllEmployes");
        logger.info("ajouter deux employees");
        Employe emp1 = new Employe("Ahmed", "Touati", "ahmed@ahmed.com", "ahmed123", true, Role.ADMINISTRATEUR);
        Employe emp2 = new Employe("Imed", "Felah", "imed@imed.com", "imed123", true, Role.CHEF_DEPARTEMENT);

        doReturn(Arrays.asList(emp1, emp2)).when(employeRepository).findAll();

        List<Employe> employes = service.getAllEmployes();
        logger.info("vérifier le nombre des employees");
      
        Assertions.assertEquals(2, employes.size(), "findAll should return 2 employes");
        logger.info("Test passed getAllEmployes");
    }
	
    @Test
    @DisplayName("Test getEmployePrenomById Success")
    public void testGetEmployePrenomById() {

        logger.info("Start Test getEmployePrenomById");
        logger.info("ajouter un employee");
        Employe emp = new Employe(5, "Ahmed", "Touati", "ahmed@ahmed.com", "ahmed123", true, Role.ADMINISTRATEUR);
        doReturn(Optional.of(emp)).when(employeRepository).findById(5);
 
        String empPrenom = service.getEmployePrenomById(5);
         
        logger.info("vérifier l existence de prenom");
        Assertions.assertFalse(empPrenom.isEmpty(), "prenom should not be empty");
        logger.info("vérifier que le prenom est le même");
        Assertions.assertSame(empPrenom, "Ahmed", "prenom is not the same");
        logger.info("Test passed getEmployePrenomById");
    }

    @Test
    @DisplayName("Test ajouterContrat")
    public void testAjouterContrat() {
		logger.info("Start Test ajouterContrat");
        Contrat contrat = new Contrat(new Date(2022, 01, 10), "CDI", 1000);
		logger.info(contrat);
		contrat.setReference(0001);
		Assertions.assertEquals(contrat.getReference(), 0001);
        Assertions.assertEquals(contrat.getSalaire(), 1000);
        Assertions.assertEquals(contrat.getDateDebut(), new Date(2022, 01, 10));
		logger.info("Test passed ajouterContrat");

	}

    @Test
    @DisplayName("Test deleteContrat")
    public void testDeleteContrat() {
		logger.info("Start Test deleteContrat");
        Contrat contract = new Contrat(new Date(22, 01, 10), "CDI", 2000);
		int reference = service.ajouterContrat(contract);
        service.deleteContratById(reference);
        Optional<Contrat> newContract = contractRepository.findById(reference);
		Assertions.assertEquals(newContract, Optional.empty());
		logger.info("Test passed ajouterContrat");

	}

}
