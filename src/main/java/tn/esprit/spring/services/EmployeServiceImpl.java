package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private static final Logger logger = Logger. getLogger(EmployeServiceImpl.class);
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public Employe authenticate(String login, String password) {
		logger.info("authenctification...");
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	@Override
	public int addOrUpdateEmploye(Employe employe) {
		logger.info("sauvegarder les données de l employee en cours...");
		employeRepository.save(employe);
		return employe.getId();
	}


	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		logger.info("mettre à jour l email d l employee en cours...");
		employe.setEmail(email);
		employeRepository.save(employe);

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);
		}
		logger.info("affecter l employee au departement...");
		// à ajouter? 
		deptRepoistory.save(depManagedEntity); 

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				logger.info("desaffecter l employee au departement...");
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}
	} 
	
	// Tablesapce (espace disque) 

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		logger.info("ajouter nouveau contract...");
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
        logger.info("affecter le contrat à l employee...");
		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);

	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		logger.info("recuperer l employee avec cette id...");
		return employeManagedEntity.getPrenom();
	}
	 
	public void deleteEmployeById(int employeId)
	{
		Employe employe = employeRepository.findById(employeId).get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}
        logger.info("supprimer l employee en cours...");
		employeRepository.delete(employe);
	}

	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		logger.info("supprimer le contrat en cours...");
		contratRepoistory.delete(contratManagedEntity);

	}

	public int getNombreEmployeJPQL() {
		logger.info("recuperer le nombre de tous les employes...");
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		logger.info("recuperer tous les employes...");
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		logger.info("recuperer tous les employes de l entreprise en cours...");
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		logger.info("mettre a jour l email de l employe...");
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
		logger.info("supprimer tous les contrats...");
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		logger.info("recuperer le salaire de l employe en cours...");
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		logger.info("recuperer le salaire moyen de departement en cours...");
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		logger.info("recuperer timesheets...");
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		logger.info("recuperer tous les employees...");
		return (List<Employe>) employeRepository.findAll();
	}

}
