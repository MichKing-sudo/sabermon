package com.saberjhon.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.saberjhon.app.entity.Coordinador;
import com.saberjhon.app.entity.Estudiante;
import com.saberjhon.app.exception.NotFoundException;
import com.saberjhon.app.repository.CoordinadorRepository;
import com.saberjhon.app.repository.EstudianteRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "coordinador")
public class ControllerWebCoordinador {
    
    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @GetMapping("/index")
    public String coordinadorIndexTemplate(Model model, HttpSession session) {
    	Coordinador coordinador = (Coordinador) session.getAttribute("usuarioLogeado");
        
        if (coordinador != null) {
            model.addAttribute("usuario", coordinador.getUsuario());
            model.addAttribute("nombre", coordinador.getNombre());
        }
        
        return "index-coordinador";
    }
    
    @GetMapping("/login")
    public String coordinadorLoginTemplate(Model model) {
        return "login-coordinador";
    }
    
    @PostMapping("/logear")
    public String coordinadorLogearTemplate(@RequestParam String usuario, @RequestParam String contrasena, Model model, HttpSession session) {
        Coordinador coordinador = null;
        for (Coordinador c : coordinadorRepository.findAll()) {
            if (c.getUsuario().equals(usuario)) {
                coordinador = c;
                break;
            }
        }
        
        if (coordinador != null && coordinador.getContrasena().equals(contrasena)) {
            session.setAttribute("usuarioLogeado", coordinador);
            return "redirect:/coordinador/index";
        } else {
            model.addAttribute("error", true);
            return "login-coordinador";
        }
    }
    
    @GetMapping("/estudiante/crear")
    public String coordinadorCrearTemplate(Model model) {
		model.addAttribute("estudiante", new Estudiante());
        return "estudiante-form";
    }
	
	@GetMapping("/lista")
	public String asociacionListTemplate(Model model) {
		model.addAttribute("estudiantes", estudianteRepository.findAll());
		return "estudiante-lista";
	}

	@GetMapping("/estudiante/edit/{id}")
	public String coordinadorEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("estudiante",
				estudianteRepository.findById(id).orElseThrow(() -> new NotFoundException("estudiante no encontrada")));
		return "estudiante-form";
	}

	@PostMapping("/estudiante/save")
	public String coordinadorSaveProcess(@ModelAttribute("estudiante") Estudiante estudiante) {
		if (estudiante.getId().isEmpty()) {
			estudiante.setId(null);
		}
		estudianteRepository.save(estudiante);
		return "redirect:/coordinador/index";
	}

	@GetMapping("/estudiante/delete/{id}")
	public String coordinadorDeleteProcess(@PathVariable("id") String id) {
		estudianteRepository.deleteById(id);
		return "redirect:/coordinador/lista";
	}
}
