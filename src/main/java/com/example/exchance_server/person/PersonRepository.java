package com.example.exchance_server.person;

import com.example.exchance_server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PersonRepository extends JpaRepository<Person, Long> {
    /*Optional<Person> findPersonByAppUser(AppUser appUser);
    //Optional<Person> findPersonByAppUser_AppUserRole_Student();
    //Optional<Person> findPersonByAppUser_AppUserRole_UniversityEmployee();
    Optional<Person>findAllByAppUserAppUserRole_Student();
    Optional<Person>findAllByAppUserAppUserRole_SimpleUser();
    Optional<Person>findAllByAppUserAppUserRole_UniversityEmployee();*/

    @Override
    List<Person> findAll();

    Optional<Person> findByAppUser(AppUser user);
}
