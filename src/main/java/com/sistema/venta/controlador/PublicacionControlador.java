package com.sistema.venta.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.venta.dto.PublicacionDTO;
import com.sistema.venta.servicio.PublicacionServicio;

import io.micrometer.core.instrument.Meter.Id;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {
	
	@Autowired
	private PublicacionServicio publicacionServicio;
	
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacon(@RequestBody PublicacionDTO pulblicacionDTO){
		return new ResponseEntity<>(publicacionServicio.crearPublicacion(pulblicacionDTO),HttpStatus.CREATED);
	}

	@GetMapping
	public List<PublicacionDTO> listarPublicaciones(){
		return publicacionServicio.obtenerTodasLasPublicaciones();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id){
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@RequestBody PublicacionDTO pulblicacionDTO, @PathVariable(name = "id") long id){
		PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublicacion(pulblicacionDTO, id);
		return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id){
		publicacionServicio.eliminarPublicacion(id);
		return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
	}
}
