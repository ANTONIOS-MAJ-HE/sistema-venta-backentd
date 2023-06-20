package com.sistema.venta.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.venta.dto.PublicacionDTO;
import com.sistema.venta.servicio.PublicacionServicio;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {
	
	@Autowired
	private PublicacionServicio publicacionServicio;
	
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacon(@RequestBody PublicacionDTO pulblicacionDTO){
		return new ResponseEntity<>(publicacionServicio.crearPublicacion(pulblicacionDTO),HttpStatus.CREATED);
	}

}
