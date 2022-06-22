package tn.esprit.spring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Date;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TimesheetServiceTest {
    private static final Logger logger = LogManager.getLogger(EntrepriseTest.class);
    @Autowired
    ITimesheetService timeshetService;

    @Autowired
    IEmployeService employeService;

    @Autowired
    IEntrepriseService entrepriseService;

    @MockBean
    EmployeRepository employeRepository;

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    TimesheetRepository timesheetRepository;

    @Test
    public void ajouterTimesheet() {

		logger.info("Start Method ajouterTimesheet");

        timeshetService.ajouterTimesheet(1, 1, new Date(22, 01, 1), new Date(22, 01, 10));
        TimesheetPK timesheetPK = new TimesheetPK(1, 1, new Date(22, 01, 1), new Date(22, 01, 10));
        Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);

		Assertions.assertNotNull(timesheet);
		logger.info("Test passed ajouterTimesheet");

	}

    @Test
    public void affecterMissionADepartement() {
		logger.info("Start Test affecterMissionADepartement");

        Mission mission = new Mission("mission1", "mission numero 1");
        Departement dep = new Departement("dep1");

        int missionId = timeshetService.ajouterMission(mission);
        int depId = entrepriseService.ajouterDepartement(dep);

        timeshetService.affecterMissionADepartement(missionId, depId);

        Mission newMission = missionRepository.findById(missionId).get();
        Departement newDep = newMission.getDepartement();

        Assertions.assertEquals(newDep.getId(), depId);
		logger.info("Test passed ajouterContrat");

	}

    @Test
    public void ajouterMission() {

		logger.info("Start Method ajouterMission");

        Mission mission = new Mission("mission3", "mission numero 3");

        int missionId = timeshetService.ajouterMission(mission);

        Mission newMission = missionRepository.findById(missionId).get();

		Assertions.assertNotNull(newMission);
		logger.info("Test passed ajouterMission");

	}

}
