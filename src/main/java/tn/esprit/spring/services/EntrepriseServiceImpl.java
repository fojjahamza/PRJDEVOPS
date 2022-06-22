package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	private static final Logger l = Logger. getLogger(EntrepriseServiceImpl.class);

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		l.info("In ajouterEntreprise() : ");
		l.debug("Je vais ajouter une entreprise .");
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		l.info("In ajouterDepartement() : ");
		l.debug("Je vais ajouter une Departement .");
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		l.info("In affecterDepartementAEntreprise() : ");
		l.debug("Je vais affecter departement a Entreprise .");
				Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
				Departement depManagedEntity = deptRepoistory.findById(depId).get();
				
				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity);
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		l.info("In getAllDepartementsNamesByEntreprise() : ");
		l.debug("Je vais retourner tous les departements .");
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {

		l.info("In deleteEntrepriseById() : ");
		l.debug("Je vais lancer la suppression.");
		entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());	
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		try {

			l.info("In deleteDepartementById() : ");
			l.debug("Je vais lancer la suppression.");
			deptRepoistory.delete(deptRepoistory.findById(depId).get());	
			l.debug("Je viens de lancer la suppression. ");
			l.debug("Je viens de finir suppression");
			l.info("Out deleteDepartementById() without errors.");
			}
			catch (Exception e) { l.error("Delete dans deleteDepartementById() : " + e); }
			}


	public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("In getEntrepriseById() : ");
		l.debug("Je vais retourner l'entreprise par id.");
		return entrepriseRepoistory.findById(entrepriseId).get();	
	}

}
