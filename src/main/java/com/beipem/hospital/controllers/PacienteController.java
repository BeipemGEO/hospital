package com.beipem.hospital.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.beipem.hospital.entities.Paciente;
import com.beipem.hospital.model.PacienteRepository;

@Controller
public class PacienteController {
	
	/* Spring MVC @Controller
	 * We can annotate classic controllers with the @Controller annotation. This is simply a specialization of the @Component class, 
	 * which allows us to auto-detect implementation classes through the classpath scanning.
	 * We typically use @Controller in combination with a @RequestMapping annotation for request handling methods.
	 * We annotated the request handling method with @ResponseBody. 
	 * This annotation enables automatic serialization of the return object into the HttpResponse.
	 * */	
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/*@Autowired evita definir: PacienteControllerRest(){pacienteRepository = new PacienteRepository();} */
	@Autowired
	private PacienteRepository pacienteRepository;		
	
	@GetMapping("/altaPaciente")
	public String createPaciente(@RequestParam("nombre") String nombre, @RequestParam("apellidos") String apellidos,
			@RequestParam("fecha_alta") String fechaAlta) {
		Paciente paciente = new Paciente();
		paciente.setNombre(nombre);
		paciente.setApellidos(apellidos);
		
		try {
			Date fchAltaDate = simpleDateFormat.parse(fechaAlta);
			paciente.setFecha_alta(fchAltaDate);
		}catch(ParseException e){
			e.printStackTrace();
			System.out.println("Error en fecha");
		}
		
		pacienteRepository.save(paciente);
		return "forward:index.html";
	}
	
	@GetMapping("/bajaPacientePorId")
	public String deletePacientePorId(@ModelAttribute("paciente") Paciente paciente){
		pacienteRepository.delete(paciente);
		return "forward:index.html";
	}
	
	@GetMapping("/bajaPacientePorNombre")
	public String deletePacientePorNombre(@ModelAttribute("paciente") Paciente paciente){
		pacienteRepository.deleteByNombre(paciente.getNombre());
		return "forward:index.html";
	}		

	@GetMapping("/listadoPacientes")
	public String consultaPacientes(Model model) {
		List<Paciente> listpacientes = pacienteRepository.findAll();
		model.addAttribute("pacientes", listpacientes);
		return "consulta";
	}	
	
	@GetMapping("/consultaPaciente/{paciId}")
	public String consultaPaciente(@PathVariable("paciId") int id, Model model) {
		Paciente paci = pacienteRepository.findById(id);
		model.addAttribute("paciente", paci);
		return "consulta-paciente";
	}	
	
	@GetMapping("/actualizaPaciente")
	public String modificaPaciente(@RequestParam("identificador") Integer id, @RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos, @RequestParam("fecha_alta") String fchAlta) {
		Paciente paci = new Paciente();
		paci.setId(id);
		paci.setNombre(nombre);
		paci.setApellidos(apellidos);
		try {
			Date fechaAlta = simpleDateFormat.parse(fchAlta);
			paci.setFecha_alta(fechaAlta);
		} catch (ParseException e){
			e.printStackTrace();
			System.out.println("Error en fecha");
		}
		pacienteRepository.save(paci);
		return "forward:index.html";
	}	
}
