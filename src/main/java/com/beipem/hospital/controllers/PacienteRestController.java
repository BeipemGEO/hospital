package com.beipem.hospital.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beipem.hospital.entities.Paciente;
import com.beipem.hospital.model.PacienteRepository;

@CrossOrigin 		//Para peticiones desde otro servidor.
@RestController		//Para realizar peticiones REST
@RequestMapping("/hospital/")
public class PacienteRestController {
	
	/*
	 * Spring MVC @RestController
	 * @RestController is a specialized version of the controller. It includes the @Controller and @ResponseBody annotations, and as a result, simplifies the controller implementation:
	 * The controller is annotated with the @RestController annotation; therefore, the @ResponseBody isn't required.
	 * Every request handling method of the controller class automatically serializes return objects into HttpResponse.
	 *
	 * Conclusion
	 * In this article, we examined the classic and specialized REST controllers available in the Spring Framework.
	 * The complete source code for the examples is available in the GitHub project. 
	 * This is a Maven project, so it can be imported and used as is.
	 * https://www.baeldung.com/spring-controller-vs-restcontroller
	 */	
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/*@Autowired evita definir: PacienteControllerRest(){pacienteRepository = new PacienteRepository();} */
	@Autowired
	private PacienteRepository pacienteRepository;	
	
	@PostMapping("/{nombre}/{apellidos}/{fecha_alta}")
	public void altaPaciente(@PathVariable("nombre") String nombre, @PathVariable("apellidos") String apellidos,
			@PathVariable("fecha_alta") String fchAlta) {
		Paciente paci = new Paciente();
		paci.setNombre(nombre);
		paci.setApellidos(apellidos);
		
		try {
			Date fecha_Alta = simpleDateFormat.parse(fchAlta);
			paci.setFecha_alta(fecha_Alta);
		} catch(ParseException e){
			e.printStackTrace();
			System.out.println("Error en fecha");
		}
		pacienteRepository.save(paci);
	}	
	
	@DeleteMapping("{id}")
	public void bajaPacientePorId(@PathVariable("id") Integer id) {
		pacienteRepository.deleteById(id);
	}	
	
	@DeleteMapping("byName/{nombre}")
	public void bajaPacientePorNombre(@PathVariable("nombre") String nombre) {
		pacienteRepository.deleteByNombre(nombre);
	};
	
	@GetMapping("")
	public List<Paciente> getPacientes(){
		List<Paciente> paci = pacienteRepository.findAll();
		return paci;
	}
	
	@GetMapping("/{pacienteId}")
	public Paciente consultaPaciente(@PathVariable("pacienteId") int id) {
		Paciente paciente = pacienteRepository.findById(id);
		return paciente;
	}	
	
	@PutMapping("/{id}")
	public void modificaPaciente(@RequestBody Paciente paciente, @PathVariable("id") Integer id) {
		paciente.setId(id);
		pacienteRepository.save(paciente);
	}	
}
