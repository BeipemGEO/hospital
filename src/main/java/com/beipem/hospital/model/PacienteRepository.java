package com.beipem.hospital.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.beipem.hospital.entities.Paciente;

import jakarta.transaction.Transactional;

public interface PacienteRepository extends CrudRepository<Paciente, Integer>{
	List<Paciente> findAll();
	
	Paciente findById(int id);
	
    @Transactional
    void deleteByNombre(String nombre);	
}
