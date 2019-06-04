package com.licenta.app.UbberLicenta.repository;

import com.licenta.app.UbberLicenta.model.Fidelity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FidelityRepository extends JpaRepository<Fidelity, Integer> {

//   @Query(value = "SELECT f from Fidelity as f WHERE f.client_name = :name", nativeQuery = true)
//    Fidelity serchForFidelityClient(@Param("name")String name);

   @Query(value = "select * from fidelity as f", nativeQuery = true)
    public List<Fidelity> listOfAllFidelity();

   @Query(value = "select count (*) from Fidelity", nativeQuery = true)
    public int numberOfRecords();

}
