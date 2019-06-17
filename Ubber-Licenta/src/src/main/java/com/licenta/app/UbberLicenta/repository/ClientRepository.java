package com.licenta.app.UbberLicenta.repository;


import com.licenta.app.UbberLicenta.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {


}
