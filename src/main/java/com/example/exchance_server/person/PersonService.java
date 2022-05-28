package com.example.exchance_server.person;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserService;
import com.example.exchance_server.organization.Organization;
import com.example.exchance_server.organization.OrganizationService;
import com.example.exchance_server.person.info.EmploymentPeriod;
import com.example.exchance_server.person.info.EmploymentPeriodRepository;
import com.example.exchance_server.person.info.education.PersonEduRequest;
import com.example.exchance_server.person.info.education.PersonEducation;
import com.example.exchance_server.person.info.education.PersonEducationRepository;
import com.example.exchance_server.person.info.workexperience.WorkExpRequest;
import com.example.exchance_server.person.info.workexperience.WorkExperience;
import com.example.exchance_server.person.info.workexperience.WorkExperienceRepository;
import com.example.exchance_server.registration.PersonRegRequest;
import com.example.exchance_server.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonEducationRepository educationRepository;
    private final AppUserService userService;
    private final OrganizationService orgService;
    private final EmploymentPeriodRepository periodRepository;
    private final WorkExperienceRepository experienceRepository;

    public String registerPerson(AppUser user, PersonRegRequest request) {
        Person person = new Person(
                request.getSurname(),
                request.getPatronymic(),
                user,
                request.isGender(),
                request.getCitizenship(),
                request.getBirthday());
        personRepository.save(person);
        return "Person entity for AppUser with email " + user.getEmail() + " saved successfully";
        //return tokenService.findUnconfirmedTokenByUser(user);
    }

    private Person getPersonByToken(String token) {
        return personRepository.findByAppUser(userService.getUserByToken(token)).orElseThrow(
                () -> new IllegalStateException("Person not found")
        );
    }

    public String addPersonEducation(PersonEduRequest request) {
        PersonEducation education = new PersonEducation(
                request.getFacultyName(),
                request.getSpecialization(),
                request.getDegree(),
                getPersonByToken(request.getToken()));
        educationRepository.save(education);
        Organization university = orgService.findByNumTaxpayer(request.getEmplPeriodBody().getNumTaxpayer());
        EmploymentPeriod period = new EmploymentPeriod(
                university != null ? university.getAppUser().getName() : request.getEmplPeriodBody().getOrgName(),
                request.getEmplPeriodBody().getStartYear(),
                request.getEmplPeriodBody().getEndYear(),
                education,
                null,
                university
        );
        periodRepository.save(period);
        return "Person education saved successfully";
    }

    public String addWorkExperience(WorkExpRequest request) {
        WorkExperience experience = new WorkExperience(
                request.getInstitute(),
                request.getPost(),
                getPersonByToken(request.getToken())
        );
        experienceRepository.save(experience);
        Organization organization = orgService.findByNumTaxpayer(request.getEmplPeriodBody().getNumTaxpayer());
        EmploymentPeriod period = new EmploymentPeriod(
                organization != null ? organization.getAppUser().getName() : request.getEmplPeriodBody().getOrgName(),
                request.getEmplPeriodBody().getStartYear(),
                request.getEmplPeriodBody().getEndYear(),
                null,
                experience,
                organization
        );
        periodRepository.save(period);
        return "Person work experience saved successfully";
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }
}
