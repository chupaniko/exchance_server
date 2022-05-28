package com.example.exchance_server.organization;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.registration.OrgRegRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public String registerOrganization(AppUser user, OrgRegRequest request) {
        Organization organization = new Organization(
                request.getNumTaxpayer(),
                user
        );
        organizationRepository.save(organization);
        return "Organization entity for AppUser with email " + user.getEmail() + " saved successfully";
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization findByNumTaxpayer(String numTaxpayer){
        return organizationRepository.findByNumTaxpayer(numTaxpayer).orElseThrow(() ->
                new IllegalStateException("no organization found"));
    }
}
