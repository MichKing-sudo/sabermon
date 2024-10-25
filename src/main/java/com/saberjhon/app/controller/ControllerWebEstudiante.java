package com.saberjhon.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import com.saberjhon.app.entity.Estudiante;

import com.saberjhon.app.repository.EstudianteRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "estudiante")
	
public class ControllerWebEstudiante {
	
	@Autowired
    private EstudianteRepository estudianteRepository;
	
	@GetMapping("/resultadoTotal")
    public String estudianteResultadoTotalTemplate(Model model, HttpSession session) {
    	Estudiante estudiante = (Estudiante) session.getAttribute("usuarioLogeado");
        
        if (estudiante != null) {
            model.addAttribute("nombre", estudiante.getPrimerNombre());
            model.addAttribute("apellido", estudiante.getPrimerApellido());
            model.addAttribute("total", estudiante.getPuntaje());
            model.addAttribute("totalLevel", estudiante.getNivelSaberPro());
        }
        
        return "estudiante-resultado-total";
    }
	
	@GetMapping("/resultadoUnico")
    public String estudianteResultadoUnicoTemplate(Model model, HttpSession session) {
    	Estudiante estudiante = (Estudiante) session.getAttribute("usuarioLogeado");
        
        if (estudiante != null) {
            model.addAttribute("nombre", estudiante.getPrimerNombre());
            model.addAttribute("apellido", estudiante.getPrimerApellido());
            model.addAttribute("comunicacion", estudiante.getComunicacionEscrita());
            model.addAttribute("comunicacionLevel", estudiante.getNivelComunicacionEscrita());
            model.addAttribute("razonamiento", estudiante.getRazonamientoCuantitativo());
            model.addAttribute("razonamientoLevel", estudiante.getNivelRazonamientoCuantitativo());
            model.addAttribute("lectura", estudiante.getLecturaCritica());
            model.addAttribute("lecturaLevel", estudiante.getNivelLecturaCritica());
            model.addAttribute("competencias", estudiante.getCompetenciasCiudadanas());
            model.addAttribute("competenciasLevel", estudiante.getNivelCompetenciasCiudadanas());
            model.addAttribute("ingles", estudiante.getIngles());
            model.addAttribute("inglesLevel", estudiante.getNivelIngles());
            model.addAttribute("levelIngles", estudiante.getNivelDeIngles());
            model.addAttribute("formulacion", estudiante.getFormulacionProyectosIngenieria());
            model.addAttribute("formulacionLevel", estudiante.getNivelFormulacionProyectosIngenieria());
            model.addAttribute("pensamiento", estudiante.getPensamientoCientifico());
            model.addAttribute("pensamientoLevel", estudiante.getNivelPensamientoCientifico());
            model.addAttribute("Diseno", estudiante.getDiseñoSoftware());
            model.addAttribute("DisenoLevel", estudiante.getNivelDiseñoSoftware());
        }
        
        return "estudiante-resultado";
    }
	
	@GetMapping("/index")
    public String estudianteIndexTemplate(Model model, HttpSession session) {
    	Estudiante estudiante = (Estudiante) session.getAttribute("usuarioLogeado");
        
        if (estudiante != null) {
            model.addAttribute("nombre", estudiante.getPrimerNombre());
            model.addAttribute("apellido", estudiante.getPrimerApellido());
        }
        
        return "index-estudiante";
    }
	
	@PostMapping("/logear")
    public String estudianteLogearTemplate(@RequestParam String usuario, @RequestParam String contrasena, Model model, HttpSession session) {
        Estudiante estudiante = null;
        for (Estudiante c : estudianteRepository.findAll()) {
            if (c.getUsuario().equals(usuario)) {
            	estudiante = c;
                break;
            }
        }
        
        if (estudiante != null && estudiante.getContrasena().equals(contrasena)) {
            session.setAttribute("usuarioLogeado", estudiante);
            return "redirect:/estudiante/index";
        } else {
            model.addAttribute("error", true);
            return "login-coordinador";
        }
    }
	
	@GetMapping("/login")
	public String estudianteLoginTemplate(Model model) {
		return "login-estudiante";
	}
	
	
	@GetMapping("/informe/{id}")
	public String verInformeEstudiante(@PathVariable("id") String id, Model model) {
	   
	    Estudiante estudiante = estudianteRepository.findById(id).orElse(null);
	    
	    if (estudiante != null) {
	       
	    	model.addAttribute("nombre", estudiante.getPrimerNombre());
            model.addAttribute("apellido", estudiante.getPrimerApellido());
            model.addAttribute("puntaje", estudiante.getPuntaje());
            model.addAttribute("comunicacion", estudiante.getComunicacionEscrita());
            model.addAttribute("comunicacionLevel", estudiante.getNivelComunicacionEscrita());
            model.addAttribute("razonamiento", estudiante.getRazonamientoCuantitativo());
            model.addAttribute("razonamientoLevel", estudiante.getNivelRazonamientoCuantitativo());
            model.addAttribute("lectura", estudiante.getLecturaCritica());
            model.addAttribute("lecturaLevel", estudiante.getNivelLecturaCritica());
            model.addAttribute("competencias", estudiante.getCompetenciasCiudadanas());
            model.addAttribute("competenciasLevel", estudiante.getNivelCompetenciasCiudadanas());
            model.addAttribute("ingles", estudiante.getIngles());
            model.addAttribute("inglesLevel", estudiante.getNivelIngles());
            model.addAttribute("levelIngles", estudiante.getNivelDeIngles());
            model.addAttribute("formulacion", estudiante.getFormulacionProyectosIngenieria());
            model.addAttribute("formulacionLevel", estudiante.getNivelFormulacionProyectosIngenieria());
            model.addAttribute("pensamiento", estudiante.getPensamientoCientifico());
            model.addAttribute("pensamientoLevel", estudiante.getNivelPensamientoCientifico());
            model.addAttribute("Diseno", estudiante.getDiseñoSoftware());
            model.addAttribute("DisenoLevel", estudiante.getNivelDiseñoSoftware());
	        
	    }
	    
	    return "informe-estudiante"; 
	}

	
	@GetMapping("/ranking")
	public String mostrarRanking(Model model) {
	    List<Estudiante> ranking = estudianteRepository.findTop3ByOrderByPuntajeDesc(); 
	    model.addAttribute("ranking", ranking);
	    return "ranking";
	}

	
	
}
